package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Recommend;

public class RecommendDetailActivity extends AppCompatActivity {

    TextView roomid;

    private List<Recommend> getDetails(){
        Gson gson = new Gson();
        List<Recommend> productFromShared = new ArrayList<>();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("MyProduct", "");

        Type type = new TypeToken<List<Recommend>>() {}.getType();
        productFromShared = gson.fromJson(jsonPreferences, type);

        return productFromShared;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_detail);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.recommend));
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());

        List<Recommend> RecommendEntityList = getDetails();
        int index = getIntent().getExtras().getInt("position");
        Recommend rec = RecommendEntityList.get(index);

        ImageView productImageView = (ImageView) findViewById(R.id.imageView);
        productImageView.setImageResource(rec.getImage());
        TextView productTitleTextView = (TextView) findViewById(R.id.recNameTV);
        productTitleTextView.setText(rec.getAddress());
        TextView productDetailsTextView = (TextView) findViewById(R.id.recDescriptTV);
        productDetailsTextView.setText(rec.getDescription());

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
                new DatePickerDialog(RecommendDetailActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private String getRoomID(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }
}
