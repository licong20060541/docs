#Recents流程简析

##第一条线路
##获取最近任务SystemServicesProxy.java
```
    public List<ActivityManager.RecentTaskInfo> getRecentTasks(int numLatestTasks, int userId,
            boolean includeFrontMostExcludedTask, ArraySet<Integer> quietProfileIds) {
                    try {
            tasks = mAm.getRecentTasksForUser(numTasksToQuery, flags, userId);
        } catch (Exception e) {
            Log.e(TAG, "Failed to get recent tasks", e);
        }
   }
```

##RecentsTaskLoadPlan.java
```

    public synchronized void preloadRawTasks(boolean includeFrontMostExcludedTask) {
        int currentUserId = UserHandle.USER_CURRENT;
        updateCurrentQuietProfilesCache(currentUserId);
        SystemServicesProxy ssp = Recents.getSystemServices();//!!!
        mRawTasks = ssp.getRecentTasks(ActivityManager.getMaxRecentTasksStatic(),
                currentUserId, includeFrontMostExcludedTask, mCurrentQuietProfiles);

        // Since the raw tasks are given in most-recent to least-recent order, we need to reverse it
        Collections.reverse(mRawTasks);
    }


    public synchronized void preloadPlan(RecentsTaskLoader loader, int runningTaskId,
            boolean includeFrontMostExcludedTask) {
        int taskCount = mRawTasks.size();
        for (int i = 0; i < taskCount; i++) {
            ActivityManager.RecentTaskInfo t = mRawTasks.get(i);
            allTasks.add(task);
        }
           // Initialize the stacks
        mStack = new TaskStack();
        mStack.setTasks(mContext, allTasks, false /* notifyStackChanges */);
```

##RecentsTvActivity
```
    private void updateRecentsTasks() {
        RecentsTaskLoader loader = Recents.getTaskLoader();
        RecentsTaskLoadPlan plan = RecentsImpl.consumeInstanceLoadPlan();
        if (plan == null) {
            plan = loader.createLoadPlan(this);
        }

        RecentsConfiguration config = Recents.getConfiguration();
        RecentsActivityLaunchState launchState = config.getLaunchState();
        if (!plan.hasTasks()) {
            loader.preloadTasks(plan, -1, !launchState.launchedFromHome);
        }

        int numVisibleTasks = TaskCardView.getNumberOfVisibleTasks(getApplicationContext());
        mLaunchedFromHome = launchState.launchedFromHome;
        TaskStack stack = plan.getTaskStack();//即上面的mStack
        RecentsTaskLoadPlan.Options loadOpts = new RecentsTaskLoadPlan.Options();
        loadOpts.runningTaskId = launchState.launchedToTaskId;
        loadOpts.numVisibleTasks = numVisibleTasks;
        loadOpts.numVisibleTaskThumbnails = numVisibleTasks;
        loader.loadTasks(this, plan, loadOpts);

        List stackTasks = stack.getStackTasks();//stack即上面的TaskStack mStack
        Collections.reverse(stackTasks);
        if (mTaskStackViewAdapter == null) {
            mTaskStackViewAdapter = new TaskStackHorizontalViewAdapter(stackTasks);
     // 设置到了adapter
```


##第二条线路
##RecentsTaskLoader
```
// 利用RecentsTaskLoadPlan加载最近任务，加载到了mLoadQueue中
    /** Begins loading the heavy task data according to the specified options. */
    public void loadTasks(Context context, RecentsTaskLoadPlan plan,
            RecentsTaskLoadPlan.Options opts) {
        if (opts == null) {
            throw new RuntimeException("Requires load options");
        }
        plan.executePlan(opts, this, mLoadQueue);
        if (!opts.onlyLoadForCache) {
            mNumVisibleTasksLoaded = opts.numVisibleTasks;
            mNumVisibleThumbnailsLoaded = opts.numVisibleTaskThumbnails;

            // Start the loader
            mLoader.start(context);
        }
    }
```

