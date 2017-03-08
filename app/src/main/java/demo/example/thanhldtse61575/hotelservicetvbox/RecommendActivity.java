package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Hobby;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Recommend;

public class RecommendActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private List<Recommend> RecommendEntityList = new ArrayList<Recommend>();
    private List<Hobby> HobbyEntityList = new ArrayList<Hobby>();
    private ListView listView;
    private RecommendAdapter adapter;
    private HobbyAdapter HobbyAdapter;
    private Spinner HobbySpinner;
    TextView roomid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.recommend));
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());

        HobbySpinner = (Spinner)findViewById(R.id.hobbySpinner);
        listView = (ListView) findViewById(R.id.recListView);
        HobbyAdapter = new HobbyAdapter(this,android.R.layout.simple_spinner_dropdown_item,loadDummyCities());
        HobbyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        HobbySpinner.setAdapter(HobbyAdapter);
        HobbySpinner.setOnItemSelectedListener(this);
        loadDummyRecommend();
        adapter = new RecommendAdapter(this, RecommendEntityList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RecommendActivity.this,RecommendDetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
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
                new DatePickerDialog(RecommendActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
            //return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    private List<Hobby> loadDummyCities(){
        HobbyEntityList = new ArrayList<Hobby>();

        Hobby Hobby0 = new Hobby();
        Hobby0.setId(0);
        Hobby0.setName("ALL");
        HobbyEntityList.add(Hobby0);

        Hobby Hobby1 = new Hobby();
        Hobby1.setId(1);
        Hobby1.setName("OCEAN");
        HobbyEntityList.add(Hobby1);

        Hobby Hobby2 = new Hobby();
        Hobby2.setId(2);
        Hobby2.setName("MOUNTAIN");
        HobbyEntityList.add(Hobby2);

        Hobby Hobby3 = new Hobby();
        Hobby3.setId(3);
        Hobby3.setName("COUNTRYSIDE");
        HobbyEntityList.add(Hobby3);

        return HobbyEntityList;
    }

    private List<Recommend> loadDummyRecommend(){

        RecommendEntityList = new ArrayList<Recommend>();

        Recommend Recommend1 = new Recommend();
        Recommend1.setId(1);
        Recommend1.setHobbyID(0);
        Recommend1.setHobbyID(1);
        Recommend1.setImage(R.drawable.ocean1);
        Recommend1.setName("ABC");
        Recommend1.setDescription("Hello Ahihi");
        RecommendEntityList.add(Recommend1);

        Recommend Recommend2 = new Recommend();
        Recommend2.setId(2);
        Recommend2.setHobbyID(0);
        Recommend2.setHobbyID(2);
        Recommend2.setImage(R.drawable.mountain1);
        Recommend2.setName("BCD");
        Recommend2.setDescription("2nd Cross");
        RecommendEntityList.add(Recommend2);

        Recommend Recommend3 = new Recommend();
        Recommend3.setId(3);
        Recommend3.setHobbyID(0);
        Recommend3.setHobbyID(2);
        Recommend3.setImage(R.drawable.mountain2);
        Recommend3.setName("Carlton");
        Recommend3.setDescription("Church Street");
        RecommendEntityList.add(Recommend3);

        Recommend Recommend4 = new Recommend();
        Recommend4.setId(4);
        Recommend4.setHobbyID(0);
        Recommend4.setHobbyID(2);
        Recommend4.setImage(R.drawable.mountain3);
        Recommend4.setName("New");
        Recommend4.setDescription("Vatanappilly");
        RecommendEntityList.add(Recommend4);

        //ALL
        Recommend Recommend5 = new Recommend();
        Recommend5.setId(5);
        Recommend5.setHobbyID(0);
        Recommend5.setHobbyID(3);
        Recommend5.setImage(R.drawable.cntrside1);
        Recommend5.setName("ABCD");
        Recommend5.setDescription("Hehe Ahihi");
        RecommendEntityList.add(Recommend5);

        Recommend Recommend6 = new Recommend();
        Recommend6.setId(6);
        Recommend6.setHobbyID(0);
        Recommend6.setHobbyID(1);
        Recommend6.setImage(R.drawable.ocean2);
        Recommend6.setName("BCD");
        Recommend6.setDescription("thueydhs");
        RecommendEntityList.add(Recommend6);

        return RecommendEntityList;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Hobby Hobby = HobbyAdapter.getItem(position);

//Here we use the Filtering Feature which we implemented in our Adapter class.
        adapter.getFilter().filter(Long.toString(Hobby.getId()),new Filter.FilterListener() {
            @Override
            public void onFilterComplete(int count) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String getRoomID(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }
}
