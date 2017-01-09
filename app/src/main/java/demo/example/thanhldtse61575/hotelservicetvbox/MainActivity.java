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
    ImageView igvPrChanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnWelcome2Hotel = (Button)findViewById(R.id.btnWelcome);
        txtvWelcome = (TextView)findViewById(R.id.textViewWelcome);
        igvHotel1 = (ImageView)findViewById(R.id.imageViewHotel1);
        txtvService = (TextView)findViewById(R.id.textViewService);
        igvService = (ImageView)findViewById(R.id.imageViewService);
        igvPrChanel = (ImageView)findViewById(R.id.imageViewHotel2);

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

        igvPrChanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Tv = (TextView) findViewById(R.id.textView2);

                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                Typeface normalTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);

                Tv.setTypeface(boldTypeface);

                Intent prChl = new Intent(MainActivity.this,PrChanelActivity.class);
                startActivity(prChl);
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
