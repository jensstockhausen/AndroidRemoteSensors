package de.famst.jens.remotesensors;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import de.famst.jens.remotesensors.biz.Orientation;
import de.famst.jens.remotesensors.biz.OrientationListener;
import de.famst.jens.remotesensors.biz.SensorListener;


public class MainActivity extends Activity implements OrientationListener
{
    private SensorListener sensorListener = null;

    TextView azValue = null;
    TextView elValue = null;
    TextView roValue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorListener = new SensorListener((SensorManager)getSystemService(SENSOR_SERVICE));
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        azValue = (TextView) findViewById(R.id.textViewAzValue);
        elValue = (TextView) findViewById(R.id.textViewElValue);
        roValue = (TextView) findViewById(R.id.textViewRoValue);

        sensorListener.registerOrientationListener(this);
        sensorListener.resume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorListener.suspend();
        sensorListener.unregisterOrientationListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOrientationChanged(Orientation newOrientation)
    {
        //Log.d("Orientation",newOrientation.toString());
        if ( (azValue!= null) && (elValue != null) && (roValue != null))
        {
            azValue.setText(String.format("%6.1f°", newOrientation.getAzimuthInDegrees()));
            elValue.setText(String.format("%6.1f°", newOrientation.getElevationInDegrees()));
            roValue.setText(String.format("%6.1f°", newOrientation.getRollInDegrees()));
        }

    }
}
