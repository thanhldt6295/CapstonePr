package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodsBeveragesActivity extends AppCompatActivity {

    ExpandableListView expandableListView;

    GridView gridView;

    String itemList[] = {"Rice", "Meat", "Honnor"};

    int itemIcon[] = {R.drawable.h, R.drawable.h, R.drawable.h};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodsbeverages);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText("FOODS & BEVERAGES");

        expandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);

        gridView = (GridView) findViewById(R.id.gridView);

        List<String> Headings = new ArrayList<>();
        List<String> L1 = new ArrayList<>();
        List<String> L2 = new ArrayList<>();
        HashMap<String,List<String>> ChildList = new HashMap<String, List<String>>();
        String heading_items[] = getResources().getStringArray(R.array.header_titles);
        String l1[] = getResources().getStringArray(R.array.h1_items);
        String l2[] = getResources().getStringArray(R.array.h2_items);
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
        MenuAdapter menuAdapter = new MenuAdapter(this,Headings,ChildList);
        expandableListView.setAdapter(menuAdapter);

        GridAdapter adapter = new GridAdapter(this, itemIcon, itemList);
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

    /*class LoadContentAsync extends AsyncTask<Void, Void, MainContentModel> {

        @Override
        protected MainContentModel doInBackground(Void... voids) {
            //Gson gson = new Gson();
            MainContentModel mainContentModel = null;
            *//*try {
                InputStream is = getAssets().open("phones.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                synchronized (this) {
                    mainContentModel = gson.fromJson(reader, MainContentModel.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*//*
            return mainContentModel;
        }

        @Override
        protected void onPostExecute(MainContentModel mainContentModel) {
            super.onPostExecute(mainContentModel);
        }
    }*/
}
