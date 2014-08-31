package de.famst.jens.remotesensors.controller;

import android.util.Log;
import android.view.View;

import de.famst.jens.remotesensors.R;
import de.famst.jens.remotesensors.biz.DataModel;
import de.famst.jens.remotesensors.biz.Orientation;

/**
 * Created by jens on 31/08/14.
 */
public class LevelListener implements View.OnClickListener
{
    DataModel model;

    public LevelListener(DataModel model)
    {
        this.model = model;
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.buttonLevel)
        {
            Orientation curOrient = model.getOrientation();
            Orientation graOrient = model.getOrientationGravity();

            model.setOffsetOrientation(
                    new Orientation(
                            curOrient.getAzimuthInDegrees(),
                            graOrient.getElevationInDegrees(),
                            graOrient.getRollInDegrees()));

            Log.d("Offset", "Setting Offset" + curOrient.toString());
        } else if (view.getId() == R.id.buttonReset)
        {
            model.setOffsetOrientation(new Orientation());

            Log.d("Offset", "Reset Offset");
        }
    }
}
