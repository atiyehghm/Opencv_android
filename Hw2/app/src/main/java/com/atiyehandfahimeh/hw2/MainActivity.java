package com.atiyehandfahimeh.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        float norm_Of_g =(float) Math.sqrt(x * x + y * y + z * z);

        // Normalize the accelerometer vector
        x = (x / norm_Of_g);
        y = (y / norm_Of_g);
        z = (z / norm_Of_g);
        int inclination = (int) Math.round(Math.toDegrees(Math.acos(z)));
        Log.i("tag","incline is:"+inclination);

        if (inclination < 15 || inclination > 165)
        {
            Toast.makeText(this,"device flat - beep!",Toast.LENGTH_SHORT);
            TextView showing = findViewById(R.id.show1);
            showing.setText("it's flat");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //do nothing for now
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
