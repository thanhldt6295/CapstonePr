package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.OrderDetail;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;
import demo.example.thanhldtse61575.hotelservicetvbox.housekeeping.ExtraActivity;
import demo.example.thanhldtse61575.hotelservicetvbox.housekeeping.ExtraAdapter;

public class MaintenanceActivity extends AppCompatActivity {

    TextView roomid;

    class GetDataFromServer extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            CommonService commonService = new CommonService();
            String returnva = commonService.getData(params[0]);
            return returnva;
        }

        protected void onPostExecute(String response) {
            //parse json sang list service
            final List<Service> acc = new Gson().fromJson(response, new TypeToken<List<Service>>() {
            }.getType());

            // Search follow categoryName
            final List<Service> accID = new ArrayList<Service>();
            for (Service ac : acc) {
                String cagName = ac.getCategoryName().toString().toUpperCase().trim();;
                if (cagName.equals(getResources().getString(R.string.maintenance).toString())) {
                    accID.add(new Service(ac.getServiceID(), ac.getServiceName(), ac.getCategoryID(), ac.getCategoryName(), ac.getUnitPrice(), ac.getDescription(), ac.getImage()));
                }
            }

            Button btnFinalize = (Button) findViewById(R.id.btnFinalizeOrder);
            TimePicker deliveryTime = (TimePicker) findViewById(R.id.timePicker);
            deliveryTime.setIs24HourView(true);
            DatePicker deliveryDate = (DatePicker) findViewById(R.id.datePicker);

            if(accID.size()!=0){
                ListView listView = (ListView) findViewById(R.id.maintenanceListView);
                MaintenanceAdapter a = new MaintenanceAdapter(MaintenanceActivity.this, listView, accID, btnFinalize, deliveryTime, deliveryDate);
                listView.setAdapter(a);
            } else {
                Toast.makeText(MaintenanceActivity.this, R.string.notitynull, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.maintenance));
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getDataFromSharedPreferences());

        new MaintenanceActivity.GetDataFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/ServicesApi/GetAllService");

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
                new DatePickerDialog(MaintenanceActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private String getDataFromSharedPreferences(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }
}
