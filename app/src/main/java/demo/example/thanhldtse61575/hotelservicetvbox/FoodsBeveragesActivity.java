package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;

public class FoodsBeveragesActivity extends AppCompatActivity {

    // Declare
    ExpandableListView expandableListView;
    GridView gridView;
    ImageView image;
    TextView name;
    TextView price;
    TextView description;
    Button btnOrder;
    Button btnMinus;
    Button btnPlus;
    EditText quantity;
    List<CartItem> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodsbeverages);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.food_drink));

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
                new DatePickerDialog(FoodsBeveragesActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Map
        expandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        gridView = (GridView) findViewById(R.id.gridView);
        image = (ImageView) findViewById(R.id.imageViewDetail);
        name = (TextView) findViewById(R.id.txtServiceName);
        price = (TextView) findViewById(R.id.txtUnitPrice);
        description = (TextView) findViewById(R.id.txtDescription);
        quantity = (EditText) findViewById(R.id.txtQuantity);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnOrder = (Button) findViewById(R.id.btnOrder);

        cart = new ArrayList<>();

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

        FoodsBeveragesAdapter menuAdapter = new FoodsBeveragesAdapter(this, Headings, ChildList, gridView,
                image, name, price, description, btnOrder, btnMinus, btnPlus, quantity, cart);
        expandableListView.setAdapter(menuAdapter);
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
                Intent intent = new Intent(this, OrderActivity.class);
                intent.putExtra("storeItem", (Serializable) cart);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        Gson gson = new Gson();

        SharedPreferences sp = getSharedPreferences("cart", Context.MODE_PRIVATE);
        String json = sp.getString("cartinfo", "");

        if (json.length() > 0) {
            cart.clear();
            JsonArray entries = (JsonArray) new JsonParser().parse(json);

            for (int i = 0; i < entries.size(); i++) {
                CartItem cartItem = gson.fromJson(entries.get(i).toString(), CartItem.class);
                cart.add(cartItem);
            }

        }

        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(cart.size()!=0) {
            final Gson gson = new Gson();
            SharedPreferences sp = getSharedPreferences("cart", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sp.edit();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.confirm_back);
            builder.setMessage(R.string.confirm_back_question);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    cart.clear();
                    String str = gson.toJson(cart);
                    editor.putString("cartinfo", str);
                    editor.commit();
                    FoodsBeveragesActivity.super.onBackPressed();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //Nothing
                }
            });
            builder.show();
        }
        if(cart.size()==0) {
            FoodsBeveragesActivity.super.onBackPressed();
        }
    }
}
