package de.famst.jens.remotesensors.controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import de.famst.jens.remotesensors.biz.DataModel;
import de.famst.jens.remotesensors.biz.Orientation;

/**
 * Created by jens on 30/08/14.
 */
public class SensorListener implements SensorEventListener
{
    private DataModel model = null;

    private SensorManager sensorManager = null;
    private Sensor accelerometer = null;
    private Sensor magneticField = null;

    private float[] acceleration;
    private float[] geomagnetic;

    final private float[] unitVector = {1.0f, 0.0f, 0.0f};

    public SensorListener(SensorManager sensorManager, DataModel model)
    {
        this.sensorManager = sensorManager;
        this.model = model;

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void resume()
    {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void suspend()
    {
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            acceleration = sensorEvent.values.clone();
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            geomagnetic = sensorEvent.values.clone();
        }

        if (acceleration != null && geomagnetic != null)
        {
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success;

            success = SensorManager.getRotationMatrix(R, I, acceleration, geomagnetic);

            if (success)
            {
                float orientationRad[] = new float[3];
                SensorManager.getOrientation(R, orientationRad);

                model.setOrientation(new Orientation((orientationRad)));
            }

            success = SensorManager.getRotationMatrix(R, I, acceleration, unitVector);

            if (success)
            {
                float orientationRad[] = new float[3];
                SensorManager.getOrientation(R, orientationRad);

                model.setOrientationGravity(new Orientation((orientationRad)));
            }

        }
    }

}
