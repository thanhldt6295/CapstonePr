package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RoomServicesActivity extends AppCompatActivity {

    TextView txtvFood;
    ImageView igvFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_services);

        txtvFood = (TextView)findViewById(R.id.textViewFood);
        igvFood = (ImageView)findViewById(R.id.imageViewFood);

        igvFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Tv = (TextView) findViewById(R.id.textViewFood);

                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                Typeface normalTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);

                Tv.setTypeface(boldTypeface);

                Intent foodPage = new Intent(RoomServicesActivity.this,FoodsandDrinksActivity.class);
                startActivity(foodPage);
            }
        });
    }
}
