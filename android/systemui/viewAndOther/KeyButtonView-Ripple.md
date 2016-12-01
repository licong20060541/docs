```
    package com.android.systemui.statusbar.policy;  
      
    import android.animation.Animator;  
    import android.animation.AnimatorListenerAdapter;  
    import android.animation.ObjectAnimator;  
    import android.content.Context;  
    import android.graphics.Canvas;  
    import android.graphics.CanvasProperty;  
    import android.graphics.ColorFilter;  
    import android.graphics.Paint;  
    import android.graphics.PixelFormat;  
    import android.graphics.drawable.Drawable;  
    import android.view.DisplayListCanvas;  
    import android.view.RenderNodeAnimator;  
    import android.view.View;  
    import android.view.animation.Interpolator;  
      
    import com.android.systemui.Interpolators;  
    import com.android.systemui.R;  
      
    import java.util.ArrayList;  
    import java.util.HashSet;  
      
    public class KeyButtonRipple extends Drawable {  
      
        private static final float GLOW_MAX_SCALE_FACTOR = 1.35f;  
        private static final float GLOW_MAX_ALPHA = 0.2f;  
        private static final int ANIMATION_DURATION_SCALE = 350;  
        private static final int ANIMATION_DURATION_FADE = 450;  
      
        private Paint mRipplePaint;  
        private CanvasProperty<Float> mLeftProp;  
        private CanvasProperty<Float> mTopProp;  
        private CanvasProperty<Float> mRightProp;  
        private CanvasProperty<Float> mBottomProp;  
        private CanvasProperty<Float> mRxProp;  
        private CanvasProperty<Float> mRyProp;  
        private CanvasProperty<Paint> mPaintProp;  
        private float mGlowAlpha = 0f;  
        private float mGlowScale = 1f;  
        private boolean mPressed;  
        private boolean mDrawingHardwareGlow;  
        private int mMaxWidth;  
      
        private final Interpolator mInterpolator = new LogInterpolator();  
        private boolean mSupportHardware;  
        private final View mTargetView;  
      
        private final HashSet<Animator> mRunningAnimations = new HashSet<>();  
        private final ArrayList<Animator> mTmpArray = new ArrayList<>();  
      
        public KeyButtonRipple(Context ctx, View targetView) {  
            mMaxWidth =  ctx.getResources().getDimensionPixelSize(R.dimen.key_button_ripple_max_width);  
            mTargetView = targetView;  
        }  
      
        private Paint getRipplePaint() {  
            if (mRipplePaint == null) {  
                mRipplePaint = new Paint();  
                mRipplePaint.setAntiAlias(true);  
                mRipplePaint.setColor(0xffffffff);  
            }  
            return mRipplePaint;  
        }  
      
        private void drawSoftware(Canvas canvas) {  
            if (mGlowAlpha > 0f) {  
                final Paint p = getRipplePaint();  
                p.setAlpha((int)(mGlowAlpha * 255f));  
      
                final float w = getBounds().width();  
                final float h = getBounds().height();  
                final boolean horizontal = w > h;  
                final float diameter = getRippleSize() * mGlowScale;  
                final float radius = diameter * .5f;  
                final float cx = w * .5f;  
                final float cy = h * .5f;  
                final float rx = horizontal ? radius : cx;  
                final float ry = horizontal ? cy : radius;  
                final float corner = horizontal ? cy : cx;  
      
                canvas.drawRoundRect(cx - rx, cy - ry,  
                        cx + rx, cy + ry,  
                        corner, corner, p);  
            }  
        }  
      
        @Override  
        public void draw(Canvas canvas) {  
            mSupportHardware = canvas.isHardwareAccelerated();  
            if (mSupportHardware) {  
                drawHardware((DisplayListCanvas) canvas);  
            } else {  
                drawSoftware(canvas);  
            }  
        }  
      
        @Override  
        public void setAlpha(int alpha) {  
            // Not supported.  
        }  
      
        @Override  
        public void setColorFilter(ColorFilter colorFilter) {  
            // Not supported.  
        }  
      
        @Override  
        public int getOpacity() {  
            return PixelFormat.TRANSLUCENT;  
        }  
      
        private boolean isHorizontal() {  
            return getBounds().width() > getBounds().height();  
        }  
      
        private void drawHardware(DisplayListCanvas c) {  
            if (mDrawingHardwareGlow) {  
                c.drawRoundRect(mLeftProp, mTopProp, mRightProp, mBottomProp, mRxProp, mRyProp,  
                        mPaintProp);  
            }  
        }  
      
        public float getGlowAlpha() {  
            return mGlowAlpha;  
        }  
      
        public void setGlowAlpha(float x) {  
            mGlowAlpha = x;  
            invalidateSelf();  
        }  
      
        public float getGlowScale() {  
            return mGlowScale;  
        }  
      
        public void setGlowScale(float x) {  
            mGlowScale = x;  
            invalidateSelf();  
        }  
      
        @Override  
        protected boolean onStateChange(int[] state) {  
            boolean pressed = false;  
            for (int i = 0; i < state.length; i++) {  
                if (state[i] == android.R.attr.state_pressed) {  
                    pressed = true;  
                    break;  
                }  
            }  
            if (pressed != mPressed) {  
                setPressed(pressed);  
                mPressed = pressed;  
                return true;  
            } else {  
                return false;  
            }  
        }  
      
        @Override  
        public void jumpToCurrentState() {  
            cancelAnimations();  
        }  
      
        @Override  
        public boolean isStateful() {  
            return true;  
        }  
      
        public void setPressed(boolean pressed) {  
            if (mSupportHardware) {  
                setPressedHardware(pressed);  
            } else {  
                setPressedSoftware(pressed);  
            }  
        }  
      
        private void cancelAnimations() {  
            mTmpArray.addAll(mRunningAnimations);  
            int size = mTmpArray.size();  
            for (int i = 0; i < size; i++) {  
                Animator a = mTmpArray.get(i);  
                a.cancel();  
            }  
            mTmpArray.clear();  
            mRunningAnimations.clear();  
        }  
      
        private void setPressedSoftware(boolean pressed) {  
            if (pressed) {  
                enterSoftware();  
            } else {  
                exitSoftware();  
            }  
        }  
      
        private void enterSoftware() {  
            cancelAnimations();  
            mGlowAlpha = GLOW_MAX_ALPHA;  
            ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(this, "glowScale",  
                    0f, GLOW_MAX_SCALE_FACTOR);  
            scaleAnimator.setInterpolator(mInterpolator);  
            scaleAnimator.setDuration(ANIMATION_DURATION_SCALE);  
            scaleAnimator.addListener(mAnimatorListener);  
            scaleAnimator.start();  
            mRunningAnimations.add(scaleAnimator);  
        }  
      
        private void exitSoftware() {  
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(this, "glowAlpha", mGlowAlpha, 0f);  
            alphaAnimator.setInterpolator(Interpolators.ALPHA_OUT);  
            alphaAnimator.setDuration(ANIMATION_DURATION_FADE);  
            alphaAnimator.addListener(mAnimatorListener);  
            alphaAnimator.start();  
            mRunningAnimations.add(alphaAnimator);  
        }  
      
        private void setPressedHardware(boolean pressed) {  
            if (pressed) {  
                enterHardware();  
            } else {  
                exitHardware();  
            }  
        }  
      
        /** 
         * Sets the left/top property for the round rect to {@code prop} depending on whether we are 
         * horizontal or vertical mode. 
         */  
        private void setExtendStart(CanvasProperty<Float> prop) {  
            if (isHorizontal()) {  
                mLeftProp = prop;  
            } else {  
                mTopProp = prop;  
            }  
        }  
      
        private CanvasProperty<Float> getExtendStart() {  
            return isHorizontal() ? mLeftProp : mTopProp;  
        }  
      
        /** 
         * Sets the right/bottom property for the round rect to {@code prop} depending on whether we are 
         * horizontal or vertical mode. 
         */  
        private void setExtendEnd(CanvasProperty<Float> prop) {  
            if (isHorizontal()) {  
                mRightProp = prop;  
            } else {  
                mBottomProp = prop;  
            }  
        }  
      
        private CanvasProperty<Float> getExtendEnd() {  
            return isHorizontal() ? mRightProp : mBottomProp;  
        }  
      
        private int getExtendSize() {  
            return isHorizontal() ? getBounds().width() : getBounds().height();  
        }  
      
        private int getRippleSize() {  
            int size = isHorizontal() ? getBounds().width() : getBounds().height();  
            return Math.min(size, mMaxWidth);  
        }  
      
        private void enterHardware() {  
            cancelAnimations();  
            mDrawingHardwareGlow = true;  
            setExtendStart(CanvasProperty.createFloat(getExtendSize() / 2));  
            final RenderNodeAnimator startAnim = new RenderNodeAnimator(getExtendStart(),  
                    getExtendSize()/2 - GLOW_MAX_SCALE_FACTOR * getRippleSize()/2);  
            startAnim.setDuration(ANIMATION_DURATION_SCALE);  
            startAnim.setInterpolator(mInterpolator);  
            startAnim.addListener(mAnimatorListener);  
            startAnim.setTarget(mTargetView);  
      
            setExtendEnd(CanvasProperty.createFloat(getExtendSize() / 2));  
            final RenderNodeAnimator endAnim = new RenderNodeAnimator(getExtendEnd(),  
                    getExtendSize()/2 + GLOW_MAX_SCALE_FACTOR * getRippleSize()/2);  
            endAnim.setDuration(ANIMATION_DURATION_SCALE);  
            endAnim.setInterpolator(mInterpolator);  
            endAnim.addListener(mAnimatorListener);  
            endAnim.setTarget(mTargetView);  
      
            if (isHorizontal()) {  
                mTopProp = CanvasProperty.createFloat(0f);  
                mBottomProp = CanvasProperty.createFloat(getBounds().height());  
                mRxProp = CanvasProperty.createFloat(getBounds().height()/2);  
                mRyProp = CanvasProperty.createFloat(getBounds().height()/2);  
            } else {  
                mLeftProp = CanvasProperty.createFloat(0f);  
                mRightProp = CanvasProperty.createFloat(getBounds().width());  
                mRxProp = CanvasProperty.createFloat(getBounds().width()/2);  
                mRyProp = CanvasProperty.createFloat(getBounds().width()/2);  
            }  
      
            mGlowScale = GLOW_MAX_SCALE_FACTOR;  
            mGlowAlpha = GLOW_MAX_ALPHA;  
            mRipplePaint = getRipplePaint();  
            mRipplePaint.setAlpha((int) (mGlowAlpha * 255));  
            mPaintProp = CanvasProperty.createPaint(mRipplePaint);  
      
            startAnim.start();  
            endAnim.start();  
            mRunningAnimations.add(startAnim);  
            mRunningAnimations.add(endAnim);  
      
            invalidateSelf();  
        }  
      
        private void exitHardware() {  
            mPaintProp = CanvasProperty.createPaint(getRipplePaint());  
            final RenderNodeAnimator opacityAnim = new RenderNodeAnimator(mPaintProp,  
                    RenderNodeAnimator.PAINT_ALPHA, 0);  
            opacityAnim.setDuration(ANIMATION_DURATION_FADE);  
            opacityAnim.setInterpolator(Interpolators.ALPHA_OUT);  
            opacityAnim.addListener(mAnimatorListener);  
            opacityAnim.setTarget(mTargetView);  
      
            opacityAnim.start();  
            mRunningAnimations.add(opacityAnim);  
      
            invalidateSelf();  
        }  
      
        private final AnimatorListenerAdapter mAnimatorListener =  
                new AnimatorListenerAdapter() {  
            @Override  
            public void onAnimationEnd(Animator animation) {  
                mRunningAnimations.remove(animation);  
                if (mRunningAnimations.isEmpty() && !mPressed) {  
                    mDrawingHardwareGlow = false;  
                    invalidateSelf();  
                }  
            }  
        };  
      
        /** 
         * Interpolator with a smooth log deceleration 
         */  
        private static final class LogInterpolator implements Interpolator {  
            @Override  
            public float getInterpolation(float input) {  
                return 1 - (float) Math.pow(400, -input * 1.4);  
            }  
        }  
    }  
```


