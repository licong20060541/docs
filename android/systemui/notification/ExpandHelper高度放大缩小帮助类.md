# 向外回调

```
    public interface Callback {
        ExpandableView getChildAtRawPosition(float x, float y);
        ExpandableView getChildAtPosition(float x, float y);
        boolean canChildBeExpanded(View v);
        void setUserExpandedChild(View v, boolean userExpanded);
        void setUserLockedChild(View v, boolean userLocked);
        void expansionStateChanged(boolean isExpanding);
        int getMaxExpandHeight(ExpandableView view);
        void setExpansionCancelled(View view);
    }
/**
 * A scroll adapter which can be queried for meta information about the scroll state
 */
public interface ScrollAdapter {

    /**
     * @return Whether the view returned by {@link #getHostView()} is scrolled to the top
     */
    public boolean isScrolledToTop();

    /**
     * @return Whether the view returned by {@link #getHostView()} is scrolled to the bottom
     */
    public boolean isScrolledToBottom();

    /**
     * @return The view in which the scrolling is performed
     */
    public View getHostView();
}
```

使用

```

    /* Only ever called as a consequence of an expansion gesture in the shade. */
    @Override
    public void setUserExpandedChild(View v, boolean userExpanded) {
        if (v instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow row = (ExpandableNotificationRow) v;
            row.setUserExpanded(userExpanded, true /* allowChildrenExpansion */);
            row.onExpandedByGesture(userExpanded);
        }
    }

    @Override
    public void setUserLockedChild(View v, boolean userLocked) {
        if (v instanceof ExpandableNotificationRow) {
            ((ExpandableNotificationRow) v).setUserLocked(userLocked);
        }
        removeLongPressCallback();
        requestDisallowInterceptTouchEvent(true); // ！！！
    }
```


# 变量及基本函数

```
   private static final String TAG = "ExpandHelper";
    private static final float EXPAND_DURATION = 0.3f;

    // amount of overstretch for maximum brightness expressed in U
    // 2f: maximum brightness is stretching a 1U to 3U, or a 4U to 6U
    private static final float STRETCH_INTERVAL = 2f;

    private boolean mExpanding;
    // 滑动模式，什么触发了滑动
    private static final int NONE    = 0;//初始值
    // 百叶窗，父view的scroll为0，并且此时向下滑动了一段距离
    private static final int BLINDS  = 1<<0;
    // 多手指触摸会触发的
    private static final int PULL    = 1<<1;
    // ScaleGestureDetector的回调中onScaleBegin设置的
    private static final int STRETCH = 1<<2;
    
    private int mExpansionStyle = NONE;
    
    private boolean mWatchingForPull;
    private boolean mHasPopped;

    private Callback mCallback;
    // 根据接收的MotionEvent, 侦测由多个触点（多点触控）引发的变形手势
    private ScaleGestureDetector mSGD; 
    private ViewScaler mScaler;
    private ObjectAnimator mScaleAnimation;
    private boolean mEnabled = true;
    
    private View mEventSource; // 扩展view的父类，即mResizedView的父类
    private ExpandableView mResizedView; // 被扩展的view
    
    private float mCurrentHeight;

    private int mSmallSize;
    private int mLargeSize;
    private float mMaximumStretch;
    private boolean mOnlyMovements;

    private int mGravity;

    private ScrollAdapter mScrollAdapter;
    private FlingAnimationUtils mFlingAnimationUtils;
    private VelocityTracker mVelocityTracker;
```

