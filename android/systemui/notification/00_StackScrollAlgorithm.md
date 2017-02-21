```
AmbientState的mScrollY由NotificationStackScrollLayout设置，代表了
NotificationStackScrollLayout的滑动距离。

int scrollY = ambientState.getScrollY();

        // Due to the overScroller, the stackscroller can have negative scroll state. This is
        // already accounted for by the top padding and doesn't need an additional adaption
// StackScrollAlgorithmState的scrollY：The scroll position of the algorithm
scrollY = Math.max(0, scrollY);
algorithmState.scrollY = (int) (scrollY + mCollapsedSize + bottomOverScroll);

初始值
algorithmState.scrollY = 64
当向上滑动时不断增大algorithmState.scrollY = 190

并且mCollapsedSize=64

```

```
// Find the number of items in the top stack 顶栈 and update the result state if needed.
// 与滑动距离有关，比如滑动到最大时，顶部会重叠，
// 本应该滑动出去的，但是也留着显示了，并且越靠前，优先级越高，压着下面的
findNumberOfItemsInTopStackAndUpdateState

algorithmState.itemsInTopStack在其中的数量

/** log
I/licong  ( 4719): findNumberOfItemsInTopStackAndUpdateStatey==== 
I/licong  ( 4719): yPositionInScrollView = 0.0 第一个完全显示的
I/licong  ( 4719): algorithmState.scrollY = 188
I/licong  ( 4719): findNumberOfItemsInTopStackAndUpdateStatey==== 
I/licong  ( 4719): yPositionInScrollView = 68.0 第2个完全显示的
I/licong  ( 4719): algorithmState.scrollY = 188
I/licong  ( 4719): findNumberOfItemsInTopStackAndUpdateStatey==== 
I/licong  ( 4719): yPositionInScrollView = 136.0 此处break了，没有循环了
I/licong  ( 4719): algorithmState.scrollY = 188
I/licong  ( 4719): ：before scrollY=188
I/licong  ( 4719): ： mCollapsedSize=64
I/licong  ( 4719): ： bottomOverScroll=0.0
I/licong  ( 4719): ： scrollY=189


I/licong  ( 4719): findNumberOfItemsInTopStackAndUpdateStatey==== 
I/licong  ( 4719): yPositionInScrollView = 0.0
I/licong  ( 4719): algorithmState.scrollY = 64
I/licong  ( 4719): findNumberOfItemsInTopStackAndUpdateStatey==== 
I/licong  ( 4719): yPositionInScrollView = 175.0
I/licong  ( 4719): algorithmState.scrollY = 64
I/licong  ( 4719): ：before scrollY=64
I/licong  ( 4719): ： mCollapsedSize=64
I/licong  ( 4719): ： bottomOverScroll=0.0
I/licong  ( 4719): ： scrollY=64


**/


```


