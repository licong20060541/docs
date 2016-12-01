```

补充：dual：二重的

caret：插入符号

自己是垂直布局，
		第一个view：内部新建了一个水平的LinearLayout，addview到了自己
	       第二个view： mSecondLine


  if (Objects.equals(newText, mText)) return;


Whitespace     //空白         Character.isWhitespace()




/**
 * Text displayed over one or two lines, centered horizontally.  A caret is always drawn at the end
 * of the first line, and considered part of the content for centering purposes.
 *           ：文字显示在一行或两行上
 * Text overflow 溢出 rules:
 *   First line: break on a word, unless a single word takes up the entire line - in which case
 *               truncate截断. 超过自己范围了就截断，剩余的交给 Second line，哈哈，这种情况下才可见
 *   Second line: ellipsis省略  if necessary，文字过多就省略
 */
public class QSDualTileLabel extends LinearLayout {

    private final Context mContext;
    private final TextView mFirstLine;
    private final ImageView mFirstLineCaret;
    private final TextView mSecondLine;
    private final int mHorizontalPaddingPx;
    private String mText;

    public QSDualTileLabel(Context context) {
        super(context);
        mContext = context;
        setOrientation(LinearLayout.VERTICAL);

        mHorizontalPaddingPx = mContext.getResources()
                .getDimensionPixelSize(R.dimen.qs_dual_tile_padding_horizontal);

        mFirstLine = initTextView();
        mFirstLine.setPadding(mHorizontalPaddingPx, 0, mHorizontalPaddingPx, 0);

        final LinearLayout firstLineLayout = new LinearLayout(mContext);
        firstLineLayout.setPadding(0, 0, 0, 0);
        firstLineLayout.setOrientation(LinearLayout.HORIZONTAL);
        firstLineLayout.setClickable(false);
        firstLineLayout.setBackground(null);
        firstLineLayout.addView(mFirstLine);

        mFirstLineCaret = new ImageView(mContext);
        mFirstLineCaret.setScaleType(ImageView.ScaleType.MATRIX);
        mFirstLineCaret.setClickable(false);
        firstLineLayout.addView(mFirstLineCaret);

        addView(firstLineLayout, newLinearLayoutParams());

        mSecondLine = initTextView();
        mSecondLine.setPadding(mHorizontalPaddingPx, 0, mHorizontalPaddingPx, 0);
        mSecondLine.setEllipsize(TruncateAt.END); // 省略模式
        mSecondLine.setVisibility(GONE); //下面某种情况会显示
        addView(mSecondLine, newLinearLayoutParams());

        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right,
                    int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if ((oldRight - oldLeft) != (right - left)) {
                    rescheduleUpdateText();
                }
            }
        });
    }
    private static LayoutParams newLinearLayoutParams() {
        final LayoutParams lp =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        return lp;
    }
    public void setFirstLineCaret(Drawable d) {
        mFirstLineCaret.setImageDrawable(d);
        if (d != null) {
            final int h = d.getIntrinsicHeight();
            mFirstLine.setMinHeight(h);
            mFirstLine.setPadding(mHorizontalPaddingPx, 0, 0, 0);
        }
    }
    private TextView initTextView() {
        final TextView tv = new TextView(mContext);
        tv.setPadding(0, 0, 0, 0);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setSingleLine(true);
        tv.setClickable(false);
        tv.setBackground(null);
        return tv;
    }
    public void setText(CharSequence text) {
        final String newText = text == null ? null : text.toString().trim();
        if (Objects.equals(newText, mText)) return;
        mText = newText;
        rescheduleUpdateText();
    }
    public String getText() {
        return mText;
    }
    public void setTextSize(int unit, float size) {
        mFirstLine.setTextSize(unit, size);
        mSecondLine.setTextSize(unit, size);
        rescheduleUpdateText();
    }
    public void setTextColor(int color) {
        mFirstLine.setTextColor(color);
        mSecondLine.setTextColor(color);
        rescheduleUpdateText();
    }
    public void setTypeface(Typeface tf) {
        mFirstLine.setTypeface(tf);
        mSecondLine.setTypeface(tf);
        rescheduleUpdateText();
    }
    private void rescheduleUpdateText() {
        removeCallbacks(mUpdateText);
        post(mUpdateText);
    }
    private void updateText() {
        if (getWidth() == 0) return;
        if (TextUtils.isEmpty(mText)) {
            mFirstLine.setText(null);
            mSecondLine.setText(null);
            mSecondLine.setVisibility(GONE);
            return;
        }
        final float maxWidth = getWidth() - mFirstLineCaret.getWidth() - mHorizontalPaddingPx
                - getPaddingLeft() - getPaddingRight(); // mHorizontalPaddingPx？？目的呢，不管了
        float width = mFirstLine.getPaint().measureText(mText);
        if (width <= maxWidth) {
            mFirstLine.setText(mText);
            mSecondLine.setText(null);
            mSecondLine.setVisibility(GONE);
            return;
        }
        final int n = mText.length();
        int lastWordBoundary = -1;
        boolean inWhitespace = false; //空白
        int i = 0;
        for (i = 1; i < n; i++) {
            width = mFirstLine.getPaint().measureText(mText.substring(0, i));
            final boolean done = width > maxWidth;
            if (Character.isWhitespace(mText.charAt(i))) { // 这里是将连续空白去除
                if (!inWhitespace && !done) { // 并保存到了 lastWordBoundary，
                    lastWordBoundary = i; // 因此，结果就会是第一个空格前的内容啦啦阿拉
                }
                inWhitespace = true;
            } else {
                inWhitespace = false;
            }
            if (done) {
                break;
            }
        }
        if (lastWordBoundary == -1) {
            lastWordBoundary = i – 1; // 没有空格的处理
        }
        mFirstLine.setText(mText.substring(0, lastWordBoundary));
        mSecondLine.setText(mText.substring(lastWordBoundary).trim()); // 剩余的交给second了哈哈end省略
        mSecondLine.setVisibility(VISIBLE); // 终于显示
    }
    private final Runnable mUpdateText = new Runnable() {
        @Override
        public void run() {
            updateText();
        }
    };
}
```