package com.automattic.simplenote;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

import com.simperium.Simperium;
import com.simperium.SimperiumNotInitializedException;

import org.wordpress.passcodelock.PasscodePreferenceFragment;

/**
 * Created by daniel on 19/08/16.
 *
 * Helper class to catch passcode changes
 *
 */
public class PasscodePreferenceFragmentWithResult extends PasscodePreferenceFragment {

    private static final String TAG = "PasscodePreferenceFragmentWithResult";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        int passwordHash =  PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(org.wordpress.passcodelock.BuildConfig.PASSWORD_PREFERENCE_KEY, -1);


        switch (requestCode){
            
            case PasscodePreferenceFragment.CHANGE_PASSWORD:
                if (BuildConfig.DEBUG){
                    Log.d(TAG, "CHANGE_PASSWORD: " + passwordHash);
                }
                if (resultCode == Activity.RESULT_OK) {
                    changeDatabasePassword(""+passwordHash);
                }
                break;
            case PasscodePreferenceFragment.DISABLE_PASSLOCK:
                if (BuildConfig.DEBUG){
                    Log.d(TAG, "DISABLE_PASSLOCK: " + passwordHash);
                }
                if (resultCode == Activity.RESULT_OK) {
                    changeDatabasePassword(null);
                }
                break;
            case PasscodePreferenceFragment.ENABLE_PASSLOCK:
                if (BuildConfig.DEBUG){
                    Log.d(TAG, "ENABLE_PASSLOCK: " + passwordHash);
                }
                if (resultCode == Activity.RESULT_OK) {
                    changeDatabasePassword(""+passwordHash);
                }
                break;
            
            
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);


    }

    protected void changeDatabasePassword(String password) {
        try {
            Simperium.getInstance().changePassword(password);
        } catch (SimperiumNotInitializedException e) {
            e.printStackTrace();
        }
    }
}