```
    /**
     * Find the number of items in the top stack and update the result state if needed.
     *
     * @param resultState The result state to update if a height change of an child occurs
     * @param algorithmState The state in which the current pass of the algorithm is currently in
     */
    private void findNumberOfItemsInTopStackAndUpdateState(StackScrollState resultState,
            StackScrollAlgorithmState algorithmState) {

        // The y Position if the element would be in a regular scrollView
        float yPositionInScrollView = 0.0f;
        int childCount = algorithmState.visibleChildren.size();

        // find the number of elements in the top stack.
        for (int i = 0; i < childCount; i++) { 
            ExpandableView child = algorithmState.visibleChildren.get(i);
            StackScrollState.ViewState childViewState = resultState.getViewStateForView(child);
            int childHeight = getMaxAllowedChildHeight(child);
            float yPositionInScrollViewAfterElement = yPositionInScrollView
                    + childHeight
                    + mPaddingBetweenElements;
            Log.i("licong", "findNumberOfItemsInTopStackAndUpdateStatey==== ");
            Log.i("licong", "yPositionInScrollView = " + yPositionInScrollView);
            Log.i("licong", "algorithmState.scrollY = " + algorithmState.scrollY);
            if (yPositionInScrollView < algorithmState.scrollY) {
                if (i == 0 && algorithmState.scrollY <= mCollapsedSize) {
                       // 一般这个都会满足
                    // The starting position of the bottom stack peek
                    int bottomPeekStart = mInnerHeight - mBottomStackPeekSize -
                            mCollapseSecondCardPadding;
                    // Collapse and expand the first child while the shade is being expanded
                    float maxHeight = mIsExpansionChanging && child == mFirstChildWhileExpanding
                            ? mFirstChildMaxHeight
                            : childHeight;
                    childViewState.height = (int) Math.max(Math.min(bottomPeekStart, maxHeight),
                            mCollapsedSize);
                    algorithmState.itemsInTopStack = 1.0f; // 加1

                } else if (yPositionInScrollViewAfterElement < algorithmState.scrollY) {
                    // 整个元素都在里面，则加1
                    // According to the regular scroll view we are fully off screen
                    algorithmState.itemsInTopStack += 1.0f;
                    if (i == 0) {
                        childViewState.height = mCollapsedSize;
                    }
                } else {
                     // 当前元素的位置，超出了滑动的范围
                    // According to the regular scroll view we are partially off screen

                    // How much did we scroll into this child
                    // 计算下这个元素有多是在滑动距离范围内的
                    algorithmState.scrolledPixelsTop = algorithmState.scrollY
                            - yPositionInScrollView;
                    algorithmState.partialInTop = (algorithmState.scrolledPixelsTop) / (childHeight
                            + mPaddingBetweenElements);

                    // Our element can be expanded, so this can get negative
                    // 加上这个小范围的值
                    algorithmState.partialInTop = Math.max(0.0f, algorithmState.partialInTop);
                    algorithmState.itemsInTopStack += algorithmState.partialInTop;

                    if (i == 0) {
                        // If it is expanded we have to collapse it to a new size
                        float newSize = yPositionInScrollViewAfterElement
                                - mPaddingBetweenElements
                                - algorithmState.scrollY + mCollapsedSize;
                        newSize = Math.max(mCollapsedSize, newSize);
                        algorithmState.itemsInTopStack = 1.0f;
                        childViewState.height = (int) newSize;
                    }
                    algorithmState.lastTopStackIndex = i;
                    break;
                }
            } else {
                algorithmState.lastTopStackIndex = i - 1;
                // We are already past the stack so we can end the loop
                break;
            }
            yPositionInScrollView = yPositionInScrollViewAfterElement;
        }
    }

```

```

    class StackScrollAlgorithmState {

        /**
         * The scroll position of the algorithm-见上
         */
        public int scrollY;

        /**
         *  The quantity of items which are in the top stack.-见上
         */
        public float itemsInTopStack;

        /**
         * how far in is the element currently transitioning into the top stack-见上
         */
        public float partialInTop;

        /**
         * The number of pixels the last child in the top stack has scrolled in to the stack-见上
         */
        public float scrolledPixelsTop;

        /**
         * The last item index which is in the top stack.-见上
         */
        public int lastTopStackIndex;

        /**
         * The quantity of items which are in the bottom stack.对比top
         */
        public float itemsInBottomStack;

        /**
         * how far in is the element currently transitioning into the bottom stack对比top
         */
        public float partialInBottom;

        /**
         * The children from the host view which are not gone.
         */
        public final ArrayList<ExpandableView> visibleChildren = new ArrayList<ExpandableView>();
    }
```


```
    private void updateClipping(StackScrollState resultState,
            StackScrollAlgorithmState algorithmState) {
   }
   // 关键代码：
   clipHeight = newNotificationEnd - previousNotificationEnd;

```

updatePositionsForState()



