package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    String[] titles = {"PROMOTIONAL CHANNEL", "ROOM SERVICES", "VIRTUAL SHOPPING", "E-CARD", "SURVEY", "VIEW BILL"};
    int[] images = {R.drawable.pool, R.drawable.room, R.drawable.shop, R.drawable.card, R.drawable.survey, R.drawable.bill};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Hotel Service TV Box");

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_menu);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        MainAdapter ma = new MainAdapter(this, titles, images);
        rv.setAdapter(ma);
    }
}
