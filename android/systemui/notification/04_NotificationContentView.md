#变量
```
/**
 * A frame layout containing the actual payload of the notification, including the contracted,
 * expanded and heads up layout. This class is responsible for clipping the content and and
 * switching between the expanded, contracted and the heads up view depending on its clipped size.
 */
 // 通知的实际载体，包括了contracted，expanded and heads up layout
 // 负责裁剪内容，并根据裁剪后的大小选择上述三者之一
public class NotificationContentView extends FrameLayout {

    private static final long ANIMATION_DURATION_LENGTH = 170;//TIME
    private static final int VISIBLE_TYPE_CONTRACTED = 0;//上述三者类型
    private static final int VISIBLE_TYPE_EXPANDED = 1;
    private static final int VISIBLE_TYPE_HEADSUP = 2;

    private final Rect mClipBounds = new Rect();//
    private final int mSmallHeight;
    private final int mHeadsUpHeight;
    private final int mRoundRectRadius;
    private final Interpolator mLinearInterpolator = new LinearInterpolator();
    private final boolean mRoundRectClippingEnabled;

    private View mContractedChild; // 关键三者
    private View mExpandedChild;
    private View mHeadsUpChild;

    private NotificationViewWrapper mContractedWrapper;//
    private NotificationViewWrapper mExpandedWrapper;
    private NotificationViewWrapper mHeadsUpWrapper;

    private int mClipTopAmount;
    private int mContentHeight; // 内容高度，裁剪用到的
    private int mUnrestrictedContentHeight;
    private int mVisibleType = VISIBLE_TYPE_CONTRACTED;
    private boolean mDark;
    private final Paint mFadePaint = new Paint();
    private boolean mAnimate;
    private boolean mIsHeadsUp;
    private boolean mShowingLegacyBackground;
```

#测量布局
```
 @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        boolean hasFixedHeight = heightMode == MeasureSpec.EXACTLY;
        boolean isHeightLimited = heightMode == MeasureSpec.AT_MOST;
        int maxSize = Integer.MAX_VALUE;
        if (hasFixedHeight || isHeightLimited) {
            maxSize = MeasureSpec.getSize(heightMeasureSpec);//！！！！父类允许的当前view最大高度的设置
        } // 看布局为isHeightLimited，因此父view如果小的话，那么此处会限制三个子view的显示逻辑了！！！
        int maxChildHeight = 0; // child 的实际最大高度
        if (mContractedChild != null) {
            int size = Math.min(maxSize, mSmallHeight);//
            mContractedChild.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
            maxChildHeight = Math.max(maxChildHeight, mContractedChild.getMeasuredHeight());
        }
        if (mExpandedChild != null) {
            int size = maxSize;
            ViewGroup.LayoutParams layoutParams = mExpandedChild.getLayoutParams();
            if (layoutParams.height >= 0) {
                // An actual height is set
                size = Math.min(maxSize, layoutParams.height);
            }
            int spec = size == Integer.MAX_VALUE
                    ? MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                    : MeasureSpec.makeMeasureSpec(size, MeasureSpec.AT_MOST);
            mExpandedChild.measure(widthMeasureSpec, spec);
            maxChildHeight = Math.max(maxChildHeight, mExpandedChild.getMeasuredHeight());
        }
        if (mHeadsUpChild != null) {
            int size = Math.min(maxSize, mHeadsUpHeight);
            ViewGroup.LayoutParams layoutParams = mHeadsUpChild.getLayoutParams();
            if (layoutParams.height >= 0) {
                // An actual height is set
                size = Math.min(maxSize, layoutParams.height);
            }
            mHeadsUpChild.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(size, MeasureSpec.AT_MOST));
            maxChildHeight = Math.max(maxChildHeight, mHeadsUpChild.getMeasuredHeight());
        }
        int ownHeight = Math.min(maxChildHeight, maxSize);//取两者较小！！！！
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, ownHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        updateClipping();
        invalidateOutline();
    }
```

