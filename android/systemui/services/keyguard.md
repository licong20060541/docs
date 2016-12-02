#Keyguard

#view的添加
```
PhoneStatusBar.java

    protected ViewGroup getBouncerContainer() {
        return mStatusBarWindow;
    }


   mStatusBarKeyguardViewManager = keyguardViewMediator.registerStatusBar(this,
          getBouncerContainer(), mStatusBarWindowManager, mScrimController,
          mFingerprintUnlockController);
```
```

KeyguardViewMediator.java

    public StatusBarKeyguardViewManager registerStatusBar(PhoneStatusBar phoneStatusBar,
            ViewGroup container, StatusBarWindowManager statusBarWindowManager,
            ScrimController scrimController,
            FingerprintUnlockController fingerprintUnlockController) {
        mStatusBarKeyguardViewManager.registerStatusBar(phoneStatusBar, container,
                statusBarWindowManager, scrimController, fingerprintUnlockController);
        return mStatusBarKeyguardViewManager;
    }

```
```
StatusBarKeyguardViewManager.java

    public void registerStatusBar(PhoneStatusBar phoneStatusBar,
            ViewGroup container, StatusBarWindowManager statusBarWindowManager,
            ScrimController scrimController,
            FingerprintUnlockController fingerprintUnlockController) {
        mPhoneStatusBar = phoneStatusBar;
        mContainer = container; // !!!
        mStatusBarWindowManager = statusBarWindowManager;
        mScrimController = scrimController;
        mFingerprintUnlockController = fingerprintUnlockController;
        // !!!
        mBouncer = SystemUIFactory.getInstance().createKeyguardBouncer(mContext,
                mViewMediatorCallback, mLockPatternUtils, mStatusBarWindowManager, container);
       // 调用 return new KeyguardBouncer(context, callback, lockPatternUtils, windowManager, container);
    }
```
```

KeyguardBouncer.java

    public KeyguardBouncer(Context context, ViewMediatorCallback callback,
            LockPatternUtils lockPatternUtils, StatusBarWindowManager windowManager,
            ViewGroup container) {
        mContext = context;
        mCallback = callback;
        mLockPatternUtils = lockPatternUtils;
        mContainer = container;
        mWindowManager = windowManager;
        KeyguardUpdateMonitor.getInstance(mContext).registerCallback(mUpdateMonitorCallback);
        mFalsingManager = FalsingManager.getInstance(mContext);
    }


    protected void inflateView() {
        removeView();
        mRoot = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.keyguard_bouncer, null);
        mKeyguardView = (KeyguardHostView) mRoot.findViewById(R.id.keyguard_host_view);
        mKeyguardView.setLockPatternUtils(mLockPatternUtils);
        mKeyguardView.setViewMediatorCallback(mCallback);
        mContainer.addView(mRoot, mContainer.getChildCount()); // 将keyguardview添加到了statubar
        mRoot.setVisibility(View.INVISIBLE);
        mRoot.setSystemUiVisibility(View.STATUS_BAR_DISABLE_HOME);
    }

其中KeyguardHostView是framework提供了


```


# 界面说明

```
首先看到的称为LockScreen界面，即滑动就消失
接着判断是否设置UnLockScreen界面，即图案，密码等等开锁界面，设置了就显示

```
[以下参考](http://blog.csdn.net/ocean2006/article/details/8079457)
```
PhoneWindowManager是解锁屏模块对外交互的接口，窗口管理Service、电源管理Service等外部模块都是通过PhoneWindowManager访问Keyguard内部功能。
      KeyguardViewMediator类为解锁屏模块的中介者，以中介的身份处理keyguard状态变化，处理event、power管理、PhoneWindowManager通知等请求，并作为回调对象供解锁屏模块的其它类回调。
      KeyguardUpdateMonitor类为解锁屏模块的监听者，它负责监听时间、sim卡、运营商信息、电池信息、电话信息等状态的变化，并通知keyguard View模块更新显示。
      KeyguardViewManager类为解锁屏view模块的管理者，管理解锁屏界面的创建、显示、隐藏以及重置等。
      LockPatternKeyguardView类为解锁屏模块的View界面，为所有解锁屏界面的host view。根据设置的安全策略，显示不同的解锁屏界面。Google原生代码中实现了6种解锁屏界面：
     1) LockScreen：用于显示屏幕加锁状态
     2) PatternUnlockScreen：实现图案解锁模式
     3) SimPukUnlockScreen：屏幕实现SIM PUK码解锁模式
     4) SimUnlockScreen：实现Sim PIN码解锁模式
     5) AccountUnlockScreen：实现 GOOGLE 帐户解锁
     6) PasswordUnlockScreen：实现自定义密码解锁模式
```

#其它
```
Keyguard的启动是从WindowManagerService的systemReady开始的，
在WindowManagerService.systemReady()中会调用PhoneWindowManager的systemReady,
因为PhoneWindowManager是WindowManagerPolicy的子类
keyguard画面view是在屏幕关闭的时候显示的，所以当屏幕亮起来的时候，keyguard画面能够直接显示了
```