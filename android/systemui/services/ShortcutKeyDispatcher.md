```
/**
 * Dispatches shortcut to System UI components
 */




    /**
     * Registers a shortcut key to window manager.
     * @param shortcutCode packed representation of shortcut key code and meta information
     */
    public void registerShortcutKey(long shortcutCode) {
        try {
            mWindowManagerService.registerShortcutKey(shortcutCode, mShortcutKeyServiceProxy);
        } catch (RemoteException e) {
            // Do nothing
        }
    }


    private void handleDockKey(long shortcutCode) {
        try {
            int dockSide = mWindowManagerService.getDockedStackSide();
            if (dockSide == WindowManager.DOCKED_INVALID) {
                // If there is no window docked, we dock the top-most window.
                Recents recents = getComponent(Recents.class);
```