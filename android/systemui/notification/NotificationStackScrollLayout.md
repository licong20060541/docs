变换ev的坐标
```
    private void transformTouchEvent(MotionEvent ev, View sourceView, View targetView) {
        ev.offsetLocation(sourceView.getX(), sourceView.getY());
        ev.offsetLocation(-targetView.getX(), -targetView.getY());
    }
    
            transformTouchEvent(ev, this, mScrollView);
            if (mScrollView.onInterceptTouchEvent(ev)) {
                mDelegateToScrollView = true;
                removeLongPressCallback();
                return true;
            }
            transformTouchEvent(ev, mScrollView, this);
    
```

onInterceptTouchEvent
```
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 1
        if (mInterceptDelegateEnabled) {
            transformTouchEvent(ev, this, mScrollView);
            if (mScrollView.onInterceptTouchEvent(ev)) {
                mDelegateToScrollView = true;
                removeLongPressCallback();
                return true;
            }
            transformTouchEvent(ev, mScrollView, this);
        }
        initDownStates(ev);
        handleEmptySpaceClick(ev);
        boolean expandWantsIt = false;
        
        // 2
        if (!mSwipingInProgress && !mOnlyScrollingInThisMotion && isScrollingEnabled()) {
            expandWantsIt = mExpandHelper.onInterceptTouchEvent(ev);
        }
        
        // 3
        boolean scrollWantsIt = false;
        if (!mSwipingInProgress && !mExpandingNotification) {
            scrollWantsIt = onInterceptTouchEventScroll(ev);
        }
        
        // 4
        boolean swipeWantsIt = false;
        if (!mIsBeingDragged
                && !mExpandingNotification
                && !mExpandedInThisMotion
                && !mOnlyScrollingInThisMotion) {
            swipeWantsIt = mSwipeHelper.onInterceptTouchEvent(ev);
        }
        return swipeWantsIt || scrollWantsIt || expandWantsIt || super.onInterceptTouchEvent(ev);
    }
```


create down event

```
    private void dispatchDownEventToScroller(MotionEvent ev) {
        MotionEvent downEvent = MotionEvent.obtain(ev);
        downEvent.setAction(MotionEvent.ACTION_DOWN);
        onScrollTouch(downEvent);
        downEvent.recycle();
    }
```