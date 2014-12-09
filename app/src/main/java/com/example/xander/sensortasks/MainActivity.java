package com.example.xander.sensortasks;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAcceleratorSensor;
    private Sensor mProximitySensor;
    private static final int CONST_ACCELEROMETER = 15;
    private MediaPlayer mRayGunPlayer;
    private MediaPlayer mRoarPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            mAcceleratorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mRayGunPlayer = MediaPlayer.create(this, R.raw.ray_gun);
        }
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            mRoarPlayer = MediaPlayer.create(this, R.raw.roar);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAcceleratorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == mAcceleratorSensor.getType()) {
            if (event.values[0] > CONST_ACCELEROMETER || event.values[1] > CONST_ACCELEROMETER || event.values[2] > CONST_ACCELEROMETER) {
                mRayGunPlayer.start();
            }
        }
        if (event.sensor.getType() == mProximitySensor.getType()) {
            Log.d(MainActivity.this.getLocalClassName(), event.values[0] + "");
            if (event.values[0] == 0f) {
                mRoarPlayer = MediaPlayer.create(this, R.raw.roar);
                mRoarPlayer.start();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
