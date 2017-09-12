package yiihs.nfcgame.info;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

/**
 * Created by whdgm on 9/26/2016.
 */

public class PhoneInformation {
    String mPhoneNumber;
    TelephonyManager mTelephonyManager;
    SharedPreferences mSharedPreference;
    boolean isSearched;
    Context mContext;

    public PhoneInformation(Context context){
        mContext=context;
        loadPhoneNumber();
        loadPreference();
    }

    // get Phone Number from System. format is last 8 digit of number
    private void loadPhoneNumber(){
        mTelephonyManager = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneNumber = new StringBuilder(
                new StringBuilder(mTelephonyManager.getLine1Number())
                        .reverse().substring(0,8))
                .reverse().toString();
    }

    // get isSearched status from preference
    private void loadPreference(){
        mSharedPreference = mContext.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        isSearched = mSharedPreference.getBoolean("isSearched",false);
    }


    // return phoneNumber
    public String getPhoneNumber(){
        return mPhoneNumber;
    }

    // return isSearched
    public boolean getStatus(){
        return isSearched;
    }


    // set isSearched status
    public void setStatus(boolean status) {
        mSharedPreference.edit().putBoolean("isSearched", status).commit();
    }

}
