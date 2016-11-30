```
1
SignalClusterView---调用mNC.addSignalCallback(this);添加接口

@Override
    public void setWifiIndicators(boolean enabled, IconState statusIcon, IconState qsIcon,
            boolean activityIn, boolean activityOut, String description) {
        mWifiVisible = statusIcon.visible && !mBlockWifi;
        mWifiStrengthId = statusIcon.icon;
        mWifiDescription = statusIcon.contentDescription;

        apply();
    }

2
NetworkControllerImpl---

    public void addSignalCallback(SignalCallback cb) {
        cb.setSubs(mCurrentSubscriptions);
        cb.setIsAirplaneMode(new IconState(mAirplaneMode,
                TelephonyIcons.FLIGHT_MODE_ICON, R.string.accessibility_airplane_mode, mContext));
        cb.setNoSims(mHasNoSims);
        mWifiSignalController.notifyListeners(cb);
        mEthernetSignalController.notifyListeners(cb);
        for (MobileSignalController mobileSignalController : mMobileSignalControllers.values()) {
            mobileSignalController.notifyListeners(cb);
        }
        mCallbackHandler.setListening(cb, true);
    }


3.
CallbackHandler--
    // All the callbacks.
    private final ArrayList<EmergencyListener> mEmergencyListeners = new ArrayList<>();
    private final ArrayList<SignalCallback> mSignalCallbacks = new ArrayList<>();

    @Override
    public void setWifiIndicators(final boolean enabled, final IconState statusIcon,
            final IconState qsIcon, final boolean activityIn, final boolean activityOut,
            final String description) {
        post(new Runnable() {
            @Override
            public void run() {
                for (SignalCallback callback : mSignalCallbacks) {
                    callback.setWifiIndicators(enabled, statusIcon, qsIcon, activityIn, activityOut,
                            description);
                }
            }
        });
    }


4


class WifiSignalController---
在NetworkControllerImpl下
        mWifiSignalController = new WifiSignalController(mContext, mHasMobileDataFeature,
                mCallbackHandler, this);

在NetworkControllerImpl下收到广播后，会调用如下

    public void notifyListeners(SignalCallback callback) {

而后。。。
```