package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Order;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.OrderDetail;

public class ViewBillActivity extends AppCompatActivity {

    private static double roomprice = 0;
    List<OrderDetail> details = new ArrayList<>();
    List<OrderDetail> done = new ArrayList<>();
    List<OrderDetail> temp = new ArrayList<>();
    Order bill;
    TextView roomid;

    class GetOrderFromServer extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            CommonService commonService = new CommonService();
            String returnva = commonService.getData(params[0]);
            return returnva;
        }

        protected void onPostExecute(String response) {
            //parse json sang list service
            final Order acc = new Gson().fromJson(response, new TypeToken<Order>() {
            }.getType());

            roomprice = acc.getSubTotal();
        }
    }

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
            bill = getOrder();
            final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
            final long usedTime = calendar.getTimeInMillis()/1000 - bill.getStartTime();
            final float ritsu = usedTime/(24*60*60);
            int dayNum = Math.round(ritsu);
            if(dayNum==0) dayNum = 1;
            done.add(new OrderDetail(0,bill.getOrderID(),0,getResources().getString(R.string.room_order),
                    bill.getRoomID(),bill.getRTypName().toUpperCase(),roomprice,"", "",dayNum,"",
                    bill.getStartTime(),bill.getEndTime(),"",""));
            for (OrderDetail od: details) {
                String stt = od.getStatus().toString().toUpperCase().trim();
                if (stt.equals("DONE") | stt.equals("ONGOING")) {
                    done.add(new OrderDetail(od.getOrderDetailID(), od.getOrderID(), od.getServiceID(),
                            od.getServiceName(), od.getCategoryID(), od.getCategoryName(),od.getPrice(),
                            od.getDescription(), od.getImage(), od.getQuantity(), od.getNote(), od.getOrderTime(),
                            od.getDeliverTime(),od.getStaffID(),od.getStatus()));
                }
            }

            for(int i = 0; i < done.size()-1; i++){
                for (int j = i+1; j < done.size(); j++) {
                    if ((done.get(i).getServiceName().equals(done.get(j).getServiceName())) && (!done.get(i).getServiceName().equals(""))) {
                        done.get(i).setQuantity(done.get(i).getQuantity() + done.get(j).getQuantity());
                        done.get(i).setPrice(done.get(i).getPrice() + done.get(j).getPrice());
                        done.get(j).setServiceName("");
                    }
                }
            }

            for(OrderDetail od: done){
                if(!od.getServiceName().equals(""))
                    temp.add(new OrderDetail(od.getOrderDetailID(), od.getOrderID(), od.getServiceID(),
                        od.getServiceName(), od.getCategoryID(), od.getCategoryName(),od.getPrice(),
                        od.getDescription(), od.getImage(), od.getQuantity(), od.getNote(), od.getOrderTime(),
                        od.getDeliverTime(),od.getStaffID(),od.getStatus()));
            }

            if(temp.size()!=0) {
                ListView listView = (ListView) findViewById(R.id.detailsListView);
                ViewBillAdapter a = new ViewBillAdapter(ViewBillActivity.this, temp, total);
                listView.setAdapter(a);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_view_bill);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.bill));
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        new ViewBillActivity.GetOrderFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/OrdersApi/GetOrderInfo/" + getRoomID());
        new ViewBillActivity.GetDataFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/OrderDetailsApi/GetOrderDetailByRoomID/" + getRoomID());

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
                new DatePickerDialog(ViewBillActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private String getRoomID(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }

    private Order getOrder(){

        Gson gson = new Gson();
        Order bill;
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedBill", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("BillTotal", "");

        Type type = new TypeToken<Order>() {}.getType();
        bill = gson.fromJson(jsonPreferences, type);

        return bill;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pending, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.pending:
                Intent i = new Intent(ViewBillActivity.this, PendingActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
