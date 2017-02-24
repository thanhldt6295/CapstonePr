package demo.example.thanhldtse61575.hotelservicetvbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Recommend;

public class RecommendDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_detail);

        List<Recommend> RecommendEntityList = (List<Recommend>) getIntent().getSerializableExtra("list");
        int index = getIntent().getExtras().getInt("position");
        Recommend rec = RecommendEntityList.get(index);

        ImageView productImageView = (ImageView) findViewById(R.id.imageView);
        productImageView.setImageResource(rec.getImage());
        TextView productTitleTextView = (TextView) findViewById(R.id.recNameTV);
        productTitleTextView.setText(rec.getName());
        TextView productDetailsTextView = (TextView) findViewById(R.id.recDescriptTV);
        productDetailsTextView.setText(rec.getDescription());
    }
}
