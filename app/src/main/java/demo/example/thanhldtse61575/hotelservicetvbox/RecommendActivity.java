package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Hobby;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Price;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Recommend;

public class RecommendActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int posh = 0;
    private int posp = 0;
    //private Button btnSearch;
    private List<Recommend> RecommendEntityList = new ArrayList<Recommend>();
    private List<Hobby> HobbyEntityList = new ArrayList<Hobby>();
    private List<Hobby> HobbyList = new ArrayList<Hobby>();
    private List<Price> PriceEntityList = new ArrayList<Price>();
    private List<Price> PriceList = new ArrayList<Price>();
    private ListView listView;
    private RecommendAdapter adapter;
    private HobbyAdapter HobbyAdapter;
    private PriceAdapter PriceAdapter;
    private Spinner HobbySpinner;
    private Spinner PriceSpinner;
    TextView roomid;

    int[] titles = {R.string.promotional, R.string.service, R.string.ecard, R.string.bill, R.string.survey, R.string.recommend, R.string.application};
    int[] images = {R.drawable.img_promotional, R.drawable.img_roomservices, R.drawable.img_ecard,
            R.drawable.img_billing, R.drawable.img_survey, R.drawable.img_recommend, R.drawable.img_app};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

//        RecyclerView rv = (RecyclerView) findViewById(R.id.friends_to_invite);
//        rv.setHasFixedSize(true);
//        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        RecommendListAdapter ma = new RecommendListAdapter(this, titles, images);
//        rv.setAdapter(ma);
//
//        RecyclerView rvs = (RecyclerView) findViewById(R.id.in_app_friends);
//        rvs.setHasFixedSize(true);
//        rvs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        MainAdapter mas = new MainAdapter(this, titles, images);
//        rvs.setAdapter(mas);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
//        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
//        abTitle.setText(getResources().getString(R.string.recommend));
//        roomid = (TextView) findViewById(R.id.roomid);
//        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());
//
//        //btnSearch = (Button) findViewById(R.id.btnSearch);
//
//        HobbySpinner = (Spinner)findViewById(R.id.hobbySpinner);
//        HobbySpinner.getBackground().setAlpha(80);
//        PriceSpinner = (Spinner) findViewById(R.id.priceSpinner);
//        PriceSpinner.getBackground().setAlpha(80);
//        listView = (ListView) findViewById(R.id.recListView);
//        listView.getBackground().setAlpha(48);
//        HobbyAdapter = new HobbyAdapter(this,android.R.layout.simple_spinner_dropdown_item, loadDummyHobby());
//        HobbyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        HobbySpinner.setAdapter(HobbyAdapter);
//        HobbySpinner.setOnItemSelectedListener(this);
//
//        PriceAdapter = new PriceAdapter(this,android.R.layout.simple_spinner_dropdown_item, loadDummyPrice());
//        PriceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        PriceSpinner.setAdapter(PriceAdapter);
//        PriceSpinner.setOnItemSelectedListener(this);
//
//        adapter = new RecommendAdapter(this, getRecommendList());
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(RecommendActivity.this,RecommendDetailActivity.class);
//                intent.putExtra("position", position);
//                startActivity(intent);
//            }
//        });
//
//        // Datetime & Calendar
//        final TextView txtDate;
//        txtDate = (TextView) findViewById(R.id.txtDate);
//
//        Thread t = new Thread() {
//
//            @Override
//            public void run() {
//                try {
//                    while (!isInterrupted()) {
//                        Thread.sleep(1000);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                String currentDateTimeString = DateFormat.getTimeInstance().format(new Date()) + "  "
//                                        + DateFormat.getDateInstance().format(new Date());
//                                txtDate.setText(currentDateTimeString);
//                            }
//                        });
//                    }
//                } catch (InterruptedException e) {
//                }
//            }
//        };
//
//        t.start();
//
//        final Calendar myCalen = Calendar.getInstance();
//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                myCalen.set(Calendar.YEAR, year);
//                myCalen.set(Calendar.MONTH, monthOfYear);
//                myCalen.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//            }
//        };
//
//        txtDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(RecommendActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
//                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });

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

    private List<Hobby> loadDummyHobby(){
        HobbyEntityList = getHobbyList();

        Hobby Hobby0 = new Hobby();
        Hobby0.setId(0);
        Hobby0.setName(getResources().getString(R.string.hobby));
        HobbyList.add(Hobby0);

        for(Hobby h : HobbyEntityList){
            HobbyList.add(h);
        }

        return HobbyList;
    }

    private List<Price> loadDummyPrice(){
        PriceEntityList = getPriceList();

        Price price = new Price();
        price.setId(0);
        price.setPrice(getResources().getString(R.string.money));
        PriceList.add(price);

        for(Price p : PriceEntityList){
            PriceList.add(p);
        }
        return PriceList;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final String search;
        Spinner spinner = (Spinner) parent;
//        if(spinner.getId() == R.id.hobbySpinner)
//        {
//            posh = position;
//        }
//        else if(spinner.getId() == R.id.priceSpinner)
//        {
//            posp = position;
//        }
        Hobby Hobby = HobbyAdapter.getItem(posh);
        Price Price = PriceAdapter.getItem(posp);
        search = Hobby.getId()+"," + Price.getId();
//Here we use the Filtering Feature which we implemented in our Adapter class.
        adapter.getFilter().filter(search,new Filter.FilterListener() {
            @Override
            public void onFilterComplete(int count) {

            }
        });
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                adapter.getFilter().filter(search,new Filter.FilterListener() {
//                    @Override
//                    public void onFilterComplete(int count) {
//
//                    }
//                });
//            }
//        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String getRoomID(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }

    private List<Hobby> getHobbyList(){
        Gson gson = new Gson();
        List<Hobby> list = new ArrayList<>();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedHobby", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("HobbyList", "");

        Type type = new TypeToken<List<Hobby>>() {}.getType();
        list = gson.fromJson(jsonPreferences, type);

        return list;
    }

    private List<Price> getPriceList(){
        Gson gson = new Gson();
        List<Price> list = new ArrayList<>();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedPrice", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("PriceList", "");

        Type type = new TypeToken<List<Price>>() {}.getType();
        list = gson.fromJson(jsonPreferences, type);

        return list;
    }

    private List<Recommend> getRecommendList(){
        Gson gson = new Gson();
        List<Recommend> list = new ArrayList<>();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedRecommend", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RecommendList", "");

        Type type = new TypeToken<List<Recommend>>() {}.getType();
        list = gson.fromJson(jsonPreferences, type);

        return list;
    }
}
