```

@RemoteView
public class AnimatedImageView extends ImageView {
    AnimationDrawable mAnim;
    boolean mAttached;

    public AnimatedImageView(Context context) {
        super(context);
    }

    public AnimatedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void updateAnim() {
        Drawable drawable = getDrawable();
        if (mAttached && mAnim != null) {
            mAnim.stop();
        }
        if (drawable instanceof AnimationDrawable) {
            mAnim = (AnimationDrawable)drawable;
            if (isShown()) {
                mAnim.start();
            }
        } else {
            mAnim = null;
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        updateAnim();
    }

    @Override
    @android.view.RemotableViewMethod
    public void setImageResource(int resid) {
        super.setImageResource(resid);
        updateAnim();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAttached = true;
        updateAnim();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnim != null) {
            mAnim.stop();
        }
        mAttached = false;
    }

    @Override
    protected void onVisibilityChanged(View changedView, int vis) {
        super.onVisibilityChanged(changedView, vis);
        if (mAnim != null) {
            if (isShown()) {
                mAnim.start();
            } else {
                mAnim.stop();
            }
        }
    }
}


```