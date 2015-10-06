package com.yxhuang.listview.DBstorge;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.yxhuang.listview.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MySettingActivityFragment extends PreferenceFragment {

    public MySettingActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(getActivity(), R.xml.preference, false);
        addPreferencesFromResource(R.xml.preference);

       Preference preference1 =  findPreference("network.broadcast");
        preference1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(getActivity(), preference.getKey() + " " + preference.getTitle() +  " " + newValue.toString(), Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }
}
