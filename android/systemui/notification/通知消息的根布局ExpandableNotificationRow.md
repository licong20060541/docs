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
    /**
     * This method returns the drawing rect for the view which is different from the regular
     * drawing rect, since we layout all children in the {@link NotificationStackScrollLayout} at
     * position 0 and usually the translation is neglected被忽视的. Since we are manually clipping this
     * view,we also need to subtract the clipTopAmount from the top. This is needed in order to
     * ensure that accessibility and focusing work correctly.
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
public abstract class ExpandableOutlineView extends ExpandableView {

    private final Rect mOutlineRect = new Rect();
    private boolean mCustomOutline;
    private float mOutlineAlpha = -1f;

    ViewOutlineProvider mProvider = new ViewOutlineProvider() {
        @Override
        public void getOutline(View view, Outline outline) {
            int translation = (int) getTranslation();
            if (!mCustomOutline) {
                outline.setRect(translation,
                        mClipTopAmount,
                        getWidth() + translation,
                        Math.max(getActualHeight(), mClipTopAmount));
            } else {
                outline.setRect(mOutlineRect);
            }
            outline.setAlpha(mOutlineAlpha);
        }
    };

    public ExpandableOutlineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOutlineProvider(mProvider);
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

    protected void setOutlineAlpha(float alpha) {
        if (alpha != mOutlineAlpha) {
            mOutlineAlpha = alpha;
            invalidateOutline();
        }
    }

}
```

# 3 ActivatableNotificationView

```

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
public class ExpandableNotificationRow extends ActivatableNotificationView {
    private ArrayList<View> mTranslateableViews;
    private NotificationContentView mPublicLayout;
    private NotificationContentView mPrivateLayout;
    private NotificationGroupManager mGroupManager;
}
```
