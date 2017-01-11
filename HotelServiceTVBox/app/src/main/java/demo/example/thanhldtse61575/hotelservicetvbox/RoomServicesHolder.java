package demo.example.thanhldtse61575.hotelservicetvbox;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ThanhLDTSE61575 on 1/11/2017.
 */
public class RoomServicesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView img;
    TextView txt;
    ItemClickListener itemClickListener;

    public RoomServicesHolder(View itemView) {

        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.image);
        txt = (TextView) itemView.findViewById(R.id.title);
        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        this.itemClickListener.onItemClick(v, getLayoutPosition());

    }

    public void setItemClickListener(ItemClickListener icl){
        this.itemClickListener = icl;
    }
}
