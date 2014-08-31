package de.famst.jens.remotesensors.biz;

/**
 * Created by jens on 31/08/14.
 */
public class DataModel extends SimpleObservable<DataModel>
{
    private Orientation orientation;

    public DataModel()
    {
        orientation = new Orientation();
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
}
