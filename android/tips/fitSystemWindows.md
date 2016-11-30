```

fitSystemWindows属性：
    官方描述:
        Boolean internal attribute to adjust view layout based on system windows such as the status bar. If true, adjusts the padding of this view to leave space for the system windows. Will only take effect if this view is in a non-embedded activity.
    简单描述：
     这个一个boolean值的内部属性，让view可以根据系统窗口(如status bar)来调整自己的布局，如果值为true,就会调整view的paingding属性来给system windows留出空间....
    实际效果：
     当 status bar为透明或半透明时(4.4以上),系统会设置view的paddingTop值为一个适合的值(status bar的高度)让view的内容不被上拉到状态栏，当在不占据status bar的情况下(4.4以下)会设置paddingTop值为0(因为没有占据status bar所以不用留出空间)。

View 的 protected boolean fitSystemWindows (Rect insets) 函数，其参数值为 Rect insets， 关于该参数的说明如下：
Rect: Current content insets of the window. Prior to JELLY_BEAN you must not modify the insets or else you and Android will be unhappy.
返回值的说明如下：
true if this view applied the insets and it should not continue propagating further down the hierarchy, false otherwise.
content insets 是系统状态栏、导航栏、输入法等其他系统窗口所占用的空间，系统通过该回调接口告诉你的应用， 系统的窗口占用了多少空间，然后你可以根据该信息来调整你的

```