```
    if (i == algorithmState.lastTopStackIndex + 1) {
    // 注释很明白，就是顶部栈和底部堆栈
                // Normally the position of this child is the position in the regular scrollview,
                // but if the two stacks are very close to each other,
                // then have have to push it even more upwards to the position of the bottom
                // stack start.
                currentYPosition = Math.min(scrollOffset, bottomStackStart);
            }
            
            
    if (i <= algorithmState.lastTopStackIndex) {
                // Case 1: 顶部栈
                // We are in the top Stack
                updateStateForTopStackChild(algorithmState,
                        numberOfElementsCompletelyIn, i, childHeight, childViewState, scrollOffset);
                clampPositionToTopStackEnd(childViewState, childHeight);

                // check if we are overlapping with the bottom stack
                if (childViewState.yTranslation + childHeight + mPaddingBetweenElements
                        >= bottomStackStart && !mIsExpansionChanging && i != 0 && mIsSmallScreen) {
                    // we just collapse this element slightly
                    int newSize = (int) Math.max(bottomStackStart - mPaddingBetweenElements -
                            childViewState.yTranslation, mCollapsedSize);
                    childViewState.height = newSize;
                    updateStateForChildTransitioningInBottom(algorithmState, bottomStackStart,
                            bottomPeekStart, childViewState.yTranslation, childViewState,
                            childHeight);
                }
                clampPositionToBottomStackStart(childViewState, childViewState.height);
            } else if (nextYPosition >= bottomStackStart) {
                // Case 2:底部堆栈
                // We are in the bottom stack.
                if (currentYPosition >= bottomStackStart) {
                    // According to the regular scroll view we are fully translated out of the
                    // bottom of the screen so we are fully in the bottom stack
                    updateStateForChildFullyInBottomStack(algorithmState,
                            bottomStackStart, childViewState, childHeight);
                } else {
                    // According to the regular scroll view we are currently translating out of /
                    // into the bottom of the screen
                    updateStateForChildTransitioningInBottom(algorithmState,
                            bottomStackStart, bottomPeekStart, currentYPosition,
                            childViewState, childHeight);
                }
            } else {
                // Case 3:
                // We are in the regular scroll area.正常区域
                childViewState.location = StackScrollState.ViewState.LOCATION_MAIN_AREA;
                clampYTranslation(childViewState, childHeight);
            }

            // The first card is always rendered.
            if (i == 0) { // 特殊处理
                childViewState.alpha = 1.0f;
                childViewState.yTranslation = Math.max(mCollapsedSize - algorithmState.scrollY, 0);
                if (childViewState.yTranslation + childViewState.height
                        > bottomPeekStart - mCollapseSecondCardPadding) {
                    childViewState.height = (int) Math.max(
                            bottomPeekStart - mCollapseSecondCardPadding
                                    - childViewState.yTranslation, mCollapsedSize);
                }
                childViewState.location = StackScrollState.ViewState.LOCATION_FIRST_CARD;
            }
            if (childViewState.location == StackScrollState.ViewState.LOCATION_UNKNOWN) {
                Log.wtf(LOG_TAG, "Failed to assign location for child " + i);
            }
            currentYPosition = childViewState.yTranslation + childHeight + mPaddingBetweenElements;
            yPositionInScrollView = yPositionInScrollViewAfterElement;

            childViewState.yTranslation += mTopPadding;
        }
    }
```
```
补充：状态
        public static final int LOCATION_UNKNOWN = 0x00;
        public static final int LOCATION_FIRST_CARD = 0x01;
        public static final int LOCATION_TOP_STACK_HIDDEN = 0x02;
        public static final int LOCATION_TOP_STACK_PEEKING = 0x04;
        public static final int LOCATION_MAIN_AREA = 0x08;
        public static final int LOCATION_BOTTOM_STACK_PEEKING = 0x10;
        public static final int LOCATION_BOTTOM_STACK_HIDDEN = 0x20;
```

