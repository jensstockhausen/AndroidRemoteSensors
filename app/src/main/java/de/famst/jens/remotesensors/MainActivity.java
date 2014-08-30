package de.famst.jens.remotesensors;

import android.app.Activity;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;

import de.famst.jens.remotesensors.biz.Orientation;
import de.famst.jens.remotesensors.biz.OrientationListener;
import de.famst.jens.remotesensors.biz.SensorListener;
import de.famst.jens.remotesensors.http.HttpServer;
import de.famst.jens.remotesensors.http.IpInformation;

public class MainActivity extends Activity implements OrientationListener
{
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

        sensorListener = new SensorListener((SensorManager)getSystemService(SENSOR_SERVICE));
        ipInformation = new IpInformation((WifiManager) getSystemService(WIFI_SERVICE));

        try
        {
            httpServer = new HttpServer(8080);
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

        sensorListener.registerOrientationListener(this);
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
        sensorListener.unregisterOrientationListener(this);

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
    public void onOrientationChanged(Orientation newOrientation)
    {
        if ( (azValue!= null) && (elValue != null) && (roValue != null))
        {
            azValue.setText(String.format("%6.1f°", newOrientation.getAzimuthInDegrees()));
            elValue.setText(String.format("%6.1f°", newOrientation.getElevationInDegrees()));
            roValue.setText(String.format("%6.1f°", newOrientation.getRollInDegrees()));

            httpServer.setOrientation(newOrientation);
        }
    }

}
