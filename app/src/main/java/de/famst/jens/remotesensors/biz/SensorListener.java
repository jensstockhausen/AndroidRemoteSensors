package de.famst.jens.remotesensors.biz;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by jens on 30/08/14.
 */
public class SensorListener implements SensorEventListener
{
    private SensorManager sensorManager = null;
    private Sensor accelerometer = null;
    private Sensor magneticField = null;

    private float[] acceleration;
    private float[] geomagnetic;

    private OrientationListener orientationListener = null;

    public SensorListener(SensorManager sensorManager)
    {
        this.sensorManager = sensorManager;

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

    public void registerOrientationListener(OrientationListener orientationListener)
    {
        this.orientationListener = orientationListener;
    }

    public void unregisterOrientationListener(OrientationListener orientationListener)
    {
        if (this.orientationListener == orientationListener)
        {
            this.orientationListener = null;
        }
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

            boolean success = SensorManager.getRotationMatrix(R, I, acceleration, geomagnetic);

            if (success)
            {
                float orientationRad[] = new float[3];
                SensorManager.getOrientation(R, orientationRad);

                Orientation orientationDeg = new Orientation(orientationRad);

                if (orientationListener != null)
                {
                    orientationListener.onOrientationChanged(orientationDeg);
                }
            }
        }
    }

}
