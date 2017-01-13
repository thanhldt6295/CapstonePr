package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FoodsBeveragesActivity extends AppCompatActivity {

    // Declare
    Button btnOrder;
    ExpandableListView expandableListView;
    GridView gridView;

    String itemList[] = {"Rice", "Meat", "Honnor", "Rice", "Meat", "Honnor", "Rice", "Meat", "Honnor",
            "Rice", "Meat", "Honnor", "Rice", "Meat", "Honnor", "Rice", "Meat", "Honnor",};

    int itemIcon[] = {R.drawable.demo, R.drawable.demo, R.drawable.demo, R.drawable.demo, R.drawable.demo,
            R.drawable.demo, R.drawable.demo, R.drawable.demo, R.drawable.demo, R.drawable.demo,
            R.drawable.demo, R.drawable.demo, R.drawable.demo, R.drawable.demo, R.drawable.demo,
            R.drawable.demo, R.drawable.demo, R.drawable.demo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodsbeverages);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText("FOODS & BEVERAGES");

        // Datetime & Calendar
        TextView txtDate;
        txtDate = (TextView) findViewById(R.id.txtDate);
        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date()) + "  "
                                        + DateFormat.getDateInstance().format(new Date());
        txtDate.setText(currentDateTimeString);

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
                new DatePickerDialog(FoodsBeveragesActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Map
        btnOrder = (Button) findViewById(R.id.btnOrder);
        expandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        gridView = (GridView) findViewById(R.id.gridView);

        // Button effect
        btnOrder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        // Your action here on button click
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

        // List
        List<String> Headings = new ArrayList<>();
        List<String> L1 = new ArrayList<>();
        List<String> L2 = new ArrayList<>();
        HashMap<String,List<String>> ChildList = new HashMap<String, List<String>>();
        String heading_items[] = getResources().getStringArray(R.array.food_header_titles);
        String l1[] = getResources().getStringArray(R.array.food_h1_items);
        String l2[] = getResources().getStringArray(R.array.food_h2_items);
        for (String title : heading_items){
            Headings.add(title);
        }
        for(String title : l1){
            L1.add(title);
        }
        for(String title : l2){
            L2.add(title);
        }
        ChildList.put(Headings.get(0),L1);
        ChildList.put(Headings.get(1),L2);
        MenuListAdapter menuAdapter = new MenuListAdapter(this,Headings,ChildList);
        expandableListView.setAdapter(menuAdapter);

        ItemGridAdapter adapter = new ItemGridAdapter(this, itemIcon, itemList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FoodsBeveragesActivity.this,itemList[position],Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.cart:
                startActivity(new Intent(this, OrderActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
