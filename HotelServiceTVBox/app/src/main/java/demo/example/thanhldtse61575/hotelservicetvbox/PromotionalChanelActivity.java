package demo.example.thanhldtse61575.hotelservicetvbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class PromotionalChanelActivity extends AppCompatActivity {

    String[] titles = {"SPA", "GYM", "POOL", "CANTIN", "SPORT"};
    int[] images = {R.drawable.hotelww, R.drawable.pool, R.drawable.room, R.drawable.shop, R.drawable.card};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotional_chanel);
        getSupportActionBar().setTitle("Promotional Chanel");

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_menu);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        PromotionalChanelAdapter ma = new PromotionalChanelAdapter(this, titles, images);
        rv.setAdapter(ma);
    }
}
