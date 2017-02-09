·   同一个ViewRoot下，不同类型的View（不同类型指不同的UI单元，例如按钮、文本框等）使用同一个Surface吗？

是的，但是SurfaceView要除外。因为SurfaceView的绘制一般在单独的线程上，并且由应用层主动调用lockCanvas、draw和unlockCanvasAndPost来完成绘制流程。应用层相当于抛开了ViewRoot的控制，直接和屏幕打交道，这在camera、video方面用得最多。



```
1、 屏幕区域的获取
activity.getWindowManager().getDefaultDisplay();

2、应用区域的获取
Rect outRect = new Rect();
activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
其中，outRect.top 即是状态栏高度。

3、view绘制区域获取
Rect outRect = new Rect();
activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
用绘制区域的outRect.top - 应用区域的outRect.top 即是标题栏的高度。

```