```

    private OnScaleGestureListener mScaleGestureListener
            = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            if (DEBUG_SCALE) Log.v(TAG, "onscalebegin()");

            if (!mOnlyMovements) {
                startExpanding(mResizedView, STRETCH); // 切换模式！！！！！！！
            }
            return mExpanding;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (DEBUG_SCALE) Log.v(TAG, "onscale() on " + mResizedView);
            return true; // Do nothing
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    };

    private class ViewScaler {
        ExpandableView mView;

        public ViewScaler() {}
        public void setView(ExpandableView v) {
            mView = v;
        }
        public void setHeight(float h) {
            if (DEBUG_SCALE) Log.v(TAG, "SetHeight: setting to " + h);
            mView.setActualHeight((int) h); // 如何设置view的高度变化的呢？这里！！！！！！！！！！！！！！！！
            mCurrentHeight = h;
        }
        public float getHeight() {
            return mView.getActualHeight();
        }
        public int getNaturalHeight() {
            return mCallback.getMaxExpandHeight(mView);
        }
    }

    /**
     * Handle expansion gestures to expand and contract children of the callback.
     *
     * @param context application context
     * @param callback the container that holds the items to be manipulated
     * @param small the smallest allowable size for the manuipulated items.
     * @param large the largest allowable size for the manuipulated items.
     */
    public ExpandHelper(Context context, Callback callback, int small, int large) {
        mSmallSize = small;
        mMaximumStretch = mSmallSize * STRETCH_INTERVAL;
        mLargeSize = large;
        mContext = context;
        mCallback = callback;
        mScaler = new ViewScaler();
        mGravity = Gravity.TOP;
        mScaleAnimation = ObjectAnimator.ofFloat(mScaler, "height", 0f);
        mPullGestureMinXSpan = mContext.getResources().getDimension(R.dimen.pull_span_min);

        final ViewConfiguration configuration = ViewConfiguration.get(mContext);
        mTouchSlop = configuration.getScaledTouchSlop();

        mSGD = new ScaleGestureDetector(context, mScaleGestureListener);
        mFlingAnimationUtils = new FlingAnimationUtils(context, EXPAND_DURATION);
    }


    //  计算高度，更新view的大小！！！！！！！！！
    private void updateExpansion() {
        if (DEBUG_SCALE) Log.v(TAG, "updateExpansion()");
        // are we scaling or dragging?
        float span = mSGD.getCurrentSpan() - mInitialTouchSpan;
        span *= USE_SPAN ? 1f : 0f;
        float drag = mSGD.getFocusY() - mInitialTouchFocusY;
        drag *= USE_DRAG ? 1f : 0f;
        drag *= mGravity == Gravity.BOTTOM ? -1f : 1f;//？？
        float pull = Math.abs(drag) + Math.abs(span) + 1f;
        float hand = drag * Math.abs(drag) / pull + span * Math.abs(span) / pull;
        float target = hand + mOldHeight;
        float newHeight = clamp(target);
        mScaler.setHeight(newHeight);//  ！！！！！！！！！！！！
        mLastFocusY = mSGD.getFocusY();
        mLastSpanY = mSGD.getCurrentSpan();
    }

    private float clamp(float target) {
        float out = target;
        out = out < mSmallSize ? mSmallSize : out;
        out = out > mNaturalHeight ? mNaturalHeight : out;
        return out;
    }

    //  see
    private ExpandableView findView(float x, float y) {
        ExpandableView v;
        if (mEventSource != null) {
            int[] location = new int[2];
            mEventSource.getLocationOnScreen(location);
            x += location[0];
            y += location[1];
            v = mCallback.getChildAtRawPosition(x, y);
        } else {
            v = mCallback.getChildAtPosition(x, y);
        }
        return v;
    }

    // 是否在view的内部，可以是扩展view的父view哦
    private boolean isInside(View v, float x, float y) {
        if (DEBUG) Log.d(TAG, "isinside (" + x + ", " + y + ")");

        if (v == null) {
            if (DEBUG) Log.d(TAG, "isinside null subject");
            return false;
        }
        if (mEventSource != null) { // 计算在屏幕位置
            int[] location = new int[2];
            mEventSource.getLocationOnScreen(location);
            x += location[0];
            y += location[1];
            if (DEBUG) Log.d(TAG, "  to global (" + x + ", " + y + ")");
        }
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        x -= location[0];
        y -= location[1];
        if (DEBUG) Log.d(TAG, "  to local (" + x + ", " + y + ")");
        if (DEBUG) Log.d(TAG, "  inside (" + v.getWidth() + ", " + v.getHeight() + ")");
        boolean inside = (x > 0f && y > 0f && x < v.getWidth() & y < v.getHeight()); // ！！！
        return inside;
    }
```

