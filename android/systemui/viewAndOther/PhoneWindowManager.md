#SystemGesturesPointerEventListener
```
    interface Callbacks {
        void onSwipeFromTop();
        void onSwipeFromBottom();
        void onSwipeFromRight();
        void onFling(int durationMs);
        void onDown();
        void onUpOrCancel();
        void onMouseHoverAtTop();
        void onMouseHoverAtBottom();
        void onMouseLeaveFromEdge();
        void onDebug();
    }
```

#PhoneWindowManager

```

    /** {@inheritDoc} */
    @Override
    public int selectAnimationLw(WindowState win, int transit) {
        if (PRINT_ANIM) Log.i(TAG, "selectAnimation in " + win
              + ": transit=" + transit);
        if (win == mStatusBar) {
            boolean isKeyguard = (win.getAttrs().privateFlags & PRIVATE_FLAG_KEYGUARD) != 0;
            if (transit == TRANSIT_EXIT
                    || transit == TRANSIT_HIDE) {
                return isKeyguard ? -1 : R.anim.dock_top_exit;
            } else if (transit == TRANSIT_ENTER
                    || transit == TRANSIT_SHOW) {
                return isKeyguard ? -1 : R.anim.dock_top_enter;
            }
        } else if (win == mNavigationBar) {
```