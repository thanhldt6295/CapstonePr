package demo.example.thanhldtse61575.hotelservicetvbox.housekeeping;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.AppsActivity;
import demo.example.thanhldtse61575.hotelservicetvbox.CommonService;
import demo.example.thanhldtse61575.hotelservicetvbox.OrderAdapter;
import demo.example.thanhldtse61575.hotelservicetvbox.R;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

public class ExtraActivity extends AppCompatActivity {

    final String[] categoryName = {null};
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
                if (cagName.equals(categoryName[0])) {
                    accID.add(new Service(ac.getServiceID(), ac.getServiceName(), ac.getCategoryID(), ac.getCategoryName(), ac.getUnitPrice(), ac.getDescription(), ac.getImage()));
                }
            }

            if(accID!=null){
                ListView listView = (ListView) findViewById(R.id.extraListView);
                ExtraAdapter a = new ExtraAdapter(ExtraActivity.this, listView, accID);
                listView.setAdapter(a);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));

        Bundle extra = getIntent().getExtras();
        int type = extra.getInt("type");
        switch (type){
            case 1:
                categoryName[0] = getResources().getString(R.string.bath_extras).toString().toUpperCase().trim();
                abTitle.setText(getResources().getString(R.string.bath_extras));
                new GetDataFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/ServicesApi/GetAllService");
                break;
            case 2:
                categoryName[0] = getResources().getString(R.string.bed_extras).toString().toUpperCase().trim();
                abTitle.setText(getResources().getString(R.string.bed_extras));
                new GetDataFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/ServicesApi/GetAllService");
                break;
            case 3:
                categoryName[0] = getResources().getString(R.string.room_extras).toString().toUpperCase().trim();
                abTitle.setText(getResources().getString(R.string.room_extras));
                new GetDataFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/ServicesApi/GetAllService");
                break;
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
                new DatePickerDialog(ExtraActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}
