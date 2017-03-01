package demo.example.thanhldtse61575.hotelservicetvbox.housekeeping;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.TimeZone;

import demo.example.thanhldtse61575.hotelservicetvbox.CommonService;
import demo.example.thanhldtse61575.hotelservicetvbox.R;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.ToServer;

public class LaundryActivity extends AppCompatActivity {

    TextView roomid;
    Button btnFinalize;
    TimePicker deliveryTime;
    DatePicker deliveryDate;
    EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_request);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.laundry));
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getDataFromSharedPreferences());

        btnFinalize = (Button) findViewById(R.id.btnFinalizeOrder);
        deliveryTime = (TimePicker) findViewById(R.id.timePicker);
        deliveryTime.setIs24HourView(true);
        deliveryDate = (DatePicker) findViewById(R.id.datePicker);
        comment = (EditText) findViewById(R.id.txtComment);

        final List<CartItem> list = new ArrayList<>();

        btnFinalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmt = comment.getText().toString();
                list.add(new CartItem(23,"Launry",46,0,"","",1,cmt));
                    new AlertDialog.Builder(LaundryActivity.this)
                            .setTitle(R.string.confirm_order)
                            .setMessage(R.string.confirm_question_do)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
                                deliveryTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                                    @Override
                                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                                            calendar.set(deliveryDate.getYear(), deliveryDate.getMonth(), deliveryDate.getDayOfMonth(),
//                                                    deliveryTime.getHour(), deliveryTime.getMinute(), 0);
                                    }
                                });
                                final long time2Serv = calendar.getTimeInMillis()/1000;
                                final String returnList = new Gson().toJson(list);
                                class SendDataToServer extends AsyncTask<String, String, String> {

                                    @Override
                                    protected String doInBackground(String... params) {
                                        CommonService commonService = new CommonService();

                                        List<CartItem> acc = new Gson().fromJson(params[2], new TypeToken<List<CartItem>>() {}.getType());
                                        ToServer toServer = new ToServer( Double.parseDouble(params[1]), acc , Integer.parseInt(params[3]));

                                        return commonService.sendData(params[0], toServer)+"";
                                    }

                                    protected void onPostExecute(String response) {
                                        if(response.equals("200")){
                                            list.clear();
                                            comment.setText("");
                                            Toast.makeText(LaundryActivity.this, R.string.confirm_answer_accepted, Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(LaundryActivity.this, response, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                new SendDataToServer().execute("http://capstoneserver2017.azurewebsites.net/api/RequestsApi/SendRequest", time2Serv+"" , returnList, getDataFromSharedPreferences());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
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
                new DatePickerDialog(LaundryActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
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
