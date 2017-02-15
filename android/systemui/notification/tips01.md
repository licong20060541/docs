```
1
anndroid L
show the notification in the shade:即显示在展开栏中

2
updateNotificationShade--更新展开栏通知 {

        updateRowStates(); // Updates expanded, dimmed and locked states of notification rows.
        //  The view representing the separation between important and less important notifications
        updateSpeedbump();
        updateClearAll(); // 清除按钮
        updateEmptyShadeView(); // 没有消息
}


3
SecureCameraLaunchManager
Handles launching the secure camera properly even when other applications may be using the camera hardware.


```