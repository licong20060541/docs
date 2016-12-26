```
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            int flags;
            int curApiVersion = android.os.Build.VERSION.SDK_INT;
            // This work only for android 4.4+
            if(curApiVersion >= Build.VERSION_CODES.KITKAT){
                // This work only for android 4.4+
                // hide navigation bar permanently in android activity
                // touch the screen, the navigation bar will not show
                flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

            }else{
                // touch the screen, the navigation bar will show
                flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            // must be executed in main thread :)
            getWindow().getDecorView().setSystemUiVisibility(flags);
        }
    };

oncreate()

        final View decorView = getWindow().getDecorView();
        decorView.post(mHideRunnable); // hide the navigation bar
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility)
            {
                decorView.postDelayed(mHideRunnable, 2500); // hide the navigation bar
            }
        });


```


