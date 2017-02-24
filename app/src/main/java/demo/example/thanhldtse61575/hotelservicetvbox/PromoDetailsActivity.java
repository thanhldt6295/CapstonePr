package demo.example.thanhldtse61575.hotelservicetvbox;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PromoDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_details);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));

        Bundle extra = getIntent().getExtras();
        int type = extra.getInt("type");
        switch (type){
            case 0:
                abTitle.setText(getResources().getString(R.string.spa));
                break;
            case 1:
                abTitle.setText(getResources().getString(R.string.gym));
                break;
            case 2:
                abTitle.setText(getResources().getString(R.string.pool));
                break;
            case 3:
                abTitle.setText(getResources().getString(R.string.tennis));
                break;
            case 4:
                abTitle.setText(getResources().getString(R.string.golf));
                break;
        }
    }
}
