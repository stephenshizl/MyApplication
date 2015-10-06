package com.yxhuang.listview.DBstorge;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.yxhuang.listview.R;

public class SharedPreferenceActivity extends AppCompatActivity {
    private static final String SHPRE = "shared_preference";
    private static final String REMEMBER_WIFI = "remember_wifi";
    private static final String USER_NAME = "user_name";

    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        mSharedPreferences = getSharedPreferences(SHPRE, MODE_PRIVATE);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.ckb_remember_wifi);
        final EditText userName = (EditText) findViewById(R.id.edt_user_name);
        Button button = (Button) findViewById(R.id.btn_remember);

        if (mSharedPreferences != null){
            checkBox.setChecked(mSharedPreferences.getBoolean(REMEMBER_WIFI, false));
            userName.setText(mSharedPreferences.getString(USER_NAME, ""));
        } else {
            Log.i("yxh", "sharedPreferences no xml ");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SharedPreferences.Editor editor =   mSharedPreferences.edit();
                editor.putBoolean(REMEMBER_WIFI, checkBox.isChecked());
                editor.putString(USER_NAME, userName.getText().toString());
                editor.commit();
                Toast.makeText(SharedPreferenceActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
