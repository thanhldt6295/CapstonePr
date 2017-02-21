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
//       img.setOnTouchListener(new View.OnTouchListener() {
//            private Rect rect;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_DOWN){
//                    img.setColorFilter(Color.argb(50, 0, 0, 0));
//                    rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
//                }
//                if(event.getAction() == MotionEvent.ACTION_UP){
//                    img.setColorFilter(Color.argb(0, 0, 0, 0));
//                }
//                if(event.getAction() == MotionEvent.ACTION_CANCEL){
//                    if(!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
//                        img.setColorFilter(Color.argb(0, 0, 0, 0));
//                    }
//                }
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN: {
//                        ImageView image = (ImageView) v;
//                        image.setColorFilter(Color.argb(50, 0, 0, 0));
//                        v.invalidate();
//                        break;
//                    }
//
//                    case MotionEvent.ACTION_UP:
//
//                    case MotionEvent.ACTION_CANCEL: {
//                        ImageView image = (ImageView) v;
//                        image.setColorFilter(Color.argb(0, 0, 0, 0));
//                        v.invalidate();
//                        break;
//                    }
//                }
//                return true;
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener icl){
        this.itemClickListener = icl;
    }
}
