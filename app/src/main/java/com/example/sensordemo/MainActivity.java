package com.example.sensordemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.security.PrivateKey;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    private Sensor mSensorProximity;
    private Sensor mSensorLight;


    private TextView mTextSensorProximity;
    private TextView mTextSensorLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //get the instance of the sensor manager from the system service.
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

//        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//
//        StringBuilder sensotText = new StringBuilder();
//
//        for(Sensor currentSensor: sensorList) {
//            sensotText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
//        }
//
//        TextView sensotTextView = findViewById(R.id.sensor_list);
//        sensotTextView.setText(sensotText);

        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);

        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        //get error string from xml resourse
        String sensor_error = getResources().getString(R.string.error_no_sensor);


        // test all available light sensor exists
        if(mSensorLight == null){
            //if it does NOT exist
            mTextSensorLight.setText(sensor_error);
        }

        //test that all proximity sensor exists
        if (mSensorProximity == null) {

            mTextSensorProximity.setText(sensor_error);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mSensorProximity != null) {

            mSensorManager.registerListener(this,mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorLight != null) {

            mSensorManager.registerListener(this,mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // called when new sensor data is available

        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];

        switch (sensorType) {

            case Sensor.TYPE_LIGHT:
                //handle light sensor
                mTextSensorLight.setText(getResources().getString(R.string.label_light, currentValue));
                break;

            case Sensor.TYPE_PROXIMITY:
                //handle proximity sensor
                mTextSensorProximity.setText(getResources().getString(R.string.label_proximity, currentValue));
                break;

            default:
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // called when the sensor accuracy changes
    }
}
