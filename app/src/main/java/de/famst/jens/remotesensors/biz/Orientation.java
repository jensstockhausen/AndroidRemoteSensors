package de.famst.jens.remotesensors.biz;

/**
 * Created by jens on 30/08/14.
 **/
public class Orientation
{
    private float azimuthInDegrees;
    private float elevationInDegrees;
    private float rollInDegrees;

    final float pi = (float) Math.PI;
    final float rad2deg = 180/pi;

    public Orientation()
    {
        this.azimuthInDegrees = 0;
        this.elevationInDegrees = 0;
        this.rollInDegrees = 0;
    }

    public Orientation(float azimuthInDegrees, float elevationInDegrees, float rollInDegrees)
    {
        this.azimuthInDegrees = azimuthInDegrees;
        this.elevationInDegrees = elevationInDegrees;
        this.rollInDegrees = rollInDegrees;
    }

    public Orientation(float[] orientationInRad)
    {
        this.azimuthInDegrees = orientationInRad[0] * rad2deg;
        this.elevationInDegrees = orientationInRad[1] * rad2deg;
        this.rollInDegrees = orientationInRad[2] * rad2deg;
    }

    @Override
    public String toString()
    {
        return "Orientation{" +
                "azimuthInDegrees=" + azimuthInDegrees +
                ", elevationInDegrees=" + elevationInDegrees +
                ", rollInDegrees=" + rollInDegrees +
                '}';
    }

    public float getAzimuthInDegrees()
    {
        return azimuthInDegrees;
    }

    public void setAzimuthInDegrees(float azimuthInDegrees)
    {
        this.azimuthInDegrees = azimuthInDegrees;
    }

    public float getElevationInDegrees()
    {
        return elevationInDegrees;
    }

    public void setElevationInDegrees(float elevationInDegrees)
    {
        this.elevationInDegrees = elevationInDegrees;
    }

    public float getRollInDegrees()
    {
        return rollInDegrees;
    }

    public void setRollInDegrees(float rollInDegrees)
    {
        this.rollInDegrees = rollInDegrees;
    }
}
