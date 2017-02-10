# 抽象wrap view

```
/**
 * Wraps the actual notification content view; used to implement behaviors which are different for
 * the individual templates and custom views.
 */
public abstract class NotificationViewWrapper {

    private static final String TAG_BIG_MEDIA_NARROW = "bigMediaNarrow";
    private static final String TAG_MEDIA = "media";
    private static final String TAG_BIG_PICTURE = "bigPicture";

    protected final View mView;

     //  静态类
    public static NotificationViewWrapper wrap(Context ctx, View v) {
        if (v.getId() == com.android.internal.R.id.status_bar_latest_event_content) {
            if (TAG_BIG_MEDIA_NARROW.equals(v.getTag())) {
                return new NotificationBigMediaNarrowViewWrapper(ctx, v);
            } else if (TAG_MEDIA.equals(v.getTag())) {
                return new NotificationMediaViewWrapper(ctx, v);
            } else if (TAG_BIG_PICTURE.equals(v.getTag())) {
                return new NotificationBigMediaNarrowViewWrapper(ctx, v);
            } else {
                return new NotificationTemplateViewWrapper(ctx, v);
            }
        } else {
            return new NotificationCustomViewWrapper(v);
        }
    }
```

# 之一wrap

```

/**
 * Wraps a notification containing a custom view.
 */
public class NotificationCustomViewWrapper extends NotificationViewWrapper {

    private final ViewInvertHelper mInvertHelper;

    protected NotificationCustomViewWrapper(View view) {
        super(view);
        mInvertHelper = new ViewInvertHelper(view, NotificationPanelView.DOZE_ANIMATION_DURATION);
    }

    @Override
    public void setDark(boolean dark, boolean fade, long delay) {
        if (fade) {
            mInvertHelper.fade(dark, delay);
        } else {
            mInvertHelper.update(dark);
        }
    }

    @Override
    public boolean needsRoundRectClipping() {
        return true;
    }
}
```

# ViewInvertHelper

```

/**
 * Helper to invert the colors of views and fade between the states.
 * 
 */
 // 反转颜色和切换状态
public class ViewInvertHelper {

    private final Paint mDarkPaint = new Paint();
    private final Interpolator mLinearOutSlowInInterpolator;
    private final View mTarget;
    private final ColorMatrix mMatrix = new ColorMatrix();
    private final ColorMatrix mGrayscaleMatrix = new ColorMatrix();
    private final long mFadeDuration;

    public ViewInvertHelper(View target, long fadeDuration) {
        mTarget = target;
        mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(mTarget.getContext(),
                android.R.interpolator.linear_out_slow_in);
        mFadeDuration = fadeDuration;
    }

    public void fade(final boolean invert, long delay) {
        float startIntensity = invert ? 0f : 1f;
        float endIntensity = invert ? 1f : 0f;
        ValueAnimator animator = ValueAnimator.ofFloat(startIntensity, endIntensity);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateInvertPaint((Float) animation.getAnimatedValue());
                 // 应用改变
                mTarget.setLayerType(View.LAYER_TYPE_HARDWARE, mDarkPaint);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!invert) {
                    mTarget.setLayerType(View.LAYER_TYPE_NONE, null);
                }
            }
        });
        animator.setDuration(mFadeDuration);
        animator.setInterpolator(mLinearOutSlowInInterpolator);
        animator.setStartDelay(delay);
        animator.start();
    }

    public void update(boolean invert) {
        if (invert) {
            updateInvertPaint(1f);
            // 应用改变
            mTarget.setLayerType(View.LAYER_TYPE_HARDWARE, mDarkPaint);
        } else {
            mTarget.setLayerType(View.LAYER_TYPE_NONE, null);
        }
    }

    public View getTarget() {
        return mTarget;
    }

    private void updateInvertPaint(float intensity) {
        float components = 1 - 2 * intensity;
        final float[] invert = {
                components, 0f,         0f,         0f, 255f * intensity,
                0f,         components, 0f,         0f, 255f * intensity,
                0f,         0f,         components, 0f, 255f * intensity,
                0f,         0f,         0f,         1f, 0f
        };
        mMatrix.set(invert);
        mGrayscaleMatrix.setSaturation(1 - intensity);
        mMatrix.preConcat(mGrayscaleMatrix);
        // 改变存储于这
        mDarkPaint.setColorFilter(new ColorMatrixColorFilter(mMatrix)); 
    }
}
```
