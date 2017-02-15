`AccessibilityService-无障碍模式，如抢红包插件`

`Activity切换动画ActivityOptions,使用了Transition动画`

```
View.java
    public void setOutlineProvider(ViewOutlineProvider provider) {
        mOutlineProvider = provider;
        invalidateOutline();
    }
    
eg.AnimateableViewBounds
```


不让应用和别人分享屏幕的,需要在清单文件的application或者activity中添加android:resizeableActivity="false"就ok了


ViewRootImpl的创建在onResume方法回调之后，而我们一开篇是在onCreate方法中创建了子线程并访问UI，在那个时刻，ViewRootImpl是没有创建的，无法检测当前线程是否是UI线程，所以程序没有崩溃一样能跑起来，而之后修改了程序，让线程休眠了200毫秒后，程序就崩了。很明显200毫秒后ViewRootImpl已经创建了，可以执行checkThread方法检查当前线程。



```
4
https://github.com/wlj32011/InspectWechatFriend

Android Accessibility这个一般可以用来做一些辅助性的功能，抢XX插件什么的
新建一个Services类集成AccessibilityService,实现对应方法
通过getRootInActiveWindow方法获取当前窗口信息，通过findAccessibilityNodeInfosByText方法找到当前对应控件进行模拟点击

uiautomatorviewer可以检查当前手机的布局结构，如果想更精确的找到控件位置，uiautomatorviewer必不可少！


```


```
5
解锁脚本

sleep 0.1 && input keyevent 3
input swipe 655 1774 655 874
sleep 1 && input tap 612 726
sleep 0.1 && input tap 813 1000
sleep 0.1 && input tap 813 1000
sleep 0.1 && input tap 255 1000
quit

keyevent等于3，代表这是HOME键事件，所以第一行的作用等同于点击HOME键。

更多的KEYCODE可以查看android.view.KeyEvent这个类，swipe是滑动操作，即模拟手指从（655，1774）滑动到（655，874），也就是手指上划，主要是进入到解锁界面，tap是点击操作，后面跟的是点击的坐标，所以接下来的四次tap，是模拟点击解锁界面的某些数字，quit是程序本身用于判断脚本结束的标志，并不是adb命令。为了能适配不同的手机，所以把解锁脚本独立出来，放到SD卡根目录，文件名为MonitorUnlock.txt，再根据自己的手机解锁操作，编写好对应的解锁脚本即可，需要解锁的时候就从SD卡中读取该文件

关于是如何确定坐标的，其实很简单，打开开发者模式-指针位置即可查看自己实际操作时候的坐标值

```