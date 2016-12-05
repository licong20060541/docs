#canvas
```
canvas.translate(x, y);
canvas.drawArc(rect, 160, 220,,,)
画出的效果是从左下开始到右下;
  首先移动，使坐标系中心位于canvas中心，由于x轴向右为正，因此x轴下方开始就是角度为正的了，
因此，是从左下开始的。

canvas.rotate(30)--逆时针！


```

#paint
```
paint.measuretext--float,but no height
paint.gettextbounds--int

颜色渐变：shader渲染,sweepGradient
边缘模糊效果：setMaskFilter(BlueMaskFilter)//close hardware acce..

```