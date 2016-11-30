```
1.
   view.setClipBounds(mRect);只显示矩形范围的，矩形外的不显示了
2.
  默认返回要绘制的全部区域，这里修改为实际绘制的区域
  public void getDrawingRect(Rect outRect) {
        super.getDrawingRect(outRect);
        outRect.left += getTranslationX();
        outRect.right += getTranslationX();
        outRect.bottom = (int) (outRect.top + getTranslationY() + getActualHeight());
        outRect.top += getTranslationY() + getClipTopAmount();
    }

```