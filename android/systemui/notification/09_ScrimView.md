```

/** 纱布,沙罩
 * A view which can draw a scrim
 */
public class ScrimView extends View
{
    private int mScrimColor;
    private boolean mIsEmpty = true;
    private boolean mDrawAsSrc;
    private float mViewAlpha = 1.0f;
    private ValueAnimator mAlphaAnimator;
    private ValueAnimator.AnimatorUpdateListener mAlphaUpdateListener
            = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mViewAlpha = (float) animation.getAnimatedValue();
            invalidate();
        }
    };
    private AnimatorListenerAdapter mClearAnimatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            mAlphaAnimator = null;
        }
    };

    public ScrimView(Context context) {
        this(context, null);
    }

    public ScrimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrimView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ScrimView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawAsSrc || (!mIsEmpty && mViewAlpha > 0f)) {
            PorterDuff.Mode mode = mDrawAsSrc ? PorterDuff.Mode.SRC : PorterDuff.Mode.SRC_OVER;
            int color = mScrimColor;
            color = Color.argb((int) (Color.alpha(color) * mViewAlpha), Color.red(color),
                    Color.green(color), Color.blue(color));
            canvas.drawColor(color, mode);
        }
    }

    public void setDrawAsSrc(boolean asSrc) {
        mDrawAsSrc = asSrc;
        invalidate();
    }

    public void setScrimColor(int color) {
        if (color != mScrimColor) {
            mIsEmpty = Color.alpha(color) == 0;
            mScrimColor = color;
            invalidate();
        }
    }

    public int getScrimColor() {
        return mScrimColor;
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    public void setViewAlpha(float alpha) {
        if (mAlphaAnimator != null) {
            mAlphaAnimator.cancel();
        }
        mViewAlpha = alpha;
        invalidate();
    }

    public void animateViewAlpha(float alpha, long durationOut, Interpolator interpolator) {
        if (mAlphaAnimator != null) {
            mAlphaAnimator.cancel();
        }
        mAlphaAnimator = ValueAnimator.ofFloat(mViewAlpha, alpha);
        mAlphaAnimator.addUpdateListener(mAlphaUpdateListener);
        mAlphaAnimator.addListener(mClearAnimatorListener);
        mAlphaAnimator.setInterpolator(interpolator);
        mAlphaAnimator.setDuration(durationOut);
        mAlphaAnimator.start();
    }
}
```