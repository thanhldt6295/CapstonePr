package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class RoomServicesActivity extends AppCompatActivity {

    String[] titles = {"FOODS & BEVERAGES", "HOUSEKEEPING", "MAINTENANCE", "FRONT-DESK"};
    int[] images = {R.drawable.hotelww, R.drawable.img_housekeeping, R.drawable.img_maintenance, R.drawable.img_request};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotional_chanel);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText("ROOM SERVICES");

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_menu);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RoomServicesAdapter ma = new RoomServicesAdapter(this, titles, images);
        rv.setAdapter(ma);
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
