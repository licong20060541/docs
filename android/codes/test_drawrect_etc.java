package com.mainitem;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;

import com.example.licong10.myapplication.R;


public class Item2Activity extends Activity implements View.OnClickListener {

    int count = 0;
    private static final String TAG = "licong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 没有标题栏
        setContentView(R.layout.activity_item2);
        findViewById(R.id.drawrect_btn).setOnClickListener(this);
        Display display = getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        Log.i(TAG, p.toString()); // Point(1080, 1794)

        getStatusBarHeight(); // Status height:63
        getNavigationBarHeight(); // Nav height:126
        
        // 模拟器：1920x1080

    }

    @Override
    public void onClick(View view) {
        count++;
        if(count % 2 == 0) {
//            view.scrollTo(20, 20);
            view.setTranslationX(40);
            Log.i("licong", "change 20");
        } else {
//            view.scrollTo(0, 0);
            view.setTranslationX(0);
            Log.i("licong", "change 0");
        }

        Rect hitRect = new Rect();
        Rect drawRect = new Rect();
        Rect localVisibleRect = new Rect();
        Rect globalVisibleRect = new Rect();
        int[] locationOnScreen = new int[2];
        int[] locationInWindow = new int[2];

        int width = view.getWidth();
        int height = view.getHeight();

        int paddingLeft = view.getPaddingLeft();
        int paddingTop = view.getPaddingTop();

        int leftToParent = view.getLeft();
        int topToParent = view.getTop();


        view.getHitRect(hitRect);
        view.getDrawingRect(drawRect);
        view.getLocalVisibleRect(localVisibleRect);
        view.getGlobalVisibleRect(globalVisibleRect);

        view.getLocationOnScreen(locationOnScreen);
        view.getLocationInWindow(locationInWindow);


        Log.d(TAG, "width---" + width + "---height---" + height);
        Log.d(TAG, "paddingLeft---" + paddingLeft + "---paddingTop---" + paddingTop);
        Log.d(TAG, "leftToParent---" + leftToParent + "---topToParent---" + topToParent);

        Log.d(TAG, "=====================");

        Log.d(TAG, "hitRect---" + hitRect.toString());
        Log.d(TAG, "drawRect---" + drawRect.toString());
        Log.d(TAG, "localVisibleRect---" + localVisibleRect.toString());
        Log.d(TAG, "globalVisibleRect---" + globalVisibleRect.toString());
        Log.d(TAG, "=====================");
        Log.d(TAG, "locationOnScreen---x--" + locationOnScreen[0] + "--y---" + locationOnScreen[1]);
        Log.d(TAG, "locationInWindow---x--" + locationInWindow[0] + "--y---" + locationInWindow[1]);
    }

    //  view.scrollTo(0, 0);
//      I/licong: scroll 0
//      D/licong10: width---525---height---525
//      D/licong10: paddingLeft---31---paddingTop---25
//      D/licong10: leftToParent---184---topToParent---184
//      D/licong10: =====================
//      D/licong10: hitRect---Rect(184, 184 - 709, 709) ---参考父布局
//      D/licong10: drawRect---Rect(0, 0 - 525, 525)---参考自己布局
//      D/licong10: localVisibleRect---Rect(0, 0 - 525, 525) ---参考自己布局
//      D/licong10: globalVisibleRect---Rect(184, 247 - 709, 772)---参考屏幕，高度加上了状态栏高度
//      D/licong10: =====================
//      D/licong10: locationOnScreen---x--184--y---247---参考屏幕，高度加上了状态栏高度
//      D/licong10: locationInWindow---x--184--y---247---参考屏幕，高度加上了状态栏高度


    //  view.scrollTo(20, 20);
//      I/licong: scroll 20
//      D/licong: width---525---height---525
//      D/licong: paddingLeft---31---paddingTop---25
//      D/licong: leftToParent---184---topToParent---184
//      D/licong: =====================
//      D/licong: hitRect---Rect(184, 184 - 709, 709)
//      D/licong: drawRect---Rect(20, 20 - 545, 545) ---随scroll发生change
//      D/licong: localVisibleRect---Rect(20, 20 - 545, 545)---随scroll发生change
//      D/licong: globalVisibleRect---Rect(184, 247 - 709, 772)
//      D/licong: =====================
//      D/licong: locationOnScreen---x--184--y---247
//      D/licong: locationInWindow---x--184--y---247


//      view.setTranslationX(40);
//      D/licong: width---525---height---525
//      D/licong: paddingLeft---31---paddingTop---25
//      D/licong: leftToParent---184---topToParent---184
//      D/licong: =====================
//      D/licong: hitRect---Rect(224, 184 - 749, 709)---随TranslationX发生change
//      D/licong: drawRect---Rect(0, 0 - 525, 525)
//      D/licong: localVisibleRect---Rect(0, 0 - 525, 525)
//      D/licong: globalVisibleRect---Rect(224, 247 - 749, 772)---随TranslationX发生change
//      D/licong: =====================
//      D/licong: locationOnScreen---x--224--y---247---随TranslationX发生change
//      D/licong: locationInWindow---x--224--y---247---随TranslationX发生change


    private int getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v(TAG, "Navi height:" + height);
        return height;
    }

    private int getStatusBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v(TAG, "Status height:" + height);
        return height;
    }

//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        int FULLSCREEN_UI_OPTIONS = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//        getWindow().getDecorView().setSystemUiVisibility(FULLSCREEN_UI_OPTIONS);
//    }

}
