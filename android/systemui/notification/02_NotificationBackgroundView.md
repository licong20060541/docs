```
/** 暗背景或者正常背景，二选一，实际中可以使用两个此view实现切换
 * A view that can be used for both the dimmed and normal background of an notification.
 */
public class NotificationBackgroundView extends View {

    private Drawable mBackground;
    private int mClipTopAmount;
    private int mActualHeight;

    public NotificationBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        draw(canvas, mBackground);
    }

    private void draw(Canvas canvas, Drawable drawable) {
        if (drawable != null && mActualHeight > mClipTopAmount) {
            drawable.setBounds(0, mClipTopAmount, getWidth(), mActualHeight);
            drawable.draw(canvas);
        }
    }


//view的说明：
protected boolean verifyDrawable(Drawable who)
If your view subclass is displaying its own Drawable objects, it should override this function and return true for any Drawable it is displaying. This allows animations for those drawables to be scheduled.
Be sure to call through to the super class when overriding this function.
Parameters:
who - The Drawable to verify. Return true if it is one you are displaying, else return the result of calling through to the super class.
Returns:
boolean If true than the Drawable is being displayed in the view; else false and it is not allowed to animate.
See Also:
unscheduleDrawable(Drawable), drawableStateChanged()//

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == mBackground;
    }

    @Override
    protected void drawableStateChanged() {
        drawableStateChanged(mBackground);
    }

    private void drawableStateChanged(Drawable d) {
        if (d != null && d.isStateful()) {
            d.setState(getDrawableState());
        }
    }

    @Override
    public void drawableHotspotChanged(float x, float y) {
        if (mBackground != null) {
            mBackground.setHotspot(x, y);
        }
    }

    /**
     * Sets a background drawable. As we need to change our bounds independently of layout, we need
     * the notion of a background independently of the regular View background..
     */
    public void setCustomBackground(Drawable background) {
        if (mBackground != null) {
            mBackground.setCallback(null);
            unscheduleDrawable(mBackground);
        }
        mBackground = background;
        if (mBackground != null) {
            mBackground.setCallback(this);
        }
        if (mBackground instanceof RippleDrawable) {
            ((RippleDrawable) mBackground).setForceSoftware(true);
        }
        invalidate();
    }

    public void setCustomBackground(int drawableResId) {
        final Drawable d = mContext.getDrawable(drawableResId);
        setCustomBackground(d);
    }

    public void setTint(int tintColor) {
        if (tintColor != 0) {
            mBackground.setColorFilter(tintColor, PorterDuff.Mode.SRC_ATOP);
        } else {
            mBackground.clearColorFilter();
        }
        invalidate();
    }

    public void setActualHeight(int actualHeight) {
        mActualHeight = actualHeight;
        invalidate();
    }

    public int getActualHeight() {
        return mActualHeight;
    }

    public void setClipTopAmount(int clipTopAmount) {
        mClipTopAmount = clipTopAmount;
        invalidate();
    }

    @Override
    public boolean hasOverlappingRendering() {

        // Prevents this view from creating a layer when alpha is animating.
        return false;
    }

    public void setState(int[] drawableState) {
        mBackground.setState(drawableState);
    }

    public void setRippleColor(int color) {
        if (mBackground instanceof RippleDrawable) {
            RippleDrawable ripple = (RippleDrawable) mBackground;
            ripple.setColor(ColorStateList.valueOf(color));
        }
    }
}
```


```

/** 和上面类似，只不过这里继承FrameLayout，长按消息会显示，其中包括了设置按钮等操作
 * The guts内脏;肠 of a notification revealed when performing a long press.
 */
public class NotificationGuts extends FrameLayout {

    private Drawable mBackground;
    private int mClipTopAmount;
    private int mActualHeight;

    public NotificationGuts(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        draw(canvas, mBackground);
    }

    private void draw(Canvas canvas, Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, mClipTopAmount, getWidth(), mActualHeight);
            drawable.draw(canvas);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackground = mContext.getDrawable(R.drawable.notification_guts_bg);
        if (mBackground != null) {
            mBackground.setCallback(this);
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == mBackground;
    }

    @Override
    protected void drawableStateChanged() {
        drawableStateChanged(mBackground);
    }

    private void drawableStateChanged(Drawable d) {
        if (d != null && d.isStateful()) {
            d.setState(getDrawableState());
        }
    }

    @Override
    public void drawableHotspotChanged(float x, float y) {
        if (mBackground != null) {
            mBackground.setHotspot(x, y);
        }
    }

    public void setActualHeight(int actualHeight) {
        mActualHeight = actualHeight;
        invalidate();
    }

    public int getActualHeight() {
        return mActualHeight;
    }

    public void setClipTopAmount(int clipTopAmount) {
        mClipTopAmount = clipTopAmount;
        invalidate();
    }

    @Override
    public boolean hasOverlappingRendering() {

        // Prevents this view from creating a layer when alpha is animating.
        return false;
    }
}
```
