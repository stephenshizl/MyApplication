package com.yxhuang.listview.Fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yxhuang.listview.R;

public class MainFragmentActivity extends AppCompatActivity {
    private RadioGroup mRadioGroupDialogType;
    private Button btn_create_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        mRadioGroupDialogType = (RadioGroup) findViewById(R.id.rad_dialog_type);
        btn_create_dialog = (Button) findViewById(R.id.btn_create_dialog);
        btn_create_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainFragmentActivity.this, "Dialog", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
