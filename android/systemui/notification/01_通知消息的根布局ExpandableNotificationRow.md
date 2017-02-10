# 1 ExpandableView

```

/**
 * An abstract view for expandable views.
 */
public abstract class ExpandableView extends FrameLayout {

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int givenSize = MeasureSpec.getSize(heightMeasureSpec);
        int ownMaxHeight = Integer.MAX_VALUE;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != MeasureSpec.UNSPECIFIED && givenSize != 0) {
            ownMaxHeight = Math.min(givenSize, ownMaxHeight);
        }
        int newHeightSpec = MeasureSpec.makeMeasureSpec(ownMaxHeight, MeasureSpec.AT_MOST);
        int maxChildHeight = 0;
        int childCount = getChildCount();
        // 先测量非MATCH_PARENT的view，确定自身的最大高度，然后利用这个值去测量mMatchParentViews
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            int childHeightSpec = newHeightSpec;
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
            if (layoutParams.height != ViewGroup.LayoutParams.MATCH_PARENT) {
                if (layoutParams.height >= 0) {
                    // An actual height is set ！！！
                    childHeightSpec = layoutParams.height > ownMaxHeight
                        ? MeasureSpec.makeMeasureSpec(ownMaxHeight, MeasureSpec.EXACTLY)
                        : MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
                }
                child.measure(
                        getChildMeasureSpec(widthMeasureSpec, 0 /* padding */, layoutParams.width),//！！！！
                        childHeightSpec);
                int childHeight = child.getMeasuredHeight();
                maxChildHeight = Math.max(maxChildHeight, childHeight);
            } else {
                mMatchParentViews.add(child);//！！！！
            }
        }
        int ownHeight = heightMode == MeasureSpec.EXACTLY
                ? givenSize : Math.min(ownMaxHeight, maxChildHeight);
        newHeightSpec = MeasureSpec.makeMeasureSpec(ownHeight, MeasureSpec.EXACTLY);
        for (View child : mMatchParentViews) {//！！！！
            child.measure(getChildMeasureSpec(
                    widthMeasureSpec, 0 /* padding */, child.getLayoutParams().width),
                    newHeightSpec);
        }
        mMatchParentViews.clear();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, ownHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        updateClipping();
    }
```

```

    /**
     * Sets the actual height of this notification. This is different than the laid out
     * {@link View#getHeight()}, as we want to avoid layouting during scrolling and expanding.
     *
     * @param actualHeight The height of this notification.
     * @param notifyListeners Whether the listener should be informed about the change.
     */
    public void setActualHeight(int actualHeight, boolean notifyListeners) {
        mActualHeight = actualHeight;
        updateClipping();
        if (notifyListeners) {
            notifyHeightChanged(false  /* needsAnimation */);
        }
    }
    
    private void updateClipping() {
        if (mClipToActualHeight) {
            int top = getClipTopAmount();
            if (top >= getActualHeight()) {
                top = getActualHeight() - 1;
            }
            mClipRect.set(0, top, getWidth(), getActualHeight() + getExtraBottomPadding());
            setClipBounds(mClipRect);// view的方法
        } else {
            setClipBounds(null);
        }
    }
```

```
    /** view method
     * Sets a rectangular area on this view to which the view will be clipped
     * when it is drawn. Setting the value to null will remove the clip bounds
     * and the view will draw normally, using its full bounds.
     *
     * @param clipBounds The rectangular area, in the local coordinates of
     * this view, to which future drawing operations will be clipped.
     */
    public void setClipBounds(Rect clipBounds) {
    }
```