# 触摸事件处理

```

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isEnabled()) {
            return false;
        }
        trackVelocity(ev);
        final int action = ev.getAction();

        // check for a spread-finger vertical pull gesture
        mSGD.onTouchEvent(ev);
        final int x = (int) mSGD.getFocusX();
        final int y = (int) mSGD.getFocusY();

        mInitialTouchFocusY = y;
        mInitialTouchSpan = mSGD.getCurrentSpan();
        mLastFocusY = mInitialTouchFocusY;
        mLastSpanY = mInitialTouchSpan;
        if (DEBUG_SCALE) Log.d(TAG, "set initial span: " + mInitialTouchSpan);

        if (mExpanding) {
            mLastMotionY = ev.getRawY();
            maybeRecycleVelocityTracker(ev);
            return true;
        } else {
            if ((action == MotionEvent.ACTION_MOVE) && 0 != (mExpansionStyle & BLINDS)) {
                // we've begun Venetian blinds style expansion 已经开始百叶窗模式啦，下拉事件已经发生了
                return true;
            }
            switch (action & MotionEvent.ACTION_MASK) {
            
            case MotionEvent.ACTION_DOWN:
                mWatchingForPull = mScrollAdapter != null &&
                        isInside(mScrollAdapter.getHostView(), x, y) // 是否在父view中
                        && mScrollAdapter.isScrolledToTop(); //  滑动到顶部了
                mResizedView = findView(x, y);//找到扩展view
                if (mResizedView != null && !mCallback.canChildBeExpanded(mResizedView)) {
                    mResizedView = null;
                    mWatchingForPull = false;
                }
                mInitialTouchY = ev.getRawY();
                mInitialTouchX = ev.getRawX();
                break;

            case MotionEvent.ACTION_MOVE: {
                final float xspan = mSGD.getCurrentSpanX();
                if (xspan > mPullGestureMinXSpan &&
                        xspan > mSGD.getCurrentSpanY() && !mExpanding) {
                    // detect a vertical pulling gesture with fingers somewhat separated
                    if (DEBUG_SCALE) Log.v(TAG, "got pull gesture (xspan=" + xspan + "px)");
                    startExpanding(mResizedView, PULL); // 多手指发生啦！！！可以扩展view了！！！！
                    mWatchingForPull = false;
                }
                if (mWatchingForPull) {
                    final float yDiff = ev.getRawY() - mInitialTouchY;
                    final float xDiff = ev.getRawX() - mInitialTouchX;
                    if (yDiff > mTouchSlop && yDiff > Math.abs(xDiff)) {// 下拉事件发生了，进入百叶窗模式
                        if (DEBUG) Log.v(TAG, "got venetian gesture (dy=" + yDiff + "px)");
                        mWatchingForPull = false;
                        if (mResizedView != null && !isFullyExpanded(mResizedView)) {
                            if (startExpanding(mResizedView, BLINDS)) {
                                mLastMotionY = ev.getRawY();
                                mInitialTouchY = ev.getRawY();
                                mHasPopped = false;
                            }
                        }
                    }
                }
                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (DEBUG) Log.d(TAG, "up/cancel");
                finishExpanding(false, getCurrentVelocity());
                clearView();
                break;
            }
            mLastMotionY = ev.getRawY();
            maybeRecycleVelocityTracker(ev);
            return mExpanding;
        }
    }
    // 总结， 1：单手指时，当父view可继续下滑时，不会放大缩小子view；当父view不可下滑后，这里可以监听其下拉事件，当发生下拉时，
    // 进入扩展view模式，即百叶窗:
    //    2：当多手指发生时，则直接进入pull模式，也可以扩展view了；
    //    3：  否则，当检测到手势如放大缩小时在回调中设置模式，进入扩展；
```


