package com.faceoffaerie.interfaces;

public class BackgroundMusicModel {

    public interface OnMusicStateListener
    {
        void stateChanged();
    }

    private static BackgroundMusicModel mInstance;
    private OnMusicStateListener mListener;
    private boolean isBackgroundMode = false;
    private BackgroundMusicModel()
    {

    }
    public static BackgroundMusicModel getInstance() {
        if(mInstance == null)
        {
            mInstance = new BackgroundMusicModel();
        }
        return mInstance;
    }
    public void setListener(OnMusicStateListener listener)
    {
        mListener = listener;
    }
    public void changeState(boolean mode) {
        if(mListener != null)
        {
            isBackgroundMode = mode;
            notifyStateChange();
        }
    }
    public boolean getState()
    {
        return isBackgroundMode;
    }
    private void notifyStateChange()
    {
        mListener.stateChanged();
    }
}