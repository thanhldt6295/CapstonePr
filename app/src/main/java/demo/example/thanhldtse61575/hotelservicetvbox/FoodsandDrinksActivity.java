package demo.example.thanhldtse61575.hotelservicetvbox;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodsandDrinksActivity extends AppCompatActivity {

    ExpandableListView expandableListView;

    GridView gridView;

    String itemList[] = {"Rice", "Meat", "Honnor"};

    int itemIcon[] = {R.drawable.h,R.drawable.h,R.drawable.h};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodsand_drinks);

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
        MenuAdapter menuAdapter = new MenuAdapter(this,Headings,ChildList);
        expandableListView.setAdapter(menuAdapter);

        GridAdapter adapter = new GridAdapter(this, itemIcon, itemList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FoodsandDrinksActivity.this,itemList[position],Toast.LENGTH_SHORT).show();
            }
        });
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
