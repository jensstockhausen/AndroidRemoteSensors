package de.famst.jens.remotesensors.biz;

/**
 * Created by jens on 31/08/14.
 */
public class DataModel extends SimpleObservable<DataModel>
{
    private Orientation orientation;
    private Orientation orientationGravity;
    private Orientation offsetOrientation;

    private Boolean doAverage;

    public DataModel()
    {
        orientation = new Orientation();
        orientationGravity = new Orientation();
        offsetOrientation = new Orientation();

        doAverage = false;
    }

    public Orientation getOrientation()
    {
        return orientation;
    }

    public void setOrientation(Orientation orientation)
    {
        this.orientation = orientation;
        notifyObservers(this);
    }

    public Orientation getOffsetOrientation()
    {
        return offsetOrientation;
    }

    public void setOffsetOrientation(Orientation offsetOrientation)
    {
        this.offsetOrientation = offsetOrientation;
        notifyObservers(this);
    }

    public Orientation getOrientationGravity()
    {
        return orientationGravity;
    }

    public void setOrientationGravity(Orientation orientationGravity)
    {
        this.orientationGravity = orientationGravity;
        notifyObservers(this);
    }

    public Boolean getDoAverage()
    {
        return doAverage;
    }

    public void setDoAverage(Boolean doAverage)
    {
        this.doAverage = doAverage;
        notifyObservers(this);
    }
}