#BackgroundTaskLoader
```
    /** Restarts the loader thread */
    void start(Context context) {
        mContext = context;
        mCancelled = false;
        // Notify the load thread to start loading
        synchronized(mLoadThread) {
            mLoadThread.notifyAll();//使能下方wait
        }
    }
    
    
    @Override
    public void run() {
        while (true) {
            if (mCancelled) {
                // We have to unset the context here, since the background thread may be using it
                // when we call stop()
                mContext = null;
                // If we are cancelled, then wait until we are started again
                synchronized(mLoadThread) {
                    try {
                        mLoadThread.wait();//一开始准备就绪
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            } else {
            // xxx
                final Task t = mLoadQueue.nextTask();
                 Drawable cachedIcon = mIconCache.get(t.key);
                ThumbnailData cachedThumbnailData = mThumbnailCache.get(t.key);
                if (!mCancelled) {
                            // Notify that the task data has changed
                            final Drawable newIcon = cachedIcon;
                            final ThumbnailData newThumbnailData = cachedThumbnailData;
                            mMainThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                // Task ！！！！
                                    t.notifyTaskDataLoaded(newThumbnailData.thumbnail, newIcon,
                                            newThumbnailData.thumbnailInfo);
                                }
                            });
                        }
            }
    SystemServicesProxy.java下
          /**
     * Returns a task thumbnail from the activity manager
     */
    public void getThumbnail(int taskId, ThumbnailData thumbnailDataOut) {
        if (mAm == null) {
            return;
        }

        ActivityManager.TaskThumbnail taskThumbnail = mAm.getTaskThumbnail(taskId);
   }

```
#Task
```
    public void notifyTaskDataLoaded(Bitmap thumbnail, Drawable applicationIcon,
            ActivityManager.TaskThumbnailInfo thumbnailInfo) {
        this.icon = applicationIcon;
        this.thumbnail = thumbnail;
        int callbackCount = mCallbacks.size();
        for (int i = 0; i < callbackCount; i++) {
            mCallbacks.get(i).onTaskDataLoaded(this, thumbnailInfo);//回调了，如下
        }
    }
```


#TaskView
```
调用了mTask.addCallback(this);//Task，因此可以回调过来了


    @Override
    public void onTaskDataLoaded(Task task, ActivityManager.TaskThumbnailInfo thumbnailInfo) {
        // Update each of the views to the new task data
        mThumbnailView.onTaskDataLoaded(thumbnailInfo);
        mHeaderView.onTaskDataLoaded();
    }
```

#TaskViewThumbnail
```
public class TaskViewThumbnail extends View {
    /**
     * Called when the bound task's data has loaded and this view should update to reflect the
     * changes.
     */
    void onTaskDataLoaded(ActivityManager.TaskThumbnailInfo thumbnailInfo) {
        if (mTask.thumbnail != null) {
            setThumbnail(mTask.thumbnail, thumbnailInfo);
        } else {
            setThumbnail(null, null);
        }
    }
        /** Sets the thumbnail to a given bitmap. */
    void setThumbnail(Bitmap bm, ActivityManager.TaskThumbnailInfo thumbnailInfo) {
        if (bm != null) {
            mBitmapShader = new BitmapShader(bm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mDrawPaint.setShader(mBitmapShader); //！！！
            mThumbnailRect.set(0, 0, bm.getWidth(), bm.getHeight());
            mThumbnailInfo = thumbnailInfo;
            updateThumbnailScale();
        } else {
            mBitmapShader = null;
            mDrawPaint.setShader(null);
            mThumbnailRect.setEmpty();
            mThumbnailInfo = null;
        }
    }
    
    
    //之后在onDraw下，利用了mDrawPaint
                // Draw the thumbnail
            canvas.drawRoundRect(0, topOffset, thumbnailWidth, thumbnailHeight,
                    mCornerRadius, mCornerRadius, mDrawPaint);
```

##栈的变化
##RecentsImpl
```
        // Register the task stack listener
        mTaskStackListener = new TaskStackListenerImpl();
        SystemServicesProxy ssp = Recents.getSystemServices();
        ssp.registerTaskStackListener(mTaskStackListener);
```

##SystemServicesProxy
```
    /**
     * Registers a task stack listener with the system.
     * This should be called on the main thread.
     */
    public void registerTaskStackListener(TaskStackListener listener) {
        if (mIam == null) return;

        mTaskStackListeners.add(listener);
        if (mTaskStackListeners.size() == 1) {
            // Register mTaskStackListener to IActivityManager only once if needed.
            try { //AMS中注册了!!!
                mIam.registerTaskStackListener(mTaskStackListener);
            } catch (Exception e) {
                Log.w(TAG, "Failed to call registerTaskStackListener", e);
            }
        }
    }
```