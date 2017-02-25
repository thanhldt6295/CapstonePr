package demo.example.thanhldtse61575.hotelservicetvbox;

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

import demo.example.thanhldtse61575.hotelservicetvbox.entity.OrderDetail;

public class PendingActivity extends AppCompatActivity {

    List<OrderDetail> details = new ArrayList<>();
    List<OrderDetail> pending = new ArrayList<>();

    class GetDataFromServer extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            CommonService commonService = new CommonService();
            String returnva = commonService.getData(params[0]);
            return returnva;
        }

        protected void onPostExecute(String response) {
            //parse json sang list
            details = new Gson().fromJson(response, new TypeToken<List<OrderDetail>>() {
            }.getType());

            TextView total = (TextView) findViewById(R.id.txtCartTotal);

            for (OrderDetail od: details) {
                String stt = od.getStatus().toString().toUpperCase().trim();
                if (stt.equals("DONE")) {
                    pending.add(new OrderDetail(od.getOrderDetailID(),od.getOrderID(),
                            od.getServiceID(),od.getServiceName(),od.getCategoryID(),
                            od.getCategoryName(),od.getUnitPrice(),od.getDescription(),od.getImage(),
                            od.getQuantity(),od.getNote(),od.getOrderTime(),od.getDeliverTime(),od.getStaffID(),od.getStatus()));
                }
            }
            ListView listView = (ListView) findViewById(R.id.detailsListView);
            PendingAdapter a = new PendingAdapter(PendingActivity.this, listView, pending, total);
            listView.setAdapter(a);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.pending));

        new GetDataFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/OrderDetailsApi/GetOrderDetailByOrderID/2");

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
                new DatePickerDialog(PendingActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}
