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