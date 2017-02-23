package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppsActivity extends AppCompatActivity {

    GridView gridView;
    String items[] = {"Youtube", "Facebook", "Instagram", "Mp3 Zing", "Google Maps", "SETTINGS", "Facebook", "Instagram", "Mp3 Zing", "Google Maps", "Google Maps"};
    int icons[] = {R.drawable.utube, R.drawable.facebook, R.drawable.instagram, R.drawable.mp3, R.drawable.maps, R.drawable.utube, R.drawable.facebook, R.drawable.instagram, R.drawable.mp3, R.drawable.maps, R.drawable.maps};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.application));

//        String networkSSID = "test";
//        String networkPass = "pass";
//
//        WifiConfiguration conf = new WifiConfiguration();
//        conf.SSID = "\"" + networkSSID + "\"";   // Please note the quotes. String should contain ssid in quotes
//
//        conf.wepKeys[0] = "\"" + networkPass + "\"";
//        conf.wepTxKeyIndex = 0;
//        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
//
//        conf.preSharedKey = "\""+ networkPass +"\"";
//
//        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//
//        WifiManager wifiManager = (WifiManager)this.getSystemService(this.WIFI_SERVICE);
//        wifiManager.addNetwork(conf);
//
//        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
//        for( WifiConfiguration i : list ) {
//            if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
//                wifiManager.disconnect();
//                wifiManager.enableNetwork(i.networkId, true);
//                wifiManager.reconnect();
//
//                break;
//            }
//        }

        gridView = (GridView) findViewById(R.id.gridViewApps);
        AppsAdapter adapter = new AppsAdapter(this, icons, items);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    OpenApps("com.google.android.youtube");
                }
                if(position==1){
                    OpenApps("com.facebook.katana");
                }
                if(position==2){
                    OpenApps("com.instagram.android");
                }
                if(position==3){
                    OpenApps("com.zing.mp3");
                }
                if(position==4){
                    OpenApps("com.google.android.apps.maps");
                }
                if(position==5){
                    startActivityForResult(new Intent(Settings.ACTION_SETTINGS),0);
                }
            }
        });

//
//        iBtnCHplay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                } catch (android.content.ActivityNotFoundException anfe) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                }
//            }
//        });
//

//        final String appPackageName = "com.google.android.youtube"; // getPackageName() from Context or Activity object
//        try {
//            //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//            PackageManager manager = getPackageManager();
//            Intent i = manager.getLaunchIntentForPackage(appPackageName);
//            i.addCategory(Intent.CATEGORY_LAUNCHER);
//            startActivity(i);
//        } catch (android.content.ActivityNotFoundException anfe) {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//        }

        // Datetime & Calendar
        final TextView txtDate;
        txtDate = (TextView) findViewById(R.id.txtDate);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String currentDateTimeString = DateFormat.getTimeInstance().format(new Date()) + "  "
                                        + DateFormat.getDateInstance().format(new Date());
                                txtDate.setText(currentDateTimeString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

        final Calendar myCalen = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalen.set(Calendar.YEAR, year);
                myCalen.set(Calendar.MONTH, monthOfYear);
                myCalen.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AppsActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void OpenApps (String appID){
        final PackageManager manager = getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(appID);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(i);
    }
}
