package de.famst.jens.remotesensors;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import de.famst.jens.remotesensors.biz.DataModel;
import de.famst.jens.remotesensors.biz.OnChangeListener;
import de.famst.jens.remotesensors.biz.Orientation;
import de.famst.jens.remotesensors.controller.LevelListener;
import de.famst.jens.remotesensors.controller.SensorListener;
import de.famst.jens.remotesensors.http.HttpServer;
import de.famst.jens.remotesensors.http.IpInformation;

public class MainActivity extends Activity implements OnChangeListener
{
    private DataModel model = null;
    private SharedPreferences prefs;

    private SensorListener sensorListener = null;

    private HttpServer httpServer = null;
    private IpInformation ipInformation = null;

    private TextView azValue = null;
    private TextView elValue = null;
    private TextView roValue = null;
    private TextView elValueGr = null;
    private TextView roValueGr = null;


    private TextView ipValue = null;
    private TextView ipPortValue = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        model = new DataModel();
        model.addListener(this);

        Boolean doAverage = prefs.getBoolean("do_average", false);
        model.setDoAverage(doAverage);

        Log.d("Average", "" + doAverage);

        sensorListener = new SensorListener((SensorManager) getSystemService(SENSOR_SERVICE), model);
        ipInformation = new IpInformation((WifiManager) getSystemService(WIFI_SERVICE), model);

        Button buttonLevel = (Button) findViewById(R.id.buttonLevel);
        Button buttonReset = (Button) findViewById(R.id.buttonReset);
        LevelListener levelListener = new LevelListener(model);
        buttonLevel.setOnClickListener(levelListener);
        buttonReset.setOnClickListener(levelListener);

        try
        {
            int port = Integer.valueOf(prefs.getString("tcp_port", "8080"));
            httpServer = new HttpServer(port, model);

            Log.d("HTTP", "Port: " + httpServer.getListeningPort());
        }
        catch (IOException e)
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

        elValueGr = (TextView) findViewById(R.id.textViewElValueGr);
        roValueGr = (TextView) findViewById(R.id.textViewRoValueGr);

        sensorListener.resume();

        try
        {
            httpServer.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        ipValue = (TextView) findViewById(R.id.textViewIpValue);
        ipValue.setText(ipInformation.getCurrentIp());

        ipPortValue = (TextView) findViewById(R.id.textViewIpPort);
        ipPortValue.setText("" + httpServer.getListeningPort());
    }

    @Override
    protected void onPause()
    {
        httpServer.stop();
        sensorListener.suspend();

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onChange(Object model)
    {
        DataModel dataModel = (DataModel) model;
        updateOrientation(dataModel);
    }


    private void updateOrientation(DataModel model)
    {
        Orientation orientation = model.getOrientation();
        Orientation corrected = orientation.substractOffset(model.getOffsetOrientation());


        if ((azValue != null) && (elValue != null) && (roValue != null))
        {
            azValue.setText(String.format("%6.1f°", corrected.getAzimuthInDegrees()));
            elValue.setText(String.format("%6.1f°", corrected.getElevationInDegrees()));
            roValue.setText(String.format("%6.1f°", corrected.getRollInDegrees()));
        }

        Orientation grav = model.getOrientationGravity();
        grav = grav.substractOffset(model.getOffsetOrientation());

        if ((elValueGr != null) && (roValueGr != null))
        {
            elValueGr.setText(String.format("%6.1f°", grav.getElevationInDegrees()));
            roValueGr.setText(String.format("%6.1f°", grav.getRollInDegrees()));
        }
    }

}