#三子view的选择逻辑
```
 // 设置子view

    public View getContractedChild() {
        return mContractedChild;
    }

    public View getExpandedChild() {
        return mExpandedChild;
    }

    public View getHeadsUpChild() {
        return mHeadsUpChild;
    }

    public void setContractedChild(View child) {
        if (mContractedChild != null) {
            mContractedChild.animate().cancel();
            removeView(mContractedChild);
        }
        addView(child);
        mContractedChild = child;
        mContractedWrapper = NotificationViewWrapper.wrap(getContext(), child);
        selectLayout(false /* animate */, true /* force */);
        mContractedWrapper.setDark(mDark, false /* animate */, 0 /* delay */);
        updateRoundRectClipping();
    }

    public void setExpandedChild(View child) {
        if (mExpandedChild != null) {
            mExpandedChild.animate().cancel();
            removeView(mExpandedChild);
        }
        addView(child);
        mExpandedChild = child;
        mExpandedWrapper = NotificationViewWrapper.wrap(getContext(), child);
        selectLayout(false /* animate */, true /* force */);
        updateRoundRectClipping();
    }

    public void setHeadsUpChild(View child) {
        if (mHeadsUpChild != null) {
            mHeadsUpChild.animate().cancel();
            removeView(mHeadsUpChild);
        }
        addView(child);
        mHeadsUpChild = child;
        mHeadsUpWrapper = NotificationViewWrapper.wrap(getContext(), child);
        selectLayout(false /* animate */, true /* force */);
        updateRoundRectClipping();
    }



    /** 获取类型！！！！
     * @return one of the static enum types in this view, calculated form the current state
     */
    private int calculateVisibleType() {
        boolean noExpandedChild = mExpandedChild == null;
        if (mIsHeadsUp && mHeadsUpChild != null) {
            if (mContentHeight <= mHeadsUpChild.getHeight() || noExpandedChild) {
                return VISIBLE_TYPE_HEADSUP;
            } else {
                return VISIBLE_TYPE_EXPANDED;
            }
        } else {
            if (mContentHeight <= mSmallHeight || noExpandedChild) { // 可以展开的条件！！！！！！！！！！！！
            // 对比下面的setContentHeight(int contentHeight)，看其如何赋值的
                return VISIBLE_TYPE_CONTRACTED;
            } else {
                return VISIBLE_TYPE_EXPANDED;
            }
        }
    }
    /** 根据类型获取view
     * @param visibleType one of the static enum types in this view
     * @return the corresponding view according to the given visible type
     */
    private View getViewForVisibleType(int visibleType) {
        switch (visibleType) {
            case VISIBLE_TYPE_EXPANDED:
                return mExpandedChild;
            case VISIBLE_TYPE_HEADSUP:
                return mHeadsUpChild;
            default:
                return mContractedChild;
        }
    }
    // 动画切换view显示
    private void runSwitchAnimation(int visibleType) {
        View shownView = getViewForVisibleType(visibleType);//将要显示的
        View hiddenView = getViewForVisibleType(mVisibleType);
        shownView.setVisibility(View.VISIBLE);
        hiddenView.setVisibility(View.VISIBLE);
        shownView.setLayerType(LAYER_TYPE_HARDWARE, mFadePaint);//！
        hiddenView.setLayerType(LAYER_TYPE_HARDWARE, mFadePaint);//！
        setLayerType(LAYER_TYPE_HARDWARE, null);//！
        hiddenView.animate()
                .alpha(0f)
                .setDuration(ANIMATION_DURATION_LENGTH)
                .setInterpolator(mLinearInterpolator)
                .withEndAction(null); // In case we have multiple changes in one frame.
        shownView.animate()
                .alpha(1f)
                .setDuration(ANIMATION_DURATION_LENGTH)
                .setInterpolator(mLinearInterpolator)
                .withEndAction(new Runnable() {//！！！！！！！！！！！！
                    @Override
                    public void run() {
                        updateViewVisibilities(mVisibleType);//下面函数
                    }
                });
        updateRoundRectClipping();
    }
    // 更新显示与隐藏
    private void updateViewVisibilities(int visibleType) {
        boolean contractedVisible = visibleType == VISIBLE_TYPE_CONTRACTED;
        mContractedChild.setVisibility(contractedVisible ? View.VISIBLE : View.INVISIBLE);
        mContractedChild.setAlpha(contractedVisible ? 1f : 0f);
        mContractedChild.setLayerType(LAYER_TYPE_NONE, null);
        if (mExpandedChild != null) {
            boolean expandedVisible = visibleType == VISIBLE_TYPE_EXPANDED;
            mExpandedChild.setVisibility(expandedVisible ? View.VISIBLE : View.INVISIBLE);
            mExpandedChild.setAlpha(expandedVisible ? 1f : 0f);
            mExpandedChild.setLayerType(LAYER_TYPE_NONE, null);
        }
        if (mHeadsUpChild != null) {
            boolean headsUpVisible = visibleType == VISIBLE_TYPE_HEADSUP;
            mHeadsUpChild.setVisibility(headsUpVisible ? View.VISIBLE : View.INVISIBLE);
            mHeadsUpChild.setAlpha(headsUpVisible ? 1f : 0f);
            mHeadsUpChild.setLayerType(LAYER_TYPE_NONE, null);
        }
        setLayerType(LAYER_TYPE_NONE, null);//！
        updateRoundRectClipping();
    }
    // 根据条件调用上面函数
    private void selectLayout(boolean animate, boolean force) {
        if (mContractedChild == null) {
            return;
        }
        int visibleType = calculateVisibleType();
        if (visibleType != mVisibleType || force) {
            if (animate && ((visibleType == VISIBLE_TYPE_EXPANDED && mExpandedChild != null)
                    || (visibleType == VISIBLE_TYPE_HEADSUP && mHeadsUpChild != null)
                    || visibleType == VISIBLE_TYPE_CONTRACTED)) {
                runSwitchAnimation(visibleType);
            } else {
                updateViewVisibilities(visibleType);
            }
            mVisibleType = visibleType;
        }
    }
```