```
    /** 加上了偏移操作，和clipTop操作
     * This method returns the drawing rect for the view which is different from the regular
     * drawing rect, since we layout all children in the {@link NotificationStackScrollLayout} at
     * position 0 and usually the translation is neglected被忽视的. Since we are manually clipping this
     * view,we also need to subtract the clipTopAmount from the top. ！！！This is needed in order to
     * ensure that accessibility and focusing work correctly.！！！
     *
     * @param outRect The (scrolled) drawing bounds of the view.
     */
    @Override
    public void getDrawingRect(Rect outRect) {
        super.getDrawingRect(outRect);
        outRect.left += getTranslationX();
        outRect.right += getTranslationX();
        outRect.bottom = (int) (outRect.top + getTranslationY() + getActualHeight());
        outRect.top += getTranslationY() + getClipTopAmount();
    }


    // 其view的实现如下，默认参考自身的布局
        /**
     * Return the visible drawing bounds of your view. Fills in the output
     * rectangle with the values from getScrollX(), getScrollY(),
     * getWidth(), and getHeight(). These bounds do not account for any
     * transformation properties currently set on the view, such as
     * {@link #setScaleX(float)} or {@link #setRotation(float)}.
     *
     * @param outRect The (scrolled) drawing bounds of the view.
     */
    public void getDrawingRect(Rect outRect) {
        outRect.left = mScrollX;
        outRect.top = mScrollY;
        outRect.right = mScrollX + (mRight - mLeft);
        outRect.bottom = mScrollY + (mBottom - mTop);
    }
    // end  其view的实现如下

    @Override
    public void getBoundsOnScreen(Rect outRect, boolean clipToParent) {
        super.getBoundsOnScreen(outRect, clipToParent);
        if (getTop() + getTranslationY() < 0) {
            // We got clipped to the parent here - make sure we undo that.
            outRect.top += getTop() + getTranslationY();
        }
        outRect.bottom = outRect.top + getActualHeight();
        outRect.top += getClipTopAmount();
    }
```

# 2 ExpandableOutlineView

```

/**
 * Like {@link ExpandableView}, but setting an outline for the height and clipping.
 */
/**
 * Like {@link ExpandableView}, but setting an outline for the height and clipping.
 */
public abstract class ExpandableOutlineView extends ExpandableView {

    private final Rect mOutlineRect = new Rect();
    private boolean mCustomOutline;

    public ExpandableOutlineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                if (!mCustomOutline) {
                    outline.setRect(0,
                            mClipTopAmount,
                            getWidth(),
                            Math.max(getActualHeight(), mClipTopAmount));
                } else {
                    outline.setRect(mOutlineRect);
                }
            }
        });
    }

    @Override
    public void setActualHeight(int actualHeight, boolean notifyListeners) {
        super.setActualHeight(actualHeight, notifyListeners);
        invalidateOutline();
    }

    @Override
    public void setClipTopAmount(int clipTopAmount) {
        super.setClipTopAmount(clipTopAmount);
        invalidateOutline();
    }

    protected void setOutlineRect(RectF rect) {
        if (rect != null) {
            setOutlineRect(rect.left, rect.top, rect.right, rect.bottom);
        } else {
            mCustomOutline = false;
            invalidateOutline();
        }
    }

    protected void setOutlineRect(float left, float top, float right, float bottom) {
        mCustomOutline = true;

        mOutlineRect.set((int) left, (int) top, (int) right, (int) bottom);

        // Outlines need to be at least 1 dp
        mOutlineRect.bottom = (int) Math.max(top, mOutlineRect.bottom);
        mOutlineRect.right = (int) Math.max(left, mOutlineRect.right);

        invalidateOutline();
    }
    
    
    // view的
        /**
     * Sets the {@link ViewOutlineProvider} of the view, which generates the Outline that defines
     * the shape of the shadow it casts, and enables outline clipping.
     * <p>
     * The default ViewOutlineProvider, {@link ViewOutlineProvider#BACKGROUND}, queries the Outline
     * from the View's background drawable, via {@link Drawable#getOutline(Outline)}. Changing the
     * outline provider with this method allows this behavior to be overridden.
     * <p>
     * If the ViewOutlineProvider is null, if querying it for an outline returns false,
     * or if the produced Outline is {@link Outline#isEmpty()}, shadows will not be cast.
     * <p>
     * Only outlines that return true from {@link Outline#canClip()} may be used for clipping.
     *
     * @see #setClipToOutline(boolean)
     * @see #getClipToOutline()
     * @see #getOutlineProvider()
     */
    public void setOutlineProvider(ViewOutlineProvider provider) {
        mOutlineProvider = provider;
        invalidateOutline();
    }

}
```

