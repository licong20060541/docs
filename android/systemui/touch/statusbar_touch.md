```

看状态栏的布局，发现在默认情况下，即没有展开栏时，
展开栏的高度和状态栏一致，但是展开栏是覆盖在状态栏的view上的，
疑问，那么touch事件来时，传递给哪个？

实验结论：
先传给展开栏，展开栏返回false，之后传给状态栏处理。

并且，
当FrameLayout下先写一个button，后写一个FrameLayout时，
发现button一直显示在最上方？？
看来内部优化了。


```
```
PanelBar状态栏的三个状态
    public static final int STATE_CLOSED = 0;
    public static final int STATE_OPENING = 1;
    public static final int STATE_OPEN = 2;
```

以下内容参考修改
http://blog.csdn.net/lyjit/article/details/52599167

```

一：下拉状态的变化
 1.点击显示状态栏不动的状态
由于展开栏不处理，因此传给PhoneStatusBarView处理，其交给了NotificationPanelView处理
在onTouch的down事件中，mExpandedHeight == 0，开始走mPeekRunnable，mPeekHeight == 320.0
                if (mExpandedHeight == 0) {
                    schedulePeek();// 在其动画中执行了显示NotificationPanelView的操作，默认为gone
                }

设置可见后，走onLayout--->方法中的setQsExpansion(mQsMinExpansionHeight + mLastOverscroll);和updateHeader();
setQsTranslation(mQsExpansionHeight);方法就是控制header布局以及快捷菜单的位置

up之后会先走后续动画，然后手指松开关闭状态栏一串调用到closeQs方法等

如果按下down后直接向下滑动，则跟随手滑动，展开后，up，不会自动收起。



/**
 * Class to encapsulate all possible status bar states regarding Keyguard.
 */
public class StatusBarState {

    /**
     * The status bar is in the "normal" shade mode.
     */
    public static final int SHADE = 0;

    /**
     * Status bar is currently the Keyguard.
     */
    public static final int KEYGUARD = 1;

    /**
     * Status bar is in the special mode, where it is fully interactive but still locked. So
     * dismissing the shade will still show the bouncer.
     */
    public static final int SHADE_LOCKED = 2;

}

默认是SHADE状态！！经常是

 2.从显示状态栏下拉到下拉状态栏
down事件和之前的差不多，只是在move事件中，走setExpandedHeightInternal()方法中的onHeightUpdated(mExpandedHeight)，
从而走updateHeaderShade()方法，
到了一定时候会走onOverscrollTopChanged方法(setExpandedHeightInternal调用)，无一例外，它们都走setQsTranslation(mQsExpansionHeight)方法，因为控制header布局以及快捷菜单的位置



   二：NotificationPanelView中几个需要判断的值
 1.mQsExpanded：当状态栏只显示头view和消息栏，此时mQsExpanded为false，将状态栏下拉，一旦向下移动，mQsExpanded立马变为true.显示头view和消息栏为mQsExpanded值变化的分界点，不管什么情况，只要在此位置即为false，一旦下移，即为true，由setQsExpanded(boolean expanded)方法控制；

现在来讨论setQsExpanded(boolean expanded)的调用流程

 1)当点击消息栏滑动时，onTouch事件传递到NotificationStackScrollLayout的onTouchEvent中，会执行scrollerWantsIt =onScrollTouch(ev)方法；
此方法中,当下拉时走overScrollDown(deltaY)方法，上移时走overScrollUp(deltaY, range)方法(注意，如果不进行向下移动的操作或者当状态栏已经到达底部时再重新上移，都不走上述的两种方法)；两种方法都调用了setOverScrolledPixels方法，最终调用NotificationStackScrollLayout中的notifyOverscrollTopListener()方法，此方法中的mOverscrollTopChangedListener.onOverscrollTopChanged(amount, isRubberbanded)在NotificationPanelView中被实现，调用setQsExpansion()方法，通过setQsExpanded(true)或者false来设置mQsExpanded的值。

释放手指：当自定义动画往下走时，走NotificationStackScrollLayout中的onOverScrollFling(true, initialVelocity)方法，又mOverscrollTopChangedListener.flingTopOverscroll(initialVelocity, open)，在NotificationPanelView中实现，flingSettings()即为释放动画；
当定义动画往上走时，走fling(-initialVelocity)方法，从而走setOverScrollAmount(0, true, false)，setOverScrollAmountInternal方法中，animate第一次的值为true，此时开启动画animateOverScrollToAmount()将动画走到原点，并且在此方法中animate改为false。


 2)点击其他位置下滑时，onTouch的down事件传递到直接在NotificationPanelView中处理，走onTouchEvent()中的onQsExpansionStarted()，action_down传递到onQsTouch中，走onQsExpansionStarted()；再调用父类PanelView的中onTouchEvent的onTrackingStarted();move事件传递到onQsTouch()中，调用setQsExpansion(h + mInitialHeightOnTouch);控制mQsExpanded的值；
手指释放，自定义动画往上走时，先走onQsTouch中的ACTION_CANCEL，flingQsWithCurrentVelocity()方法,方法中flingSettings(vel, flingExpandsQs(vel) && !isCancelMotionEvent)，其中(flingExpandsQs(vel) && !isCancelMotionEvent)为false，此方法会做释放动画；再走PanelView中的onTrackingStopped(expand);自定义动画往下走时，其中(flingExpandsQs(vel) && !isCancelMotionEvent)为true


 3)当状态栏全部滑动到底时，此时上拉，action_move的时候传递到NotificationStackScrollLayout中的onTouchEvent方法，调用ObservableScrollView的onTouchEvent方法，然后走了ObservableScrollView中的onScrollChanged()方法，其中的 mListener.onScrollChanged()在NotificationPanelView中实现，走requestPanelHeightUpdate()方法，走setExpandedHeight(currentMaxPanelHeight)，最后走onHeightUpdated(mExpandedHeight);调用setQsExpansion(mQsMinExpansionHeight+ t * (getTempQsMaxExpansion() - mQsMinExpansionHeight));

ObservableScrollView的onTouchEvent滚动时，当手指是在消息栏中引起滚动的，先走overScrollBy，再走onScrollChanged()，然后走onOverScrolled；当ObservableScrollView向上滚动到一个值(大约在60左右)时，overScrollBy中的mLastOverscrollAmount的值大于0，此时它会调用mListener.onOverscrolled(mLastX, mLastY, mLastOverscrollAmount)方法，在NotificationPanelView中实现，此时又走了一遍onScrollChanged方法，接下来会掉用在NotificationPanelView中实现的onOverscrolled方法中的onQsExpansionStarted(amount);此后move事件在NotificationPanelView中的onInterceptTouchEvent被拦截：mQsTracking返回true，调用setQsExpansion(h + mInitialHeightOnTouch);然后ontouch事件全部在onTouchEvent的onQsTouch中被处理；
释放手指，走onQsTouch的flingQsWithCurrentVelocity，做释放动画。


4)状态栏从头下拉时，直接走NotificationPanelView的onTouchEvent方法，不走拦截方法，down事件经过PanelView中onTouchEvent时，mExpandedHeight = 0.0，调用schedulePeek()方法，开启启动动画，如果手指移动多快，会进入move事件传入，调用removeCallbacks(mPeekRunnable)，删除开启动画，并调用onTrackingStarted();再调用setExpandedHeightInternal(newHeight)设置高度，走一遍onLayout方法；此后move事件继续传入，走setExpandedHeightInternal(newHeight)方法。一旦newHeight到达一个值后，调用setExpandedHeightInternal()方法中的setOverExpansion方法，继而调用mNotificationStackScroller.setOverScrolledPixels(overExpansion, true /* onTop */, false /* animate */)方法，从而调用setQsExpansion(mQsMinExpansionHeight + rounded)方法；

手指释放，ACTION_CANCEL传入到PanelView中，走onTrackingStopped(expand)方法，此方法会调用mNotificationStackScroller.setOverScrolledPixels(0.0f, true /* onTop */, true /* animate */)方法，继而调用setOverScrollAmountInternal()方法，第一次时animate为true，也就开启了释放动画，再调用PanelView中的move中的fling(vel, expand)方法；但是此方法会被拦截调用notifyExpandingFinished();由notifyExpandingFinished()开启的动画调用setQsExpansion(mQsMinExpansionHeight + rounded)方法，并且会间隔调用onLayout和onHeightChanged()方法改变mExpandedHeight和mExpandedFraction的值；

开机第一次下拉：之所以可以将内容一直拉到底部，是因为move时它走setExpandedHeightInternal(newHeight)方法，此方法会调用到onHeightUpdated(mExpandedHeight)，从而调用setQsExpansion(mQsMinExpansionHeight+ t * (getTempQsMaxExpansion() - mQsMinExpansionHeight));
不是第一次的从头下拉，move时它也走setExpandedHeightInternal(newHeight)方法，但是到了一定时候它会走setOverExpansion(overExpansionPixels, true /* isPixels */)，在这里调用setQsExpansion()方法，并不走onHeightUpdated(mExpandedHeight)中的setQsExpansion()方法

mQsMinExpansionHeight：此值表示最小值，为136
mQsExpansionHeight：此值为下拉的总高度

getMaxPanelHeight()：它的值有两个，一个是状态栏拉到最下面的高度calculatePanelHeightQsExpanded()；一个是只显示头和消息栏的高度calculatePanelHeightShade();它的最小高度为50；
第一次下拉，在setExpandedHeightInternal方法中获到getMaxPanelHeight()值为calculatePanelHeightQsExpanded()，此后每次从头下拉，获得到的值为calculatePanelHeightShade(）


  2.mQsExpandImmediate：
要想让状态栏中的各布局不随状态栏的长度变化，首先，得找到控制这些变化的地方，也就是说，将它们的事件处理全部屏蔽，其实，各布局的位置变化，只要是由setQsExpansion()方法来控制的，找到并屏蔽调用此方法的地方。

 1)当状态栏只显示头和消息栏时，移动空白区域，控制状态栏中的各布局长度的变化的，在onQsTouch中，其中move控制的是setQsExpansion(h + mInitialHeightOnTouch);而up控制的是lingQsWithCurrentVelocity(event.getActionMasked() == MotionEvent.ACTION_CANCEL);释放动画


 2)当状态栏只显示头和消息栏时，移动消息栏，move事件在NotificationStackScrollLayout的onScrollTouch(ev)方法中被处理，当下拉时走overScrollDown(deltaY)方法，上移时走overScrollUp(deltaY, range)方法，他们都走mOverscrollTopChangedListener.onOverscrollTopChanged(amount, isRubberbanded)方法，屏蔽掉；up事件，在mOverscrollTopChangedListener.flingTopOverscroll(initialVelocity, open)被处理，屏蔽掉；

3)快捷栏会根据手指的move而滚动，原因是在调用了NotificationPanelView中的setQsTranslation()，继而调用了mQsContainer.setY(mHeader.getHeight());

4)headerview会根据手指移动：headerview变高mHeader.setExpanded(mKeyguardShowing || (mQsExpanded && !mStackScrollerOverscrolling));
移动位置： updateHeaderShade方法中的mHeader.setTranslationY(getHeaderTranslation());


5)消息栏根据手指移动：通过调用NotificationStackScrollLayout中的setTopPadding()和setStackHeight()方法，屏蔽
点击headerview，会间接出现快捷栏是否可见以及是否可点击，处理代码：mQsPanel.setVisibility(expandVisually ? View.VISIBLE : View.INVISIBLE)和mScrollView.setTouchEnabled(mQsExpanded);
将快捷栏的布局改成HorizontalScrollView之后，会发现快捷栏里面的亮度条变成不可见，是因为在onMeasure方法中给亮度条设置大小时，mBrightnessView.measure(exactly(width), MeasureSpec.UNSPECIFIED);其中width为0，也就是快捷栏的长度为0

  三：关于状态栏的小细节
点击wifi快捷栏图标：
1:点击下面的wifi选择，会弹出选择界面，点击事件由QSPanel中的clickSecondary实现的，实现代码：showDetail(true);
2:点击上面的wifi图标，变化wifi状态

3:开机后headerview会不显示，在代码中做了处理：NotificationPanelView的setQsTranslation()方法中
mHeader.setY(interpolate(getQsExpansionFraction(), -mHeader.getHeight(), 0));

 4:显示状态栏的信号和电池图标不显示，是因为电池图标的布局在headerview和显示状态栏布局中各有一份，在headerview中设置了他的显示情况

 5:显示状态栏在桌面和应用中的显示颜色不相同：在color.xml中，桌面颜色system_bar_background_semi_transparent；应用颜色system_bar_background_opaque

 6:点击显示状态栏迅速松开，会出现状态栏先是下拉到底部，然后再回到顶部，分析，当我们刚开始点击状态栏时，先走的是一个动画runPeekAnimation，该动画显示的高度为mPeekHeight，有时显式状态栏会不出现，是因为在runPeekAnimation中mPeekHeight返回为0；之所以会下拉到底部，在返回到顶部，是因为mPeekHeight的值会通过requestPanelHeightUpdate中的setExpandedHeight(currentMaxPanelHeight)方法被设置为最大高度，一般情况下requestPanelHeightUpdate也会被调用，
只是setExpandedHeight的判断条件不成立

 7:在主界面点击通知按钮，会发现状态栏下拉的很慢，原因：在点击通知按钮后，会调用PhoneStatusBar中的animateExpandNotificationsPanel()方法，从而调用fling(0, true /* expand */);开启动画，接下来调用mFlingAnimationUtils.apply(animator, mExpandedHeight, target, vel, getHeight())方法，通过getProperties方法获得动画速度，因为速度设置的太慢，以至于下拉的很慢


 总结：关于SystemUi的知识点很多，不能一一写出，后续的开发过程中遇到的问题点会继续总结。
```