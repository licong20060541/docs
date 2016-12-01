#核心服务
`核心服务NotificationManagerService，消息管理中心`

```
BaseStatusBar下 NotificationListenerService mNotificationListener
调用其方法
mNotificationListener.registerAsSystemService(mContext,
                    new ComponentName(mContext.getPackageName(), getClass().getCanonicalName()),
                    UserHandle.USER_ALL);

注册到NotificationManagerService下的private NotificationListeners mListeners;

之后当有新消息时，调用listener.onNotificationPosted(sbnHolder, rankingUpdate);即可回调了
```

#测试验证

`测试用例，当连续发送同一个应用的不同通知，每发送一个即调用一次listener.onNotificationPosted，当发送第四个通知时
， 发现回调了两次listener.onNotificationPosted，因此找到切入点！！！`

```
依次找调用
1. listener.onNotificationPosted(sbnHolder, rankingUpdate);
2. NotificationManagerService.notifyPosted()
3. NotificationManagerService.notifyPostedLocked
4. private class EnqueueNotificationRunnable implements Runnable
5. 
5.1 NotificationManagerService.enqueueNotificationInternal
    此为正常发送通知流程
5.2 NotificationManagerService.maybeAddAutobundleSummary
    此为消息组相关的

以下寻找消息组相关的代码调用
6 NotificationRankerService.adjustNotification
7 Ranker.adjustAutobundlingSummary
8 Ranker.onNotificationPosted: 说明Ranker也是NotificationManagerService下回调之一
其中代码
       if (notificationsForPackage.size() >= AUTOBUNDLE_AT_COUNT) {
                        for (String key : notificationsForPackage) {
                            notificationsToBundle.add(key);
                        }
       }
  而AUTOBUNDLE_AT_COUNT = 4，与测试用例符合
```

```
// 上述5.2代码如下，可见，内部创建了一个消息，并发送给了SystemUI

 // Posts a 'fake' summary for a package that has exceeded the solo-notification limit.
    private void maybeAddAutobundleSummary(Adjustment adjustment) {
        if (adjustment.getSignals() != null) {
            Bundle.setDefusable(adjustment.getSignals(), true);
            if (adjustment.getSignals().getBoolean(Adjustment.NEEDS_AUTOGROUPING_KEY, false)) {
                final String newAutoBundleKey =
                        adjustment.getSignals().getString(Adjustment.GROUP_KEY_OVERRIDE_KEY, null);
                int userId = -1;
                NotificationRecord summaryRecord = null;
                synchronized (mNotificationList) {
                    NotificationRecord notificationRecord =
                            mNotificationsByKey.get(adjustment.getKey());
                    if (notificationRecord == null) {
                        // The notification could have been cancelled again already. A successive
                        // adjustment will post a summary if needed.
                        return;
                    }
                    final StatusBarNotification adjustedSbn = notificationRecord.sbn;
                    userId = adjustedSbn.getUser().getIdentifier();
                    ArrayMap<String, String> summaries = mAutobundledSummaries.get(userId);
                    if (summaries == null) {
                        summaries = new ArrayMap<>();
                    }
                    mAutobundledSummaries.put(userId, summaries);
                    if (!summaries.containsKey(adjustment.getPackage())
                            && newAutoBundleKey != null) {
                        // Add summary
                        final ApplicationInfo appInfo =
                                adjustedSbn.getNotification().extras.getParcelable(
                                        Notification.EXTRA_BUILDER_APPLICATION_INFO);
                        final Bundle extras = new Bundle();
                        extras.putParcelable(Notification.EXTRA_BUILDER_APPLICATION_INFO, appInfo);
                        final Notification summaryNotification =
                                new Notification.Builder(getContext()).setSmallIcon(
                                        adjustedSbn.getNotification().getSmallIcon())
                                        .setGroupSummary(true)
                                        .setGroup(newAutoBundleKey)
                                        .setFlag(Notification.FLAG_AUTOGROUP_SUMMARY, true)
                                        .setFlag(Notification.FLAG_GROUP_SUMMARY, true)
                                        .setColor(adjustedSbn.getNotification().color)
                                        .build();
                        summaryNotification.extras.putAll(extras);
                        Intent appIntent = getContext().getPackageManager()
                                .getLaunchIntentForPackage(adjustment.getPackage());
                        if (appIntent != null) {
                            summaryNotification.contentIntent = PendingIntent.getActivityAsUser(
                                    getContext(), 0, appIntent, 0, null,
                                    UserHandle.of(userId));
                        }
                        final StatusBarNotification summarySbn =
                                new StatusBarNotification(adjustedSbn.getPackageName(),
                                        adjustedSbn.getOpPkg(),
                                        Integer.MAX_VALUE, Adjustment.GROUP_KEY_OVERRIDE_KEY,
                                        adjustedSbn.getUid(), adjustedSbn.getInitialPid(),
                                        summaryNotification, adjustedSbn.getUser(),
                                        newAutoBundleKey,
                                        System.currentTimeMillis());
                        summaryRecord = new NotificationRecord(getContext(), summarySbn);
                        summaries.put(adjustment.getPackage(), summarySbn.getKey());
                    }
                }
                if (summaryRecord != null) {
                    mHandler.post(new EnqueueNotificationRunnable(userId, summaryRecord));
                }
            }
        }
    }
```


`上述只是单纯的从代码调用角度去分析`