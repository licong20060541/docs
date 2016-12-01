# 布局

```
<?xml version="1.0" encoding="utf-8"?>

<com.android.systemui.statusbar.ExpandableNotificationRow xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:clickable="true"
    android:focusable="true" >

    <com.android.systemui.statusbar.NotificationBackgroundView
        android:id="@+id/backgroundNormal"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <com.android.systemui.statusbar.NotificationBackgroundView
        android:id="@+id/backgroundDimmed"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <com.android.systemui.statusbar.NotificationContentView
        android:id="@+id/expanded"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <com.android.systemui.statusbar.NotificationContentView
        android:id="@+id/expandedPublic"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <Button
        android:id="@+id/veto"
        android:layout_width="48dp"
        android:layout_height="0dp"
        android:layout_marginEnd="-80dp"
        android:background="@null"
        android:gravity="end"
        android:paddingEnd="8dp"
        android:paddingStart="8dp" />

    <ViewStub
        android:id="@+id/child_container_stub"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inflatedId="@+id/notification_children_container"
        android:layout="@layout/notification_children_container" />

    <ViewStub
        android:id="@+id/more_button_stub"
        android:layout_width="match_parent"
        android:layout_height="@dimen/notification_bottom_decor_height"
        android:inflatedId="@+id/notification_more_button_container"
        android:layout="@layout/notification_expand_button" />

    //  here！！！！！！！！！！！！
    <ViewStub
        android:id="@+id/notification_guts_stub"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:inflatedId="@+id/notification_guts"
        android:layout="@layout/notification_guts" />

</com.android.systemui.statusbar.ExpandableNotificationRow>
```

# 源码

```

/**
 * The guts of a notification revealed when performing a long press.
 */
public class NotificationGuts extends FrameLayout {

    private Drawable mBackground;
    private int mClipTopAmount;
    private int mActualHeight;

    public NotificationGuts(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        draw(canvas, mBackground);
    }

    private void draw(Canvas canvas, Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, mClipTopAmount, getWidth(), mActualHeight);
            drawable.draw(canvas);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackground = mContext.getDrawable(R.drawable.notification_guts_bg);
        if (mBackground != null) {
            mBackground.setCallback(this);
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == mBackground;
    }

    @Override
    protected void drawableStateChanged() {
        drawableStateChanged(mBackground);
    }

    private void drawableStateChanged(Drawable d) {
        if (d != null && d.isStateful()) {
            d.setState(getDrawableState());
        }
    }

    @Override
    public void drawableHotspotChanged(float x, float y) {
        if (mBackground != null) {
            mBackground.setHotspot(x, y);
        }
    }

    public void setActualHeight(int actualHeight) {
        mActualHeight = actualHeight;
        invalidate();
    }

    public int getActualHeight() {
        return mActualHeight;
    }

    public void setClipTopAmount(int clipTopAmount) {
        mClipTopAmount = clipTopAmount;
        invalidate();
    }

    @Override
    public boolean hasOverlappingRendering() {

        // Prevents this view from creating a layer when alpha is animating.
        return false;
    }
}
```