```

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isEnabled()) {
            return false;
        }
        trackVelocity(ev);
        final int action = ev.getActionMasked();

        mSGD.onTouchEvent(ev);
        final int x = (int) mSGD.getFocusX();
        final int y = (int) mSGD.getFocusY();

        if (mOnlyMovements) {
            mLastMotionY = ev.getRawY();
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mWatchingForPull = mScrollAdapter != null &&
                        isInside(mScrollAdapter.getHostView(), x, y);
                mResizedView = findView(x, y);
                mInitialTouchX = ev.getRawX();
                mInitialTouchY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE: {
                if (mWatchingForPull) {
                    final float yDiff = ev.getRawY() - mInitialTouchY;
                    final float xDiff = ev.getRawX() - mInitialTouchX;
                    if (yDiff > mTouchSlop && yDiff > Math.abs(xDiff)) {
                        if (DEBUG) Log.v(TAG, "got venetian gesture (dy=" + yDiff + "px)");
                        mWatchingForPull = false;
                        if (mResizedView != null && !isFullyExpanded(mResizedView)) {
                            if (startExpanding(mResizedView, BLINDS)) { // 
                                mInitialTouchY = ev.getRawY();
                                mLastMotionY = ev.getRawY();
                                mHasPopped = false;
                            }
                        }
                    }
                }
                if (mExpanding && 0 != (mExpansionStyle & BLINDS)) { // 是在百叶窗模式下
                    final float rawHeight = ev.getRawY() - mLastMotionY + mCurrentHeight;
                    final float newHeight = clamp(rawHeight);
                    boolean isFinished = false;
                    boolean expanded = false;
                    if (rawHeight > mNaturalHeight) {
                        isFinished = true;
                        expanded = true;
                    }
                    if (rawHeight < mSmallSize) {
                        isFinished = true;
                        expanded = false;
                    }

                    if (!mHasPopped) {
                        if (mEventSource != null) {
                            mEventSource.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        }
                        mHasPopped = true;
                    }

                    mScaler.setHeight(newHeight);//！！！！！！！！change height
                    mLastMotionY = ev.getRawY();
                    if (isFinished) {
                        mCallback.expansionStateChanged(false);
                    } else {
                        mCallback.expansionStateChanged(true);
                    }
                    return true;
                }

                if (mExpanding) {

                    // Gestural expansion is running
                    updateExpansion(); // change height
                    mLastMotionY = ev.getRawY();
                    return true;
                }

                break;
            }

            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (DEBUG) Log.d(TAG, "pointer change");
                mInitialTouchY += mSGD.getFocusY() - mLastFocusY;
                mInitialTouchSpan += mSGD.getCurrentSpan() - mLastSpanY;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (DEBUG) Log.d(TAG, "up/cancel");
                finishExpanding(false, getCurrentVelocity());
                clearView();
                break;
        }
        mLastMotionY = ev.getRawY();
        maybeRecycleVelocityTracker(ev);
        return mResizedView != null;
    }
```

# 触摸中用到的小函数

```

    private void trackVelocity(MotionEvent event) {
        int action = event.getActionMasked();
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    mVelocityTracker.clear();
                }
                mVelocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                }
                mVelocityTracker.addMovement(event);
                break;
            default:
                break;
        }
    }

    private void maybeRecycleVelocityTracker(MotionEvent event) {
        if (mVelocityTracker != null && (event.getActionMasked() == MotionEvent.ACTION_CANCEL
                || event.getActionMasked() == MotionEvent.ACTION_UP)) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private float getCurrentVelocity() {
        if (mVelocityTracker != null) {
            mVelocityTracker.computeCurrentVelocity(1000);
            return mVelocityTracker.getYVelocity();
        } else {
            return 0f;
        }
    }

    public void setEnabled(boolean enable) {
        mEnabled = enable;
    }

    private boolean isEnabled() {
        return mEnabled;
    }

    private boolean isFullyExpanded(ExpandableView underFocus) {
        return underFocus.getIntrinsicHeight() == underFocus.getMaxContentHeight()
                && (!underFocus.isSummaryWithChildren() || underFocus.areChildrenExpanded());
    }
```

# 状态切换

