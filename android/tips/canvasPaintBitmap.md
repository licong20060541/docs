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


            private PorterDuffColorFilter mAppearAnimationFilter;
            mAppearAnimationFilter.setColor(sourceColor);
            mAppearPaint.setColorFilter(mAppearAnimationFilter);
            
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getActualHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        mAppearPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));


LightingColorFilter mLightingColorFilter = new LightingColorFilter(0xffffffff, 0);
        mDrawPaint.setColorFilter(mLightingColorFilter);
        mDrawPaint.setFilterBitmap(true);
        mDrawPaint.setAntiAlias(true);

        int mul = (int) ((1.0f - mDimAlpha) * mBitmapAlpha * 255);
        int add = (int) ((1.0f - mDimAlpha) * (1 - mBitmapAlpha) * 255);
        if (mBitmapShader != null) {
            mLightingColorFilter.setColorMultiply(Color.argb(255, mul, mul, mul));
            mLightingColorFilter.setColorAdd(Color.argb(0, add, add, add));
            mDrawPaint.setColorFilter(mLightingColorFilter);
            mDrawPaint.setColor(0xffffffff);
        } else {
            mDrawPaint.setColorFilter(null);
            int grey = mul + add;
            mDrawPaint.setColor(Color.argb(255, grey, grey, grey));
        }

// 接上代码
// bitmap铺满
    public void setImageBitmap(Bitmap bm) {
        if (bm != null) {
            mBitmapShader = new BitmapShader(bm, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP);
            mDrawPaint.setShader(mBitmapShader);
            mBitmapRect.set(0, 0, bm.getWidth(), bm.getHeight());
            updateBitmapScale();
        } else {
            mBitmapShader = null;
            mDrawPaint.setShader(null);
        }
        updateFilter();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mLayoutRect.set(0, 0, getWidth(), getHeight());
            updateBitmapScale();
        }
    }

    private void updateBitmapScale() {
        if (mBitmapShader != null) {
            mScaleMatrix.setRectToRect(mBitmapRect, mLayoutRect, Matrix.ScaleToFit.FILL);
            mBitmapShader.setLocalMatrix(mScaleMatrix);
        }
    }



```


# Bitmap

```
        // Create the protection paints
        mBgProtectionPaint = new Paint();
        mBgProtectionPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        mBgProtectionPaint.setColor(0xFFffffff);
        mBgProtectionCanvas = new Canvas();


mBgProtectionCanvas.setBitmap(thumbnail);
mBgProtectionCanvas.drawRect(0, 0, thumbnail.getWidth(), thumbnail.getHeight(),
                        mBgProtectionPaint);
mBgProtectionCanvas.setBitmap(null);
```

圆形bitmap

```

public class BitmapHelper {
    /**
     * Generate a new bitmap (width x height pixels, ARGB_8888) with the input bitmap scaled
     * to fit and clipped to an inscribed circle.
     * @param input Bitmap to resize and clip
     * @param width Width of output bitmap (and diameter of circle)
     * @param height Height of output bitmap
     * @return A shiny new bitmap for you to use
     */
    public static Bitmap createCircularClip(Bitmap input, int width, int height) {
        if (input == null) return null;

        final int inWidth = input.getWidth();
        final int inHeight = input.getHeight();
        final Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        paint.setShader(new BitmapShader(input, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        final RectF srcRect = new RectF(0, 0, inWidth, inHeight);
        final RectF dstRect = new RectF(0, 0, width, height);
        final Matrix m = new Matrix();
        m.setRectToRect(srcRect, dstRect, Matrix.ScaleToFit.CENTER);
        canvas.setMatrix(m);
        canvas.drawCircle(inWidth / 2, inHeight / 2, inWidth / 2, paint);
        return output;
    }
}
```


# task thumbnail
```
    /**
     * Returns a task thumbnail from the activity manager
     */
    public static Bitmap getThumbnail(ActivityManager activityManager, int taskId) {
        ActivityManager.TaskThumbnail taskThumbnail = activityManager.getTaskThumbnail(taskId);
        if (taskThumbnail == null)
            return null;

        sBitmapOptions = new BitmapFactory.Options();
        sBitmapOptions.inMutable = true;

        Bitmap thumbnail = taskThumbnail.mainThumbnail;
        ParcelFileDescriptor descriptor = taskThumbnail.thumbnailFileDescriptor;
        if (thumbnail == null && descriptor != null) {
            thumbnail = BitmapFactory.decodeFileDescriptor(descriptor.getFileDescriptor(),
                    null, sBitmapOptions);
        }
        if (descriptor != null) {
            try {
                descriptor.close();
            } catch (IOException e) {
            }
        }
        return thumbnail;
    }
```

```
        // Create the default assets
        Bitmap icon = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        icon.eraseColor(0x00000000);
        mDefaultThumbnail = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        mDefaultThumbnail.setHasAlpha(false);
        mDefaultThumbnail.eraseColor(0xFFffffff);
        mDefaultApplicationIcon = new BitmapDrawable(context.getResources(), icon);
```



```
        setWillNotDraw(false);
        setClipToOutline(true);
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
        });
```

# Path

```
再介绍一个比较好用的 op(Path path, Path.Op op) 方法，用于将两个Path路径进行组合之后的效果设置，靠op方法可以快速组合生成一些复杂的图形效果，例如月牙形 :


Path.Op有如下几种参数：


Path.Op.DIFFERENCE：减去Path2后Path1剩下的部分 
Path.Op.INTERSECT：保留Path1与Path2共同的部分 
Path.Op.REVERSE_DIFFERENCE：减去Path1后Path2剩下的部分 
Path.Op.UNION：保留全部Path1和Path2 
Path.Op.XOR：包含Path1与Path2但不包括两者相交的部分
```