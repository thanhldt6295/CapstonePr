package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnWelcome2Hotel;
    TextView txtvWelcome;
    ImageView igvHotel1;
    TextView txtvService;
    ImageView igvService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnWelcome2Hotel = (Button)findViewById(R.id.btnWelcome);
        txtvWelcome = (TextView)findViewById(R.id.textViewWelcome);
        igvHotel1 = (ImageView)findViewById(R.id.imageViewHotel1);
        txtvService = (TextView)findViewById(R.id.textViewService);
        igvService = (ImageView)findViewById(R.id.imageViewService);

        btnWelcome2Hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcome = new Intent(MainActivity.this,WelcomeActivity.class);
                startActivity(welcome);
            }
        });

        txtvWelcome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==true){
                    TextView Tv = (TextView) findViewById(R.id.textViewWelcome);

                    Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);

                    Tv.setTypeface(boldTypeface);
                } else{

                }
            }
        });

        txtvWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Tv = (TextView) findViewById(R.id.textViewWelcome);

                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                Typeface normalTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);

                Tv.setTypeface(boldTypeface);

                Intent welcome = new Intent(MainActivity.this,WelcomeActivity.class);
                startActivity(welcome);

                //Tv.setTypeface(normalTypeface);
            }
        });

        igvHotel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Tv = (TextView) findViewById(R.id.textViewWelcome);

                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                Typeface normalTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);

                Tv.setTypeface(boldTypeface);

                Intent welcome = new Intent(MainActivity.this,WelcomeActivity.class);
                startActivity(welcome);

                //Tv.setTypeface(normalTypeface);
            }
        });

        txtvService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Tv = (TextView) findViewById(R.id.textViewService);

                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                Typeface normalTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);

                Tv.setTypeface(boldTypeface);

                Intent roomService = new Intent(MainActivity.this,RoomServicesActivity.class);
                startActivity(roomService);
            }
        });

        igvService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Tv = (TextView) findViewById(R.id.textViewService);

                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                Typeface normalTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);

                Tv.setTypeface(boldTypeface);

                Intent roomService = new Intent(MainActivity.this,RoomServicesActivity.class);
                startActivity(roomService);
            }
        });
    }
}
