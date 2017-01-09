package demo.example.thanhldtse61575.hotelservicetvbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class PrChanelActivity extends AppCompatActivity {

    String[] titles = {"WELCOME", "PROMOTIONAL CHANNEL", "ROOM SERVICES", "SHOPPING", "E-CARD"};
    int[] images = {R.drawable.hotelww, R.drawable.pool, R.drawable.room, R.drawable.shop, R.drawable.card};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pr_chanel);

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_menu);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        PrChlMenuAdapter ma = new PrChlMenuAdapter(this, titles, images);
        rv.setAdapter(ma);
    }
}
