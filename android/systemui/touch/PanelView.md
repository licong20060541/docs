参考： http://blog.csdn.net/lyjit/article/details/52596622
```
1.状态栏上升，下拉的开始以及结束时的调用方法：
PanelView.java
开始：notifyExpandingStarted
结束：notifyExpandingFinished
对应着mExpanding

2.状态栏的自动下拉和上升方法(伴有时间动画)
PhoneStatusBar.java
自动下拉：animateExpandNotificationsPanel
自动上升：animateCollapsePanels

3.状态栏的自动下拉和上升方法(立即)
PhoneStatusBar.java
由展开状态立即不可见：makeExpandedInvisible
由不可见立即可见：makeExpandedVisible

4.下拉状态栏的是否已经不可见或者下拉状态栏的高度为0的判断：
PanelView.java
isFullyCollapsed

5.下拉状态栏是否已经下拉至最底部或者是最大高度的判断：
PanelView.java
isFullyExpanded


6.下拉状态栏3种状态的判断：
PanelBar.Java
mState：
STATE_CLOSED：下拉状态栏的高度为0
STATE_OPENING：没有到达最底部之前的下拉状态栏正在下拉或者上升过程
STATE_OPEN：到达最底部后的下拉状态栏正在下拉或者上升过程
它们由PanelView.java中onTrackingStarted和onTrackingStopped方法来控制参数，对应mTracking
```