#clip/outline/predraw
```
private final ViewTreeObserver.OnPreDrawListener mEnableAnimationPredrawListener
            = new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {
            mAnimate = true;
            getViewTreeObserver().removeOnPreDrawListener(this);
            return true;
        }
    };

    private final ViewOutlineProvider mOutlineProvider = new ViewOutlineProvider() {
        @Override
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(0, 0, view.getWidth(), mUnrestrictedContentHeight,
                    mRoundRectRadius);
        }
    };

    public NotificationContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mFadePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        mSmallHeight = getResources().getDimensionPixelSize(R.dimen.notification_min_height);
        mHeadsUpHeight = getResources().getDimensionPixelSize(R.dimen.notification_mid_height);
        mRoundRectRadius = getResources().getDimensionPixelSize(
                R.dimen.notification_material_rounded_rect_radius);
        mRoundRectClippingEnabled = getResources().getBoolean(
                R.bool.config_notifications_round_rect_clipping);
        reset(true);
        setOutlineProvider(mOutlineProvider);//view的函数
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateVisibility();
    }

    public void reset(boolean resetActualHeight) {
        if (mContractedChild != null) {
            mContractedChild.animate().cancel();
        }
        if (mExpandedChild != null) {
            mExpandedChild.animate().cancel();
        }
        if (mHeadsUpChild != null) {
            mHeadsUpChild.animate().cancel();
        }
        removeAllViews();
        mContractedChild = null;
        mExpandedChild = null;
        mHeadsUpChild = null;
        mVisibleType = VISIBLE_TYPE_CONTRACTED;
        if (resetActualHeight) {
            mContentHeight = mSmallHeight;
        }
    }

    private void updateVisibility() {
        setVisible(isShown());
    }

    private void setVisible(final boolean isVisible) {
        if (isVisible) {

            // We only animate if we are drawn at least once, otherwise the view might animate when
            // it's shown the first time
            getViewTreeObserver().addOnPreDrawListener(mEnableAnimationPredrawListener);
        } else {
            getViewTreeObserver().removeOnPreDrawListener(mEnableAnimationPredrawListener);
            mAnimate = false;
        }
    }

    public void setContentHeight(int contentHeight) {
        // 注意此处！！！！getHeight()为此view的高度，即三个子view中最大高度那个
        mContentHeight = Math.max(Math.min(contentHeight, getHeight()), getMinHeight());
        mUnrestrictedContentHeight = Math.max(contentHeight, getMinHeight());//！！！！！！！！！！！！！！唯一赋值处
          // 对比onMeasure中的maxChildHeight，含义差不多
        selectLayout(mAnimate /* animate */, false /* force */);
        updateClipping();
        invalidateOutline();//view的函数
    }
        public int getMaxHeight() {
        if (mIsHeadsUp && mHeadsUpChild != null) {
            return mHeadsUpChild.getHeight();
        } else if (mExpandedChild != null) {
            return mExpandedChild.getHeight();
        }
        return mSmallHeight;
    }

    public int getMinHeight() {
        return mSmallHeight;
    }
    private void updateClipping() {
        mClipBounds.set(0, mClipTopAmount, getWidth(), mContentHeight);
        setClipBounds(mClipBounds);//view的函数
    }
```

