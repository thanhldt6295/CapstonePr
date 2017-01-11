package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RoomServicesActivity extends AppCompatActivity {

    String[] titles = {"FOODS AND BEVERAGES", "HOUSEKEEPING", "MAINTENANCE", "OTHERS REQUEST"};
    int[] images = {R.drawable.hotelww, R.drawable.pool, R.drawable.room, R.drawable.shop};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotional_chanel);
        getSupportActionBar().setTitle("Room Services");

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_menu);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RoomServicesAdapter ma = new RoomServicesAdapter(this, titles, images);
        rv.setAdapter(ma);
    }
}
