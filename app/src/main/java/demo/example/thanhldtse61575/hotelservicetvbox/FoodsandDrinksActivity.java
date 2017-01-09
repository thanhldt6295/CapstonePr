package demo.example.thanhldtse61575.hotelservicetvbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class FoodsandDrinksActivity extends AppCompatActivity {

    GridView gridView;

    String itemList[] = {"Rice", "Meat", "Honnor"};

    int itemIcon[] = {R.drawable.h,R.drawable.h,R.drawable.h};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodsand_drinks);

        gridView = (GridView) findViewById(R.id.gridView);

        GridAdapter adapter = new GridAdapter(this, itemIcon, itemList);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FoodsandDrinksActivity.this,itemList[position],Toast.LENGTH_SHORT).show();
            }
        });
    }
}
