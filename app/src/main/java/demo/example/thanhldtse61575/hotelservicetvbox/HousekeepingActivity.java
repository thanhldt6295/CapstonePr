package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class HousekeepingActivity extends AppCompatActivity {

    int[] titles = {R.string.room_cleaning, R.string.laundry, R.string.bath_extras,
            R.string.bed_extras, R.string.room_extras};
    int[] images = {R.drawable.img_roomcleaning, R.drawable.img_laundry, R.drawable.img_bathextras,
            R.drawable.img_bedextras, R.drawable.img_roomextras};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housekeeping);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.housekeeping));

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_menu);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        HousekeepingAdapter ma = new HousekeepingAdapter(this, titles, images);
        rv.setAdapter(ma);

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
                new DatePickerDialog(HousekeepingActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

}
