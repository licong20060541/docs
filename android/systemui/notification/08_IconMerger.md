
// 一定容量的LinearLayout，当过多时显示more icon

```

public class IconMerger extends LinearLayout {
    private static final String TAG = "IconMerger";
    private static final boolean DEBUG = false;

    private int mIconSize;
    private View mMoreView;

    public IconMerger(Context context, AttributeSet attrs) {
        super(context, attrs);

        mIconSize = context.getResources().getDimensionPixelSize(
                R.dimen.status_bar_icon_size);

        if (DEBUG) {
            setBackgroundColor(0x800099FF);
        }
    }

    public void setOverflowIndicator(View v) {
        mMoreView = v;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // we need to constrain this to an integral multiple of our children
        int width = getMeasuredWidth();
        setMeasuredDimension(width - (width % mIconSize), getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        checkOverflow(r - l);
    }

    private void checkOverflow(int width) {
        if (mMoreView == null)
            return;

        final int N = getChildCount();
        int visibleChildren = 0;
        for (int i = 0; i < N; i++) {
            if (getChildAt(i).getVisibility() != GONE)
                visibleChildren++;
        }
        final boolean overflowShown = (mMoreView.getVisibility() == View.VISIBLE);
        // let's assume we have one more slot if the more icon is already
        // showing
        if (overflowShown)
            visibleChildren--;
        final boolean moreRequired = visibleChildren * mIconSize > width;
        if (moreRequired != overflowShown) {
            post(new Runnable() {
                @Override
                public void run() {
                    mMoreView.setVisibility(moreRequired ? View.VISIBLE : View.GONE);
                }
            });
        }
    }
}
```