# 3 ActivatableNotificationView可启动的

```
implement dimming/activating on Keyguard for the double-tap gesture

        setClipChildren(false);
        setClipToPadding(false);


    @Override
    public void drawableHotspotChanged(float x, float y) {
        if (!mDimmed){
            mBackgroundNormal.drawableHotspotChanged(x, y);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mDimmed) {
            mBackgroundDimmed.setState(getDrawableState());
        } else {
            mBackgroundNormal.setState(getDrawableState());
        }
    }

/**
 * Base class for both {@link ExpandableNotificationRow} and {@link NotificationOverflowContainer}
 * to implement dimming/activating on Keyguard for the double-tap gesture
 */
 // implement dimming/activating on Keyguard for the double-tap gesture
public abstract class ActivatableNotificationView extends ExpandableOutlineView {
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackgroundNormal = (NotificationBackgroundView) findViewById(R.id.backgroundNormal); //
        mFakeShadow = (FakeShadowView) findViewById(R.id.fake_shadow);
        mBackgroundDimmed = (NotificationBackgroundView) findViewById(R.id.backgroundDimmed); //
        mBackgroundNormal.setCustomBackground(R.drawable.notification_material_bg);
        mBackgroundDimmed.setCustomBackground(R.drawable.notification_material_bg_dim);
        updateBackground();
        updateBackgroundTint();
        updateOutlineAlpha();
    }

}

   private void startActivateAnimation(boolean reverse) {
        if (!isAttachedToWindow()) {
            return;
        }
        int widthHalf = mBackgroundNormal.getWidth()/2;
        int heightHalf = mBackgroundNormal.getActualHeight()/2;
        float radius = (float) Math.sqrt(widthHalf*widthHalf + heightHalf*heightHalf);
        Animator animator;
        if (reverse) {//!!!!!!!!!!!!!!!!! 对view的大小执行水波纹变化
            animator = ViewAnimationUtils.createCircularReveal(mBackgroundNormal,
                    widthHalf, heightHalf, radius, 0);
        } else {
            animator = ViewAnimationUtils.createCircularReveal(mBackgroundNormal,
                    widthHalf, heightHalf, 0, radius);
        }
        mBackgroundNormal.setVisibility(View.VISIBLE);
        Interpolator interpolator;
        Interpolator alphaInterpolator;
        if (!reverse) {
            interpolator = mLinearOutSlowInInterpolator;
            alphaInterpolator = mLinearOutSlowInInterpolator;
        } else {
            interpolator = ACTIVATE_INVERSE_INTERPOLATOR;
            alphaInterpolator = ACTIVATE_INVERSE_ALPHA_INTERPOLATOR;
        }
        animator.setInterpolator(interpolator);
        animator.setDuration(ACTIVATE_ANIMATION_LENGTH);
        if (reverse) {
            mBackgroundNormal.setAlpha(1f);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (mDimmed) {
                        mBackgroundNormal.setVisibility(View.INVISIBLE);
                    }
                }
            });
            animator.start();
        } else {
            mBackgroundNormal.setAlpha(0.4f);
            animator.start();
        }
        mBackgroundNormal.animate()
                .alpha(reverse ? 0f : 1f)
                .setInterpolator(alphaInterpolator)
                .setDuration(ACTIVATE_ANIMATION_LENGTH);
    }

  @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setPivotX(getWidth() / 2);
    }

    @Override
    public void setActualHeight(int actualHeight, boolean notifyListeners) {
        super.setActualHeight(actualHeight, notifyListeners);
        setPivotY(actualHeight / 2);
        mBackgroundNormal.setActualHeight(actualHeight);
        mBackgroundDimmed.setActualHeight(actualHeight);
    }

    @Override
    public void setClipTopAmount(int clipTopAmount) {
        super.setClipTopAmount(clipTopAmount);
        mBackgroundNormal.setClipTopAmount(clipTopAmount);
        mBackgroundDimmed.setClipTopAmount(clipTopAmount);
    }
    
        private PorterDuffColorFilter mAppearAnimationFilter;
            mAppearAnimationFilter.setColor(sourceColor);
            mAppearPaint.setColorFilter(mAppearAnimationFilter);
            
                       Bitmap bitmap = Bitmap.createBitmap(getWidth(), getActualHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                draw(canvas);
                mAppearPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                        Shader.TileMode.CLAMP));


```

