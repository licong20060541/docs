此代码为M的，在N上已大幅修改了，可以实现了消息组管理功能

```
// basic

/**
 * A container containing child notifications
 */
public class NotificationChildrenContainer extends ViewGroup {

    private final int mChildPadding;
    private final int mDividerHeight;
    private final int mMaxNotificationHeight;
    private final List<View> mDividers = new ArrayList<>();
    private final List<ExpandableNotificationRow> mChildren = new ArrayList<>();
    private final View mCollapseButton;
    private final View mCollapseDivider;
    private final int mCollapseButtonHeight;
    private final int mNotificationAppearDistance;

    public NotificationChildrenContainer(Context context) {
        this(context, null);
    }

    public NotificationChildrenContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NotificationChildrenContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NotificationChildrenContainer(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mChildPadding = getResources().getDimensionPixelSize(
                R.dimen.notification_children_padding);
        mDividerHeight = getResources().getDimensionPixelSize(
                R.dimen.notification_children_divider_height);
        mMaxNotificationHeight = getResources().getDimensionPixelSize(
                R.dimen.notification_max_height);// ！！！！！！！！！！！！！！！！！！！！！
        mNotificationAppearDistance = getResources().getDimensionPixelSize(
                R.dimen.notification_appear_distance);
        LayoutInflater inflater = mContext.getSystemService(LayoutInflater.class);// ！
        mCollapseButton = inflater.inflate(R.layout.notification_collapse_button, this,
                false);
        mCollapseButtonHeight = getResources().getDimensionPixelSize(
                R.dimen.notification_bottom_decor_height);
        addView(mCollapseButton);// ！
        mCollapseDivider = inflateDivider();
        addView(mCollapseDivider);// ！
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {//和前面NotificationContent有点像
        int ownMaxHeight = mMaxNotificationHeight;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        boolean hasFixedHeight = heightMode == MeasureSpec.EXACTLY;
        boolean isHeightLimited = heightMode == MeasureSpec.AT_MOST;
        if (hasFixedHeight || isHeightLimited) {
            int size = MeasureSpec.getSize(heightMeasureSpec);
            ownMaxHeight = Math.min(ownMaxHeight, size);
        }
        int newHeightSpec = MeasureSpec.makeMeasureSpec(ownMaxHeight, MeasureSpec.AT_MOST);
        int dividerHeightSpec = MeasureSpec.makeMeasureSpec(mDividerHeight, MeasureSpec.EXACTLY);
        int collapseButtonHeightSpec = MeasureSpec.makeMeasureSpec(mCollapseButtonHeight,
                MeasureSpec.EXACTLY);
        mCollapseButton.measure(widthMeasureSpec, collapseButtonHeightSpec);
        mCollapseDivider.measure(widthMeasureSpec, dividerHeightSpec);
        int height = mCollapseButtonHeight;
        int childCount = mChildren.size();
        boolean firstChild = true;
        for (int i = 0; i < childCount; i++) {
            View child = mChildren.get(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            child.measure(widthMeasureSpec, newHeightSpec);
            height += child.getMeasuredHeight();
            if (!firstChild) {
                // layout the divider
                View divider = mDividers.get(i - 1);
                divider.measure(widthMeasureSpec, dividerHeightSpec);
                height += mChildPadding;
            } else {
                firstChild = false;
            }
        }
        int width = MeasureSpec.getSize(widthMeasureSpec);
        height = hasFixedHeight ? ownMaxHeight
                : isHeightLimited ? Math.min(ownMaxHeight, height)
                : height;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = mChildren.size();
        boolean firstChild = true;
        for (int i = 0; i < childCount; i++) {
            View child = mChildren.get(i);
            boolean viewGone = child.getVisibility() == View.GONE;
            if (i != 0) {
                View divider = mDividers.get(i - 1);
                int dividerVisibility = divider.getVisibility();
                int newVisibility = viewGone ? INVISIBLE : VISIBLE;
                if (dividerVisibility != newVisibility) {
                    divider.setVisibility(newVisibility);
                }
            }
            if (viewGone) {
                continue;
            }
            child.layout(0, 0, getWidth(), child.getMeasuredHeight());// !!!! 搞笑呢，都重叠了
            if (!firstChild) {
                mDividers.get(i - 1).layout(0, 0, getWidth(), mDividerHeight);
            } else {
                firstChild = false;
            }
        }
        mCollapseButton.layout(0, 0, getWidth(), mCollapseButtonHeight);
        mCollapseDivider.layout(0, mCollapseButtonHeight - mDividerHeight, getWidth(),
                mCollapseButtonHeight);
    }

```

```
   // N上简要，可以看出
    public void setNotificationParent(ExpandableNotificationRow parent) {
        mNotificationParent = parent;
        mHeaderUtil = new NotificationHeaderUtil(mNotificationParent);
    }
```

# 增删

```

    /**
     * Add a child notification to this view.
     *
     * @param row the row to add
     * @param childIndex the index to add it at, if -1 it will be added at the end
     */
    public void addNotification(ExpandableNotificationRow row, int childIndex) {
        int newIndex = childIndex < 0 ? mChildren.size() : childIndex;
        mChildren.add(newIndex, row);
        addView(row);
        if (mChildren.size() != 1) {
            View divider = inflateDivider();
            addView(divider);
            mDividers.add(Math.max(newIndex - 1, 0), divider);
        }
        // TODO: adapt background corners
        // TODO: fix overdraw
    }

    public void removeNotification(ExpandableNotificationRow row) {
        int childIndex = mChildren.indexOf(row);
        mChildren.remove(row);
        removeView(row);
        if (!mDividers.isEmpty()) {
            View divider = mDividers.remove(Math.max(childIndex - 1, 0));
            removeView(divider);
        }
        row.setSystemChildExpanded(false);
        // TODO: adapt background corners
    }
```


# TODO

```

    /**
     * Apply the order given in the list to the children.
     *
     * @param childOrder the new list order
     * @return whether the list order has changed
     */
    public boolean applyChildOrder(List<ExpandableNotificationRow> childOrder) {
        if (childOrder == null) {
            return false;
        }
        boolean result = false;
        for (int i = 0; i < mChildren.size() && i < childOrder.size(); i++) {
            ExpandableNotificationRow child = mChildren.get(i);
            ExpandableNotificationRow desiredChild = childOrder.get(i);
            if (child != desiredChild) {
                mChildren.remove(desiredChild);
                mChildren.add(i, desiredChild);
                result = true;
            }
        }

        // Let's make the first child expanded!
        boolean first = true;
        for (int i = 0; i < childOrder.size(); i++) {
            ExpandableNotificationRow child = childOrder.get(i);
            child.setSystemChildExpanded(first);
            first = false;
        }
        return result;
    }

// todo
```
