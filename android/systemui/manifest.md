#内部类的广播
```
        <receiver
            android:name=".tuner.TunerService$ClearReceiver"
            android:exported="false">
            <intent-filter>
                <action android:name="com.android.systemui.action.CLEAR_TUNER" />
            </intent-filter>
        </receiver>

class TunerService {
    public static class ClearReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_CLEAR.equals(intent.getAction())) {
                get(context).clearAll();
            }
        }
    }
}

```

#DozeService
```
        <!-- Doze with notifications, run in main sysui process for every user  -->
        <service
            android:name=".doze.DozeService"
            android:exported="true"
            android:singleUser="true"
            android:permission="android.permission.BIND_DREAM_SERVICE" />
Extend this class to implement a custom dream (available to the user as a "Daydream").

Dreams are interactive screensavers launched when a charging device is idle, or docked in
a desk dock. Dreams provide another modality for apps to express themselves, tailored for
an exhibition/lean-back experience
梦想是交互式屏幕保护程序启动时充电设备闲置，或停靠在码头的一桌。梦想提供了另一种方式来表达自己的应用程序，
专为一个展览/精益回来的经验.
```

#3
```
        <!-- Callback for deleting screenshot notification -->
        <receiver android:name=".screenshot.GlobalScreenshot$DeleteScreenshotReceiver"
                  android:process=":screenshot"
                  android:exported="false" />


        <service android:name=".ImageWallpaper"
                android:permission="android.permission.BIND_WALLPAPER"
                android:exported="true" />


        <!-- started from UsbDeviceSettingsManager -->
        <activity android:name=".usb.UsbConfirmActivity"
            android:exported="true"
            android:permission="android.permission.MANAGE_USB"
            android:theme="@style/Theme.SystemUI.Dialog.Alert"
            android:finishOnCloseSystemDialogs="true"
            android:excludeFromRecents="true">
        </activity>


        <!-- started from NetworkPolicyManagerService -->
        <activity
            android:name=".net.NetworkOverLimitActivity"
            android:exported="true"
            android:permission="android.permission.MANAGE_NETWORK_POLICY"
            android:theme="@android:style/Theme.DeviceDefault.Light.Panel"
            android:finishOnCloseSystemDialogs="true"
            android:launchMode="singleTop"
            android:taskAffinity="com.android.systemui.net"
            android:excludeFromRecents="true" />
```



