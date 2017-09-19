package com.example.otto.stepcounter;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity implements SensorEventListener{

    private SensorManager mSensorManager;
    private TextView count;
    private int stepsInSensor = 1;
    boolean activityRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        count = findViewById(R.id.textView);
        Button btn = findViewById(R.id.button);
        count.setText(String.valueOf(0));

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                count.setText(String.valueOf(0));
                stepsInSensor = 1;
                Log.d("Reset", "Reset");
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (countSensor != null) {
            mSensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();

        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting step events
//        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (activityRunning) {
            Log.d("AABLYAT", sensorEvent.toString());

            count.setText(String.valueOf(stepsInSensor++));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
