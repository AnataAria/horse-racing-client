package com.theanimegroup.horse_racing_client.service;

import android.content.Context;
import android.media.MediaPlayer;

import com.theanimegroup.horse_racing_client.R;
import com.theanimegroup.horse_racing_client.constant.AudioStage;

import java.util.EnumMap;
import java.util.Map;

public class AudioMixer {
    private static AudioMixer instance;
    private Map<AudioStage, Integer> mediaPlayerList;
    private MediaPlayer currentPlay;

    private AudioMixer () {
        mediaPlayerList = new EnumMap<>(AudioStage.class);
        mediaPlayerList.put(AudioStage.LOGIN, R.raw.login);
        mediaPlayerList.put(AudioStage.BEGIN, R.raw.play);
        mediaPlayerList.put(AudioStage.CASH, R.raw.cash);
        mediaPlayerList.put(AudioStage.HOME, R.raw.home);
        mediaPlayerList.put(AudioStage.LOSE, R.raw.lose);
        mediaPlayerList.put(AudioStage.WIN, R.raw.login);
    }
    public static AudioMixer getInstance () {
        if (instance == null) {
            instance = new AudioMixer();
        }
        return instance;
    }

    public void playAudio (AudioStage stage, Context context) {
        if (currentPlay == null) {
            currentPlay = MediaPlayer.create(context, mediaPlayerList.get(stage));
            currentPlay.setLooping(true);
            currentPlay.start();
        } else {
            currentPlay.stop();
            currentPlay.release();
            currentPlay = MediaPlayer.create(context,mediaPlayerList.get(stage));
            currentPlay.setLooping(true);
            currentPlay.start();
        }
    }

    public void pauseAudio () {
        if (currentPlay != null && currentPlay.isPlaying()) {
            currentPlay.pause();
        }
    }

    public void continueAudio () {
        if (currentPlay != null && !currentPlay.isPlaying()) {
            currentPlay.start();
        }
    }

}
