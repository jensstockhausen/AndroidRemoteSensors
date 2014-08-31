package de.famst.jens.remotesensors;

import android.app.Activity;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;

import de.famst.jens.remotesensors.biz.DataModel;
import de.famst.jens.remotesensors.biz.OnChangeListener;
import de.famst.jens.remotesensors.biz.Orientation;
import de.famst.jens.remotesensors.controller.SensorListener;
import de.famst.jens.remotesensors.http.HttpServer;
import de.famst.jens.remotesensors.http.IpInformation;

public class MainActivity extends Activity implements OnChangeListener
{
    private DataModel model = null;

    private SensorListener sensorListener = null;

    private HttpServer httpServer = null;
    private IpInformation ipInformation = null;

    private TextView azValue = null;
    private TextView elValue = null;
    private TextView roValue = null;

    private TextView ipValue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new DataModel();
        model.addListener(this);

        sensorListener = new SensorListener((SensorManager) getSystemService(SENSOR_SERVICE), model);
        ipInformation = new IpInformation((WifiManager) getSystemService(WIFI_SERVICE), model);

        try
        {
            httpServer = new HttpServer(8080, model);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        azValue = (TextView) findViewById(R.id.textViewAzValue);
        elValue = (TextView) findViewById(R.id.textViewElValue);
        roValue = (TextView) findViewById(R.id.textViewRoValue);

        sensorListener.resume();

        try
        {
            httpServer.start();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        ipValue = (TextView) findViewById(R.id.textViewIpValue);
        ipValue.setText(ipInformation.getCurrentIp());
    }

    @Override
    protected void onPause()
    {
        httpServer.stop();
        sensorListener.suspend();

        super.onPause();
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

        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onChange(Object model)
    {
        DataModel dataModel = (DataModel) model;
        Orientation orientation = dataModel.getOrientation();

        updateOrientation(orientation);
    }


    private void updateOrientation(Orientation orientation)
    {
        if ( (azValue!= null) && (elValue != null) && (roValue != null))
        {
            azValue.setText(String.format("%6.1f°", orientation.getAzimuthInDegrees()));
            elValue.setText(String.format("%6.1f°", orientation.getElevationInDegrees()));
            roValue.setText(String.format("%6.1f°", orientation.getRollInDegrees()));
        }
    }

}
