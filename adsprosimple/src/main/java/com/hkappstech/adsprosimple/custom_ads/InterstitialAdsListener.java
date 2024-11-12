package com.hkappstech.adsprosimple.custom_ads;


public interface InterstitialAdsListener {

    OnCloseListener mOnPositiveListener = null;

    interface OnCloseListener {
        void onAdsClose();
    }

}
