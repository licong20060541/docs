颜色与过度绘制：

    原色：没有过度绘制
    蓝色：1 次过度绘制
    绿色：2 次过度绘制
    粉色：3 次过度绘制
    红色：4 次及以上过度绘制
    
优化过度绘制：

1. 去除Activity自带的默认背景颜色
```
<style name="Theme">
    ...
    <!-- Window attributes -->
    <item name="windowBackground">@drawable/screen_background_selector_dark</item>
    ...
</style>
```

我们只要在自己的AppTheme里面去除该背景色即可：

```
<style name="AppTheme" parent="android:Theme.Light.NoTitleBar">
    <item name="android:windowBackground">@null</item>
</style>
```

或者在Activity的onCreate方法中：
```
getWindow().setBackgroundDrawable(null);
```

2.使用Canvas的clipRect和clipPath方法限制View的绘制区域

调用了clipRect之后，画布的可绘制区域减小到和Rect指定的矩形区域一样大小。所有的绘制将限制在该矩形范围之内。

DrawerLayout.Java类。使用来实现抽屉布局的
```
@Override
protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
    final int height = getHeight();
    // 判断是否是内容视图
    final boolean drawingContent = isContentView(child);
    int clipLeft = 0, clipRight = getWidth();

    // 记录当前画布信息
    final int restoreCount = canvas.save();
    if (drawingContent) {
        // 只有在绘制内容视图时才进行裁切
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View v = getChildAt(i);
            if (v == child || v.getVisibility() != VISIBLE ||
                    !hasOpaqueBackground(v) || !isDrawerView(v) ||
                    v.getHeight() < height) {
                // 如果child是内容视图/视图不可见/视图背景透明/不是抽屉视图/child高度小于父布局高度
                // 则不做画布裁切
                continue;
            }

            if (checkDrawerViewAbsoluteGravity(v, Gravity.LEFT)) {
                // 盒子在左侧时裁切的left和right
                final int vright = v.getRight();
                if (vright > clipLeft) clipLeft = vright;
            } else {
                // 盒子在右侧时裁切的的left和right
                final int vleft = v.getLeft();
                if (vleft < clipRight) clipRight = vleft;
            }
        }
        // 裁切画布
        canvas.clipRect(clipLeft, 0, clipRight, getHeight());
    }
    // 绘制子视图
    final boolean result = super.drawChild(canvas, child, drawingTime);
    // 回复到裁切之前的画布
    canvas.restoreToCount(restoreCount);
}
```


3. ImageView的background和imageDrawable重叠