```
// 补充
/**
 * When the phone is locked, listens to touch, sensor and phone events and sends them to
 * DataCollector and HumanInteractionClassifier.
 *
 * It does not collect touch events when the bouncer shows up.
 */
public class FalsingManager implements SensorEventListener {
```

# 4 ExpandableNotificationRow

```
    /**
     * Whether the notification expansion is disabled. This is the case on Keyguard.
     */
    private boolean mExpansionDisabled;


public class ExpandableNotificationRow extends ActivatableNotificationView {
    private ArrayList<View> mTranslateableViews;
    //隐藏敏感  mShowingPublic = mSensitive && hideSensitive; 存在敏感信息并要求隐藏时显示
    private NotificationContentView mPublicLayout;
    private NotificationContentView mPrivateLayout;//包括了大小视图view
    private NotificationGroupManager mGroupManager;
    private View mVetoButton; // 清除通知按钮
    // The guts内脏;肠 of a notification revealed when performing a long press.
    // 长按消息会显示，其中包括了设置按钮等操作
    private NotificationGuts mGuts;
        /** Does this row contain layouts that can adapt to row expansion */
    private boolean mExpandable;
}

        ViewStub gutsStub = (ViewStub) findViewById(R.id.notification_guts_stub);
        gutsStub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                mGuts = (NotificationGuts) inflated;
                mGuts.setClipTopAmount(getClipTopAmount());
                mGuts.setActualHeight(getActualHeight());
            }
        });

   private void setIconAnimationRunningForChild(boolean running, View child) {
        if (child != null) {
            ImageView icon = (ImageView) child.findViewById(com.android.internal.R.id.icon);
            setIconRunning(icon, running);
            ImageView rightIcon = (ImageView) child.findViewById(
                    com.android.internal.R.id.right_icon);
            setIconRunning(rightIcon, running);
        }
    }

    private void setIconRunning(ImageView imageView, boolean running) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
                if (running) {
                    animationDrawable.start();
                } else {
                    animationDrawable.stop();
                }
            } else if (drawable instanceof AnimatedVectorDrawable) {
                AnimatedVectorDrawable animationDrawable = (AnimatedVectorDrawable) drawable;
                if (running) {
                    animationDrawable.start();
                } else {
                    animationDrawable.stop();
                }
            }
        }
    }
    
    
        @Override
    public boolean onRequestSendAccessibilityEvent(View child, AccessibilityEvent event) {
        if (super.onRequestSendAccessibilityEvent(child, event)) {
            // Add a record for the entire layout since its content is somehow small.
            // The event comes from a leaf view that is interacted with.
            AccessibilityEvent record = AccessibilityEvent.obtain();
            onInitializeAccessibilityEvent(record);
            dispatchPopulateAccessibilityEvent(record);
            event.appendRecord(record);
            return true;
        }
        return false;
    }
    
    // 补充
    Called when a child has requested sending an AccessibilityEvent and gives an opportunity to its parent to augment the event.
If an View.AccessibilityDelegate has been specified via calling View.setAccessibilityDelegate(View.AccessibilityDelegate) its View.AccessibilityDelegate.onRequestSendAccessibilityEvent(ViewGroup, View, AccessibilityEvent) is responsible for handling this call.
   //
   AccessibilityEvent：This class represents accessibility events that are sent by the system when something notable happens in the user interface. For example, when a android.widget.Button is clicked, a android.view.View is focused, etc.


```