```

    /**
     * @return True if the view is expandable, false otherwise.
     */
    private boolean startExpanding(ExpandableView v, int expandType) {
        if (!(v instanceof ExpandableNotificationRow)) {
            return false;
        }
        mExpansionStyle = expandType;
        if (mExpanding && v == mResizedView) {
            return true;
        }
        mExpanding = true;
        mCallback.expansionStateChanged(true);
        if (DEBUG) Log.d(TAG, "scale type " + expandType + " beginning on view: " + v);
        mCallback.setUserLockedChild(v, true);//阻止事件拦截
        mScaler.setView(v);//set
        mOldHeight = mScaler.getHeight();
        mCurrentHeight = mOldHeight;
        boolean canBeExpanded = mCallback.canChildBeExpanded(v);
        if (canBeExpanded) {
            if (DEBUG) Log.d(TAG, "working on an expandable child");
            mNaturalHeight = mScaler.getNaturalHeight();
            mSmallSize = v.getCollapsedHeight();
        } else {
            if (DEBUG) Log.d(TAG, "working on a non-expandable child");
            mNaturalHeight = mOldHeight;
        }
        if (DEBUG) Log.d(TAG, "got mOldHeight: " + mOldHeight +
                    " mNaturalHeight: " + mNaturalHeight);
        return true;
    }

    private void finishExpanding(boolean force, float velocity) {
        if (!mExpanding) return;

        if (DEBUG) Log.d(TAG, "scale in finishing on view: " + mResizedView);

        float currentHeight = mScaler.getHeight();
        float h = mScaler.getHeight();
        final boolean wasClosed = (mOldHeight == mSmallSize);
        boolean nowExpanded;
        int naturalHeight = mScaler.getNaturalHeight();
        if (wasClosed) {
            nowExpanded = (force || currentHeight > mOldHeight && velocity >= 0);
        } else {
            nowExpanded = !force && (currentHeight >= mOldHeight || velocity > 0);
        }
        nowExpanded |= mNaturalHeight == mSmallSize;
        if (mScaleAnimation.isRunning()) {
            mScaleAnimation.cancel();
        }
        mCallback.expansionStateChanged(false);
        float targetHeight = nowExpanded ? naturalHeight : mSmallSize;
        if (targetHeight != currentHeight) {
            mScaleAnimation.setFloatValues(targetHeight);//！！！
            mScaleAnimation.setupStartValues();///////
            final View scaledView = mResizedView;
            final boolean expand = nowExpanded;
            mScaleAnimation.addListener(new AnimatorListenerAdapter() {
                public boolean mCancelled; //！！！

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!mCancelled) {
                        mCallback.setUserExpandedChild(scaledView, expand);
                    } else {
                        mCallback.setExpansionCancelled(scaledView);
                    }
                    mCallback.setUserLockedChild(scaledView, false);
                    mScaleAnimation.removeListener(this);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    mCancelled = true;//！！！
                }
            });
            velocity = nowExpanded == velocity >= 0 ? velocity : 0;
            mFlingAnimationUtils.apply(mScaleAnimation, currentHeight, targetHeight, velocity);
            mScaleAnimation.start();
        } else {
            mCallback.setUserExpandedChild(mResizedView, nowExpanded);
            mCallback.setUserLockedChild(mResizedView, false);
        }

        mExpanding = false;
        mExpansionStyle = NONE;

    }

    private void clearView() {
        mResizedView = null;
    }

    /**
     * Use this to abort any pending expansions in progress.
     */
    public void cancel() {
        finishExpanding(true, 0f /* velocity */);
        clearView();

        // reset the gesture detector
        mSGD = new ScaleGestureDetector(mContext, mScaleGestureListener);
    }

    /**
     * Change the expansion mode to only observe movements and don't perform any resizing.
     * This is needed when the expanding is finished and the scroller kicks in,
     * performing an overscroll motion. We only want to shrink it again when we are not
     * overscrolled.
     *
     * @param onlyMovements Should only movements be observed?
     */
    public void onlyObserveMovements(boolean onlyMovements) {
        mOnlyMovements = onlyMovements;
    }
```
