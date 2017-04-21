package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.App;

public class AppsActivity extends AppCompatActivity {

    GridView gridView;
    TextView roomid;
    List<App> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_apps);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.application));
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        list.add(new App("Youtube","","com.google.android.youtube",R.drawable.icon_utube));
        list.add(new App("Facebook","","com.facebook.katana",R.drawable.icon_fb));
        list.add(new App("Calculator","","com.android2.calculator3",R.drawable.icon_calculator));
        list.add(new App("Mp3 Zing","","com.zing.mp3",R.drawable.icon_mp3));
        list.add(new App("Google Maps","","com.google.android.apps.maps",R.drawable.icon_ggmaps));
        list.add(new App("Settings","","",R.drawable.icon_setting));
        list.add(new App("Gmail","","com.google.android.gm",R.drawable.icon_gmail));
        list.add(new App("TV","","com.media.its.mytvnet",R.drawable.icon_tivi));
        list.add(new App("Fat Bat","","com.ifunsoft.fatbat",R.drawable.fat));
        list.add(new App("Caro Online","","com.joybox.carozone",R.drawable.caro));
        list.add(new App("Co Tuong","","com.joybox.cotuong",R.drawable.cotuong));
        list.add(new App("Candy Arange","","com.joybox.candyarrange",R.drawable.candy));
        list.add(new App("Angry Birds","","com.rovio.angrybirds",R.drawable.angry));
        list.add(new App("Plants & Zombies ","","com.ea.game.pvzfree_row",R.drawable.plants));
        list.add(new App("Fruit Ninja","","com.halfbrick.fruitninjafree",R.drawable.fruit));
        list.add(new App("Asphalt 8","","com.gameloft.android.ANMP.GloftA8HM",R.drawable.asphalt));

        gridView = (GridView) findViewById(R.id.gridViewApps);
        AppsAdapter adapter = new AppsAdapter(this, list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(list.get(position).getName().equals("Settings")){
                    startActivityForResult(new Intent(Settings.ACTION_SETTINGS),0);
                } else{
                    OpenApps(list.get(position).getAppid());
                }
            }
        });

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

    private String getRoomID(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }
}
