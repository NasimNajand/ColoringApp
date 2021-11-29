package com.najand.simplecoloring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.najand.simplecoloring.commons.Common;
import com.thebluealliance.spectrum.SpectrumPalette;

public class PaintActivity extends AppCompatActivity implements SpectrumPalette.OnColorSelectedListener {

    private SpectrumPalette palette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        palette = findViewById(R.id.palette);
        setPaletteListener();
    }

    private void setPaletteListener() {
        palette.setOnColorSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save){

        }else if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onColorSelected(int color) {
        Common.COLOR_SELECTED = color;
    }
}