package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

public class ExtraActivity extends AppCompatActivity {

    TextView roomid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle = (TextView) findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.room_extras));
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());

        final List<Service> acc = getServiceList();
        // Search follow categoryName
        final List<Service> accID = new ArrayList<Service>();
        for (Service ac : acc) {
            String cagName = ac.getCategoryName().toString().toUpperCase().trim().replaceAll("\\s+$", "");
            if (cagName.equals("ROOM EXTRAS")) {
                accID.add(new Service(ac.getServiceID(), ac.getServiceName(), ac.getCategoryID(), ac.getCategoryName(), ac.getUnitPrice(), ac.getDescription(), ac.getImage()));
            }
        }

        final Button btnFinalize = (Button) findViewById(R.id.btnFinalizeOrder);
        btnFinalize.setFocusable(true);
        btnFinalize.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    btnFinalize.setTextColor(Color.parseColor("#E2FFE600"));
                }
                else {
                    btnFinalize.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
            }
        });
        Spinner s = (Spinner) findViewById(R.id.timeSpinner);

        if (accID.size() != 0) {
            ListView listView = (ListView) findViewById(R.id.extraListView);
            ExtraAdapter a = new ExtraAdapter(ExtraActivity.this, listView, accID, s, btnFinalize);
            listView.setAdapter(a);
        } else {
            Toast.makeText(ExtraActivity.this, R.string.notitynull, Toast.LENGTH_SHORT).show();
        }

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
                new DatePickerDialog(ExtraActivity.this, date, myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private String getRoomID() {

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }

    private List<Service> getServiceList(){
        Gson gson = new Gson();
        List<Service> list = new ArrayList<>();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedService", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("ServiceList", "");

        Type type = new TypeToken<List<Service>>() {}.getType();
        list = gson.fromJson(jsonPreferences, type);

        return list;
    }
}
