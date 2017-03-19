package demo.example.thanhldtse61575.hotelservicetvbox;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ThanhLDTSE61575 on 1/10/2017.
 */
public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView img;
    TextView txt;
    ItemClickListener itemClickListener;

    public Holder(final View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.image);
        txt = (TextView) itemView.findViewById(R.id.title);
        txt.getBackground().setAlpha(138);
        itemView.setOnClickListener(this);
        itemView.setClickable(true);
        itemView.setFocusable(true);
        itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    img.setColorFilter(Color.argb(66, 6, 66, 26));
                    txt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                } else {
                    img.setColorFilter(Color.argb(0, 0, 0, 0));
                    txt.setTypeface(Typeface.DEFAULT);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener icl){
        this.itemClickListener = icl;
    }
}
