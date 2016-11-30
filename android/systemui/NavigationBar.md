#BarTransitions
```
BarTransitions ： 背景色的渐变
   其实就是状态栏导航栏可以透明半透明等变化的根本
       背景色渐变的执行者BarBackgroundDrawable（内部类）
       设置view的背景mView.setBackground(mBarBackground)
构造函数传入的资源 gradientResourceId为渐变会使用的

final float t = (now - mStartTime) / (float)(mEndTime - mStartTime);
final float v = Math.max(0, Math.min(mInterpolator.getInterpolation(t), 1));
mGradientAlpha = (int)(v * targetGradientAlpha + mGradientAlphaStart * (1 – v)); 
解析：上行代码转换
      mGradientAlphaStart + (targetGradientAlpha – mGradientAlphaStart) * v
含义为起始值 + 对应的变化值(目标值-起始值对应的比率)

函数isHighEndGfx()可以判断是否是高端设备,能否使用硬件图形加速
```

##NavigationBarTransitions
NavigationBarTransitions extends BarTransitions
子view的渐变，最后有触摸监听，当有事件时，会立刻退出隐藏，但没有使用哎
```
public final class NavigationBarTransitions extends BarTransitions {
    private final NavigationBarView mView;
    private final IStatusBarService mBarService;
    private boolean mLightsOut;

    public NavigationBarTransitions(NavigationBarView view) {
        super(view, R.drawable.nav_background);
        mView = view;
        mBarService = IStatusBarService.Stub.asInterface(
                ServiceManager.getService(Context.STATUS_BAR_SERVICE));
    }

    public void init() {
        applyModeBackground(-1, getMode(), false /*animate*/); // 背景渐变
        applyMode(getMode(), false /*animate*/, true /*force*/); // 子view渐变
    }

    @Override
    protected void onTransition(int oldMode, int newMode, boolean animate) {
        super.onTransition(oldMode, newMode, animate);
        applyMode(newMode, animate, false /*force*/);//添加子view渐变
    }

    private void applyMode(int mode, boolean animate, boolean force) {
        // apply to lights out
        applyLightsOut(isLightsOut(mode), animate, force);
    }

    private void applyLightsOut(boolean lightsOut, boolean animate, boolean force) {
        if (!force && lightsOut == mLightsOut) return;
        mLightsOut = lightsOut;
        final View navButtons = mView.getCurrentView().findViewById(R.id.nav_buttons);//拿到子view
        // ok, everyone, stop it right there
        navButtons.animate().cancel();

        final float navButtonsAlpha = lightsOut ? 0.5f : 1f;
        if (!animate) {
            navButtons.setAlpha(navButtonsAlpha);
        } else {
            final int duration = lightsOut ? LIGHTS_OUT_DURATION : LIGHTS_IN_DURATION;
            navButtons.animate()
                .alpha(navButtonsAlpha)
                .setDuration(duration)
                .start();
        }
    }

    private final View.OnTouchListener mLightsOutListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                // even though setting the systemUI visibility below will turn these views
                // on, we need them to come up faster so that they can catch this motion
                // event
                applyLightsOut(false, false, false);
                try {
                    mBarService.setSystemUiVisibility(0, View.SYSTEM_UI_FLAG_LOW_PROFILE,
                            "LightsOutListener");
                } catch (android.os.RemoteException ex) {
                }
            }
            return false;
        }
    };
}
```

##PhoneStatusBarTransitions

PhoneStatusBarTransitions extends BarTransitions
除了背景的渐变，也添加了对子view的支持渐变
View mLeftSide, mStatusIcons, mSignalCluster, mBattery, mClock；

#ButtonDispatcher
统一管理多个view，设置背景和事件等等

#NavigationBarInflaterView
添加view，并将相同id的存储，即0度和90度各有一套相同的view，将id相同放一起mButtonDispatchers
，然后一块操作了

#NavigationBarView
```
 private Drawable mBackIcon, mBackLandIcon, mBackAltIcon, mBackAltLandIcon;
 // 正常返回键和弹出键盘的向下返回键即隐藏键盘
 private Drawable mDockedIcon; // 分屏时的图标看效果，即上下两个小矩形


// hints标示了各种状态，根据此来判断显示哪些按钮
    public void setNavigationIconHints(int hints, boolean force) {
        if (!force && hints == mNavigationIconHints) return;
        final boolean backAlt = (hints & StatusBarManager.NAVIGATION_HINT_BACK_ALT) != 0;
        if ((mNavigationIconHints & StatusBarManager.NAVIGATION_HINT_BACK_ALT) != 0 && !backAlt) {
            mTransitionListener.onBackAltCleared(); // back alt 向下的返回图标
        }

        setDisabledFlags(mDisabledFlags, true); // 禁用判断：以及SLIPPERY


```