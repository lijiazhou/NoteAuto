package com.jiazhou.auto.note.interfaces.listener;

/**
 * Created by lijiazhou on 19/2/17.
 */

public interface WaveformListener {
    public void waveformTouchStart(float x);
    public void waveformTouchMove(float x);
    public void waveformTouchEnd();
    public void waveformFling(float x);
    public void waveformDraw();
    public void waveformZoomIn();
    public void waveformZoomOut();
}