```
    package com.android.systemui.statusbar.policy;  
      
    import android.app.ActivityManager;  
    import android.content.Context;  
    import android.content.res.Configuration;  
    import android.content.res.TypedArray;  
    import android.graphics.drawable.Drawable;  
    import android.graphics.drawable.Icon;  
    import android.hardware.input.InputManager;  
    import android.media.AudioManager;  
    import android.os.AsyncTask;  
    import android.os.Bundle;  
    import android.os.SystemClock;  
    import android.util.AttributeSet;  
    import android.util.TypedValue;  
    import android.view.HapticFeedbackConstants;  
    import android.view.InputDevice;  
    import android.view.KeyCharacterMap;  
    import android.view.KeyEvent;  
    import android.view.MotionEvent;  
    import android.view.SoundEffectConstants;  
    import android.view.View;  
    import android.view.ViewConfiguration;  
    import android.view.accessibility.AccessibilityEvent;  
    import android.view.accessibility.AccessibilityNodeInfo;  
    import android.widget.ImageView;  
      
    import com.android.systemui.R;  
      
    import static android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK;  
    import static android.view.accessibility.AccessibilityNodeInfo.ACTION_LONG_CLICK;  
      
    public class KeyButtonView extends ImageView {  
      
        private int mContentDescriptionRes;  
        private long mDownTime;  
        private int mCode;  
        private int mTouchSlop;  
        private boolean mSupportsLongpress = true;  
        private AudioManager mAudioManager;  
        private boolean mGestureAborted;  
        private boolean mLongClicked;  
      
        private final Runnable mCheckLongPress = new Runnable() {  
            public void run() {  
                if (isPressed()) {  
                    // Log.d("KeyButtonView", "longpressed: " + this);  
                    if (isLongClickable()) {  
                        // Just an old-fashioned ImageView  
                        performLongClick();  
                        mLongClicked = true;  
                    } else if (mSupportsLongpress) {  
                        sendEvent(KeyEvent.ACTION_DOWN, KeyEvent.FLAG_LONG_PRESS);  
                        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_LONG_CLICKED);  
                        mLongClicked = true;  
                    }  
                }  
            }  
        };  
      
        public KeyButtonView(Context context, AttributeSet attrs) {  
            this(context, attrs, 0);  
        }  
      
        public KeyButtonView(Context context, AttributeSet attrs, int defStyle) {  
            super(context, attrs);  
      
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KeyButtonView,  
                    defStyle, 0);  
      
            mCode = a.getInteger(R.styleable.KeyButtonView_keyCode, 0);  
      
            mSupportsLongpress = a.getBoolean(R.styleable.KeyButtonView_keyRepeat, true);  
      
            TypedValue value = new TypedValue();  
            if (a.getValue(R.styleable.KeyButtonView_android_contentDescription, value)) {  
                mContentDescriptionRes = value.resourceId;  
            }  
      
            a.recycle();  
      
      
            setClickable(true);  
            mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();  
            mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);  
            setBackground(new KeyButtonRipple(context, this));  
        }  
      
        public void setCode(int code) {  
            mCode = code;  
        }  
      
        public void loadAsync(String uri) {  
            new AsyncTask<String, Void, Drawable>() {  
                @Override  
                protected Drawable doInBackground(String... params) {  
                    return Icon.createWithContentUri(params[0]).loadDrawable(mContext);  
                }  
      
                @Override  
                protected void onPostExecute(Drawable drawable) {  
                    setImageDrawable(drawable);  
                }  
            }.execute(uri);  
        }  
      
        @Override  
        protected void onConfigurationChanged(Configuration newConfig) {  
            super.onConfigurationChanged(newConfig);  
      
            if (mContentDescriptionRes != 0) {  
                setContentDescription(mContext.getString(mContentDescriptionRes));  
            }  
        }  
      
        @Override  
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {  
            super.onInitializeAccessibilityNodeInfo(info);  
            if (mCode != 0) {  
                info.addAction(new AccessibilityNodeInfo.AccessibilityAction(ACTION_CLICK, null));  
                if (mSupportsLongpress || isLongClickable()) {  
                    info.addAction(  
                            new AccessibilityNodeInfo.AccessibilityAction(ACTION_LONG_CLICK, null));  
                }  
            }  
        }  
      
        @Override  
        protected void onWindowVisibilityChanged(int visibility) {  
            super.onWindowVisibilityChanged(visibility);  
            if (visibility != View.VISIBLE) {  
                jumpDrawablesToCurrentState();  
            }  
        }  
      
        @Override  
        public boolean performAccessibilityActionInternal(int action, Bundle arguments) {  
            if (action == ACTION_CLICK && mCode != 0) {  
                sendEvent(KeyEvent.ACTION_DOWN, 0, SystemClock.uptimeMillis());  
                sendEvent(KeyEvent.ACTION_UP, 0);  
                sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);  
                playSoundEffect(SoundEffectConstants.CLICK);  
                return true;  
            } else if (action == ACTION_LONG_CLICK && mCode != 0) {  
                sendEvent(KeyEvent.ACTION_DOWN, KeyEvent.FLAG_LONG_PRESS);  
                sendEvent(KeyEvent.ACTION_UP, 0);  
                sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_LONG_CLICKED);  
                return true;  
            }  
            return super.performAccessibilityActionInternal(action, arguments);  
        }  
      
        public boolean onTouchEvent(MotionEvent ev) {  
            final int action = ev.getAction();  
            int x, y;  
            if (action == MotionEvent.ACTION_DOWN) {  
                mGestureAborted = false;  
            }  
            if (mGestureAborted) {  
                return false;  
            }  
      
            switch (action) {  
                case MotionEvent.ACTION_DOWN:  
                    mDownTime = SystemClock.uptimeMillis();  
                    mLongClicked = false;  
                    setPressed(true);  
                    if (mCode != 0) {  
                        sendEvent(KeyEvent.ACTION_DOWN, 0, mDownTime);  
                    } else {  
                        // Provide the same haptic feedback that the system offers for virtual keys.  
                        performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);  
                    }  
                    removeCallbacks(mCheckLongPress);  
                    postDelayed(mCheckLongPress, ViewConfiguration.getLongPressTimeout());  
                    break;  
                case MotionEvent.ACTION_MOVE:  
                    x = (int)ev.getX();  
                    y = (int)ev.getY();  
                    setPressed(x >= -mTouchSlop  
                            && x < getWidth() + mTouchSlop  
                            && y >= -mTouchSlop  
                            && y < getHeight() + mTouchSlop);  
                    break;  
                case MotionEvent.ACTION_CANCEL:  
                    setPressed(false);  
                    if (mCode != 0) {  
                        sendEvent(KeyEvent.ACTION_UP, KeyEvent.FLAG_CANCELED);  
                    }  
                    removeCallbacks(mCheckLongPress);  
                    break;  
                case MotionEvent.ACTION_UP:  
                    final boolean doIt = isPressed() && !mLongClicked;  
                    setPressed(false);  
                    if (mCode != 0) {  
                        if (doIt) {  
                            sendEvent(KeyEvent.ACTION_UP, 0);  
                            sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);  
                            playSoundEffect(SoundEffectConstants.CLICK);  
                        } else {  
                            sendEvent(KeyEvent.ACTION_UP, KeyEvent.FLAG_CANCELED);  
                        }  
                    } else {  
                        // no key code, just a regular ImageView  
                        if (doIt) {  
                            performClick();  
                        }  
                    }  
                    removeCallbacks(mCheckLongPress);  
                    break;  
            }  
      
            return true;  
        }  
      
        public void playSoundEffect(int soundConstant) {  
            mAudioManager.playSoundEffect(soundConstant, ActivityManager.getCurrentUser());  
        };  
      
        public void sendEvent(int action, int flags) {  
            sendEvent(action, flags, SystemClock.uptimeMillis());  
        }  
      
        void sendEvent(int action, int flags, long when) {  
            final int repeatCount = (flags & KeyEvent.FLAG_LONG_PRESS) != 0 ? 1 : 0;  
            final KeyEvent ev = new KeyEvent(mDownTime, when, action, mCode, repeatCount,  
                    0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,  
                    flags | KeyEvent.FLAG_FROM_SYSTEM | KeyEvent.FLAG_VIRTUAL_HARD_KEY,  
                    InputDevice.SOURCE_KEYBOARD);  
            InputManager.getInstance().injectInputEvent(ev,  
                    InputManager.INJECT_INPUT_EVENT_MODE_ASYNC);  
        }  
      
        public void abortCurrentGesture() {  
            setPressed(false);  
            mGestureAborted = true;  
        }  
    }  
```

