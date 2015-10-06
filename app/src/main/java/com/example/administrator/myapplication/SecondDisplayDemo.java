package com.example.administrator.myapplication;

import android.app.Presentation;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.Toast;


public class SecondDisplayDemo extends AppCompatActivity{
    private Presentation mPresentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_display_demo);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setupSecondeDisplay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresentation != null)  mPresentation.cancel();
    }

    // 显示第二个屏幕
    private void setupSecondeDisplay(){
        DisplayManager displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        Display defaultDisplay = displayManager.getDisplay(Display.DEFAULT_DISPLAY);
        Display[]  presentationDisplays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
        if (presentationDisplays.length > 0){
            for (Display presentationDisplay : presentationDisplays){
                if (presentationDisplay.getDisplayId() != defaultDisplay.getDisplayId()){
                    Presentation presentation = new MyPresentation(this, presentationDisplay);
                    presentation.show();
                    mPresentation = presentation;
                    return;
                }
            }
        }
        Toast.makeText(this,  "No second display found !",  Toast.LENGTH_SHORT).show();
    }

    private class  MyPresentation extends  Presentation{

        public MyPresentation(Context outerContext, Display display) {
            super(outerContext, display);

            // 第二个屏幕的视图
            setContentView(R.layout.seconde_screen);
        }
    }

}
