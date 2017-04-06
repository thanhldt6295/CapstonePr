package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Foody;

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

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_request_ticket);
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

        List<Foody> list = new ArrayList<>();
        list.add(new Foody(0, getResources().getString(R.string.housekeeping),0,"",0,"",""));
        list.add(new Foody(0, getResources().getString(R.string.laundry),0,"",0,"",""));
        list.add(new Foody(0, getResources().getString(R.string.maintenance),0,"",0,"",""));
        list.add(new Foody(0, getResources().getString(R.string.front_desk),0,"",0,"",""));

        ListView listView = (ListView) findViewById(R.id.maintenanceListView);
        RequestAdapter a = new RequestAdapter(RequestTicketActivity.this, listView, list, s, btnFinalize, relativeLayout);
        listView.setAdapter(a);

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
}
