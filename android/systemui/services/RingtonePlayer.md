# play ringtones

```
/**
 * Service that offers to play ringtones by {@link Uri}, since our process has
 * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}.
 */
 
         mAudioService = IAudioService.Stub.asInterface(
                ServiceManager.getService(Context.AUDIO_SERVICE));
        try {
            mAudioService.setRingtonePlayer(mCallback);
        } catch (RemoteException e) {
            Log.e(TAG, "Problem registering RingtonePlayer: " + e);
        }
        
        
        private IRingtonePlayer mCallback = new IRingtonePlayer.Stub() {
        }
        
        interface IRingtonePlayer {
    /** Used for Ringtone.java playback */
    void play(IBinder token, in Uri uri, in AudioAttributes aa, float volume, boolean looping);
    void stop(IBinder token);
    boolean isPlaying(IBinder token);
    void setPlaybackProperties(IBinder token, float volume, boolean looping);

    /** Used for Notification sound playback. */
    void playAsync(in Uri uri, in UserHandle user, boolean looping, in AudioAttributes aa);
    void stopAsync();

    /** Return the title of the media. */
    String getTitle(in Uri uri);

    ParcelFileDescriptor openRingtone(in Uri uri);
}
```