package com.jiualng.cardviewpagerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jiualng.cardviewpagerdemo.ui.main.MainFragment;
import com.jiualng.customview.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }



}