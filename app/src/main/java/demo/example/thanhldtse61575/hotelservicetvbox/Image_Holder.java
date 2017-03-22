package demo.example.thanhldtse61575.hotelservicetvbox;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import demo.example.thanhldtse61575.hotelservicetvbox.R;

/**
 * Created by ASUS on 3/22/2017.
 */

public class Image_Holder extends RecyclerView.ViewHolder {

    ImageView img;

    public Image_Holder(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.image);
    }
}
