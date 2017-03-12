package demo.example.thanhldtse61575.hotelservicetvbox;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
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
        itemView.setOnClickListener(this);
        itemView.setClickable(true);
        itemView.setFocusable(true);
        itemView.setFocusableInTouchMode(true);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener icl){
        this.itemClickListener = icl;
    }
}
