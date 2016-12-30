01_onSaveInstanceState
注意：View在布局文件中声明一定要有一个Id.
```
@Override
protected Parcelable onSaveInstanceState()
{
    Bundle bundle = new Bundle();
    bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
    bundle.putBoolean(INSTANCE_GAME_OVER, mIsGameOver);
    return bundle;
}

@Override
protected void onRestoreInstanceState(Parcelable state)
{
    if (state instanceof Bundle)
    {
        Bundle bundle = (Bundle) state;
        mIsGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
        super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
        return;
    }
    super.onRestoreInstanceState(state);
}

```

02_设置ViewPager切换速度
```
    private void setViewPagerSpeed(int duration) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            scroller = new FixedSpeedScroller(MainActivity.this, new AccelerateInterpolator());
            field.set(viewPager, scroller);
            scroller.setmDuration(duration);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
```


03_shader
```
        //设置圆形扫描渲染的shader，渐变
        scanShader = new SweepGradient(mWidth / 2, mHeight / 2,
                new int[]{Color.TRANSPARENT, Color.parseColor("#84B5CA")}, null);

        mPaintScan.setShader(scanShader);
```

04_snakebar
官方推荐使用CoordinatorLayout来确保Snackbar和其他组件的交互，
比如Snackbar出现时FloatingActionButton上移。

05_FlexBox
```
Google开源了FlexBox布局功能类似的flexbox-layout，项目地址：
https://github.com/google/flexbox-layout
标签等待功能
```

06_Fragment

```
        Activity mActivity = new Activity();
        mActivity.getFragmentManager().beginTransaction().replace()
                .addToBackStack().commitAllowingStateLoss();

        // remove
        if(mActivity.getFragmentManager().getBackStackEntryCount() > 1) {
            mActivity.getFragmentManager().popBackStack();
        } else {
            mActivity.finish();
        }
        
        // oncreate()
        if(saveInstanceState != null) {
            // 1.异常退出了,找到保存的fragment
            mActivity.getFragmentManager().findFragmentByTag();
            // 2.显示退出时的fragment,隐藏其它fragment
            mActivity.getFragmentManager().beginTransaction()
                    .hide().hide().show().commitAllowingStateLoss();
        } else {
            //正常启动，new Fragment 等等
        }
        
```

07_LayoutInflater
```
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);
        initView();
        
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context
                    , AttributeSet attrs) {
                // return your view
                // or below
                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent, name, context, attrs);
                if(view != null && view instanceof TextView) {
                    ((TextView) view).setTypeface(typeface);
                }
                return view;
            }
        });
    }
```

08_矢量图

```
1.
    <ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@mipmap/ic_launcher"
        android:tint="#ff00ff"/>

2.
        ImageView imageView = new ImageView(this);
        Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
        imageView.setImageDrawable(drawable);
        Drawable.ConstantState state = drawable.getConstantState();
        Drawable drawable1 = DrawableCompat.wrap(state == null ? drawable
                : state.newDrawable()).mutate();
        drawable1.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        DrawableCompat.setTint(drawable1, ContextCompat.getColor(this, R.color.colorPrimary));
        ImageView imageView1 = new ImageView(this);
        imageView1.setImageDrawable(drawable1);

3. selector
        // selector
        int[] colors = new int[] {
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
        };
        int[][] states = new int[2][];
        states[0] = new int[]{ android.R.attr.state_pressed };
        states[1] = new int[]{};
        // 不同状态，颜色不同
        ColorStateList colorStateList = new ColorStateList(states, colors);

        // 不同状态，图片相同
        StateListDrawable listDrawable = new StateListDrawable();
        listDrawable.addState(states[0], drawable);
        listDrawable.addState(states[1], drawable);
        Drawable.ConstantState state1 = listDrawable.getConstantState();
        drawable = DrawableCompat.wrap(state1 == null ? drawable :
                state1.newDrawable()).mutate();
        // 设置tint颜色状态
        DrawableCompat.setTintList(drawable, colorStateList);
        imageView.setImageDrawable(drawable);

```

09_绘制虚线，贝塞尔
```
        // 绘制虚线
        DashPathEffect effect = new DashPathEffect(new float[] {5, 5}, 1); // 实线 + 虚线
        Paint paint = new Paint();
        paint.setPathEffect(effect);
        Path path = new Path();
        // 三阶贝塞尔曲线， (x1,y1) 为控制点，(x2,y2)为控制点，(x3,y3) 为结束点
        path.cubicTo(x1, y1, x2, y2, x3, y3);
```
