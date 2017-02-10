```

/**
 * A view who contains media artwork.
 */
public class BackDropView extends FrameLayout
{
    private Runnable mOnVisibilityChangedRunnable;

    public BackDropView(Context context) {
        super(context);
    }

    public BackDropView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackDropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BackDropView(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (changedView == this && mOnVisibilityChangedRunnable != null) {
            mOnVisibilityChangedRunnable.run();
        }
    }

    public void setOnVisibilityChangedRunnable(Runnable runnable) {
        mOnVisibilityChangedRunnable = runnable;
    }

}
```