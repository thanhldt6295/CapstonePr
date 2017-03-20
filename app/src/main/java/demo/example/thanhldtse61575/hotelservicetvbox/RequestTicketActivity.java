package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import java.util.TimeZone;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.ToServer;

public class RequestTicketActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private PopupWindow popup;
    private LayoutInflater popupInflater;
    TextView roomid;
    Button finalize;
    EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_ticket);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle = (TextView) findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.request_ticket));
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());

        final Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        String[] typeSpinnerArray = new String[] {getResources().getString(R.string.housekeeping), getResources().getString(R.string.maintenance), getResources().getString(R.string.front_desk), getResources().getString(R.string.laundry)};
        SpinnerAdapter typeAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, typeSpinnerArray);
        typeSpinner.setAdapter(typeAdapter);

        final Spinner timeSpinner = (Spinner) findViewById(R.id.timeSpinner);
        String[] timeSpinnerArray = new String[] {"15", "30", "45", "60", "100"};
        SpinnerAdapter timeAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, timeSpinnerArray);
        timeSpinner.setAdapter(timeAdapter);

        relativeLayout = (RelativeLayout) findViewById(R.id.activity_request_ticket);
        comment = (EditText) findViewById(R.id.txtComment);
        finalize = (Button) findViewById(R.id.btnFinalizeOrder);
        finalize.setFocusable(true);
        finalize.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    finalize.setTextColor(Color.parseColor("#E2FFE600"));
                }
                else {
                    finalize.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
            }
        });
        finalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!comment.getText().toString().equals("")) {
                    popupInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    ViewGroup container = (ViewGroup) popupInflater.inflate(R.layout.confirm_popup, null);

//                LinearLayout layoutPopup = (LinearLayout) container.findViewById(R.id.layoutPopup);
//                layoutPopup.getBackground().setAlpha(126);
//                LinearLayout layoutTitle = (LinearLayout) container.findViewById(R.id.layoutTitle);
//                layoutTitle.getBackground().setAlpha(200);
//                LinearLayout layoutContent = (LinearLayout) container.findViewById(R.id.layoutContent);
//                layoutContent.getBackground().setAlpha(238);
//                LinearLayout layoutBtn = (LinearLayout) container.findViewById(R.id.layoutBtn);
//                layoutBtn.getBackground().setAlpha(200);

                    popup = new PopupWindow(container, 600, 300, true);
                    popup.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

                    popup.setOutsideTouchable(true);
                    popup.getContentView().setFocusableInTouchMode(true);
                    popup.getContentView().setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {

                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                popup.dismiss();
                                return true;
                            }
                            return false;
                        }
                    });
                    container.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popup.dismiss();
                            return true;
                        }
                    });
                    TextView confirm = (TextView) container.findViewById(R.id.tvConfirm);
                    confirm.setText(container.getResources().getString(R.string.confirm_service));
                    TextView content = (TextView) container.findViewById(R.id.tvContent);
                    content.setText(container.getResources().getString(R.string.confirm_question_do));
                    Button cancel = (Button) container.findViewById(R.id.btnCancel);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popup.dismiss();
                        }
                    });
                    Button okyes = (Button) container.findViewById(R.id.btnOK);
                    okyes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final List<CartItem> list = new ArrayList<CartItem>();
                            list.add(new CartItem(0, typeSpinner.getSelectedItem().toString(), 0, 0, "", "", 1, comment.getText().toString()));
                            final String returnList = new Gson().toJson(list);
                            final long time=60*Long.parseLong(timeSpinner.getSelectedItem().toString());
                            final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
                            final long time2Serv = calendar.getTimeInMillis()/1000 + time;
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
                                        popup.dismiss();
                                        Toast toast = Toast.makeText(RequestTicketActivity.this, R.string.confirm_request_wait, Toast.LENGTH_SHORT);
                                        TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                        vToast.setTextColor(Color.WHITE);
                                        vToast.setTextSize(30);
                                        toast.show();
                                    }
                                    else{
                                        Toast.makeText(RequestTicketActivity.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            new SendDataToServer().execute("http://capstoneserver2017.azurewebsites.net/api/RequestsApi/SendRequest", time2Serv+"" , returnList+"", getRoomID());
                        }
                    });
                } else{
                    Toast toast = Toast.makeText(RequestTicketActivity.this, R.string.notifychoose, Toast.LENGTH_SHORT);
                    TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                    vToast.setTextColor(Color.RED);
                    vToast.setTextSize(30);
                    toast.show();
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
                new DatePickerDialog(RequestTicketActivity.this, date, myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
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
