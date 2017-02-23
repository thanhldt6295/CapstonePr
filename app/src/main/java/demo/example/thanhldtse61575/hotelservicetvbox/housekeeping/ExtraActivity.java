package demo.example.thanhldtse61575.hotelservicetvbox.housekeeping;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import demo.example.thanhldtse61575.hotelservicetvbox.R;

public class ExtraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));

        Bundle extra = getIntent().getExtras();
        int type = extra.getInt("type");
        switch (type){
            case 1:
                abTitle.setText(getResources().getString(R.string.bath_extras));
                break;
            case 2:
                abTitle.setText(getResources().getString(R.string.bed_extras));
                break;
            case 3:
                abTitle.setText(getResources().getString(R.string.room_extras));
                break;
        }
    }
}
