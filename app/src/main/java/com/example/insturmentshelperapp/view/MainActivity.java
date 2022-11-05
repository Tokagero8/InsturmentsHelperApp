package com.example.insturmentshelperapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.view.tuner.TunerActivity;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    final int REQUEST_PERMISSION_CODE = 1000;
    final int ACTIVITY_CHANGE_DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();
        this.deleteDatabase("InstrumentHelpertAppDatabase");
    }

    public void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        }, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            AtomicInteger requestResult = new AtomicInteger();
            for (int grandResult : grantResults) {
                requestResult.addAndGet(grandResult);
            }
            if (grantResults.length > 0 && requestResult.get() == PackageManager.PERMISSION_GRANTED) {
                changeActivity();
            } else {
                finish();
                System.exit(0);
            }
        }
    }

    public void changeActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent nextIntent;
                if (MainActivity.this.isInstrumentSelected()) {
                    nextIntent = new Intent(MainActivity.this, TunerActivity.class);
                } else {
                    nextIntent = new Intent(MainActivity.this, TunerActivity.class);
                }
                MainActivity.this.startActivity(nextIntent);
                MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                MainActivity.this.finish();
            }
        }, ACTIVITY_CHANGE_DELAY);
    }

    private boolean isInstrumentSelected(){
        SharedPreferences instrumentPreferences = getSharedPreferences("instrumentPreferences",MODE_PRIVATE);
        int instrumentId = instrumentPreferences.getInt("selectedInstrumentId", -1);
        return instrumentId != -1;
    }
}