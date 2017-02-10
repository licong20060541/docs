package com.mainitem.views;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.licong10.myapplication.R;

/**
 * Created by licong10 on 17-2-10.
 *
 */
public class CameraGroup extends RelativeLayout implements View.OnClickListener {

    private final String TAG = CameraGroup.class.getSimpleName();

    private Button mButton;
    private Camera mCamera;
    private Matrix mMatrix;

    private int count = -1;

    private int centerX;
    private int centerY;

    private int rotateX;
    private int rotateY;
    private int rotateZ;


    public CameraGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        mCamera = new Camera();
        mMatrix = new Matrix();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mButton = (Button) findViewById(R.id.rotate);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if(count != -1)
            drawButton(canvas, getDrawingTime());
        else
            super.dispatchDraw(canvas);
    }

    private void drawButton(Canvas canvas, long time) {

        getParamsZ();

        canvas.save();

        Log.i(TAG, "rotateX:" + rotateX);
        Log.i(TAG, "rotateY:" + rotateY);
        Log.i(TAG, "rotateZ:" + rotateZ);

        mCamera.save();
        mCamera.rotate(rotateX, rotateY, rotateZ);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.preTranslate(-centerX, -centerY);
        mMatrix.postTranslate(centerX, centerY);
        canvas.concat(mMatrix);
        drawChild(canvas, mButton, time);

        canvas.restore();
    }


    private void getParamsX() {

        Log.i(TAG, "count: " + count);

        centerX = getMeasuredWidth() / 2;
        // 在中心点做变换
//        centerY = getMeasuredHeight() / 2;
        // 使得中心点位于mButton的底部，即底部之上的view视图整体做变换
        centerY = getMeasuredHeight() / 2 + mButton.getMeasuredHeight() / 2;

        // rotateX为正值时，view的中心点的上部向里转，顶部远离，中心点的底部拉近
        if(count % 2 == 0) {
            rotateX = 30;
        } else if(count % 2  == 1) {
            // 顶部拉近，底部远离
            rotateX = -30;
        }

    }


    private void getParamsZ() {

        Log.i(TAG, "count: " + count);

        // 在中心点做变换
//        centerX = getMeasuredWidth() / 2;
//        centerY = getMeasuredHeight() / 2;

        // 沿着mButton的右上角旋转
        centerX = getMeasuredWidth() / 2 + mButton.getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2 - mButton.getMeasuredHeight() / 2;


        // rotateZ为正值时，逆时针旋转
        if(count % 2 == 0) {
            rotateZ = 30;
        } else if(count % 2  == 1) {
            // 顺时针旋转
            rotateZ = -30;
        }
    }

    private void getParamsY() {

        Log.i(TAG, "count: " + count);

        // 在中心点做变换
//        centerX = getMeasuredWidth() / 2;
        // 使得中心点位于mButton的右边界，即右边界的左边的view视图整体做变换
        centerX = getMeasuredWidth() / 2 + mButton.getMeasuredWidth() / 2;

        centerY = getMeasuredHeight() / 2;

        // rotateY为正值时，view的中心点的右部向里转，右部远离，中心点的左部拉近
        if(count % 2 == 0) {
            rotateY = 30;
        } else if(count % 2  == 1) {
            // 右部拉近，中心点的左部远离
            rotateY = -30;
        }

    }


    @Override
    public void onClick(View view) {
        count++;
        invalidate();
    }


    // 布局
//    <?xml version="1.0" encoding="utf-8"?>
//    <com.mainitem.views.CameraGroup
//    xmlns:android="http://schemas.android.com/apk/res/android"
//    xmlns:tools="http://schemas.android.com/tools"
//    android:id="@+id/activity_item4"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent"
//    android:paddingBottom="@dimen/activity_vertical_margin"
//    android:paddingLeft="@dimen/activity_horizontal_margin"
//    android:paddingRight="@dimen/activity_horizontal_margin"
//    android:paddingTop="@dimen/activity_vertical_margin"
//    tools:context="com.mainitem.Item4Activity">
//
//    <Button
//    android:id="@+id/rotate"
//    android:layout_centerInParent="true"
//    android:text="Rotate_Test"
//    android:textAllCaps="false"
//    android:layout_width="150dp"
//    android:layout_height="150dp"/>
//
//    </com.mainitem.views.CameraGroup>

}
