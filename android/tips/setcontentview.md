# setContentView
```
@Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(R.layout.activity_base_layout, null);
        super.setContentView(view);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            view.setFitsSystemWindows(true);
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);

        }

        initDefaultView(layoutResID);
        initDefaultToolBar();

    }

/**
     * 初始化默认的布局组件
     *
     * @param layoutResID
     */
    private void initDefaultView(int layoutResID) {
        mProgressLayout = (ProgressLayout) findViewById(R.id.progress_layout);
        mProgressLayout.setAttachActivity(this);
        mProgressLayout.setUseSlideBack(false);
        mToolbarContainer = (FrameLayout) findViewById(R.id.toolbar_container);
        mDefaultToolBar = (Toolbar) findViewById(R.id.default_toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mRvRight = (RippleView) findViewById(R.id.rv_right);
        mRightOptButton = (TextView) findViewById(R.id.right_opt_button);
        mContentContainer = (FrameLayout) findViewById(R.id.fl_content_container);

        View childView = layoutInflater.inflate(layoutResID, null);
        mContentContainer.addView(childView, 0);
    }

/**
     * 初始化默认的ToolBar
     */
    private void initDefaultToolBar() {
        if (mDefaultToolBar != null) {
            String label = getTitle().toString();
            setTitle(label);
            setSupportActionBar(mDefaultToolBar);
            mDefaultToolBar.setNavigationIcon(R.mipmap.toolbar_back_icn_transparent);
        }
    }
```