package com.yxhuang.customview.CustomView;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.yxhuang.customview.R;


public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        final PieChart pie = (PieChart) this.findViewById(R.id.Pie);
        Resources res = getResources();

        pie.addItem("Agamemnon", 2, res.getColor(R.color.accent_material_dark));
        pie.addItem("Bocephus", 3.5f, res.getColor(R.color.abc_background_cache_hint_selector_material_dark));
        pie.addItem("Calliope", 2.5f, res.getColor(R.color.abc_primary_text_disable_only_material_dark));
        pie.addItem("Daedalus", 3, res.getColor(R.color.button_material_dark));
        pie.addItem("Euripides", 1, res.getColor(R.color.accent_material_dark));
        pie.addItem("Ganymede", 3, res.getColor(R.color.secondary_text_default_material_light));

        ((Button) findViewById(R.id.Reset)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pie.setCurrentItem(0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pie_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
