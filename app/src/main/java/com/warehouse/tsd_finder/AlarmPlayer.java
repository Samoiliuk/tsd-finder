package com.warehouse.tsd_finder;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

public class AlarmPlayer {
    private static MediaPlayer mp;

    public static synchronized void play(Context ctx, int seconds) {
        try {
            // Максимум гучності
            AudioManager am = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
            int max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            am.setStreamVolume(AudioManager.STREAM_MUSIC, max, 0);

            stop();

            mp = MediaPlayer.create(ctx, android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI);
            if (mp == null) return;
            mp.setLooping(true);
            mp.start();

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override public void run() { stop(); }
            }, seconds * 1000L);
        } catch (Exception ignored) {}
    }

    public static synchronized void stop() {
        try {
            if (mp != null) {
                if (mp.isPlaying()) mp.stop();
                mp.release();
                mp = null;
            }
        } catch (Exception ignored) {}
    }
}
