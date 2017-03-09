package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Recommend;

/**
 * Created by ThanhLDTSE61575 on 1/10/2017.
 */
public class MainAdapter extends RecyclerView.Adapter<Holder> {

    Context c;
    int[] titles;
    int[] images;

    public MainAdapter(Context c, int[] titles, int[] images) {
        this.c = c;
        this.titles = titles;
        this.images = images;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_holder, parent, false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.img.setImageResource(images[position]);
        holder.txt.setText(titles[position]);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {

                switch(pos){
                    case 0:
                        holder.img.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(50, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                    case MotionEvent.ACTION_UP:
                                        c.startActivity(new Intent(c, AppsActivity.class));

                                    case MotionEvent.ACTION_CANCEL: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(0, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                }
                                return true;
                            }
                        });
                        break;
                    case 1:
                        holder.img.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(50, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                    case MotionEvent.ACTION_UP:
                                        c.startActivity(new Intent(c, PromotionalChanelActivity.class));

                                    case MotionEvent.ACTION_CANCEL: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(0, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                }
                                return true;
                            }
                        });
                        break;
                    case 2:
                        holder.img.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(50, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                    case MotionEvent.ACTION_UP:
                                        c.startActivity(new Intent(c, FoodyActivity.class));

                                    case MotionEvent.ACTION_CANCEL: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(0, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                }
                                return true;
                            }
                        });
                        break;
                    case 3:
                        holder.img.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(50, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                    case MotionEvent.ACTION_UP:
                                        c.startActivity(new Intent(c, ExtraActivity.class));

                                    case MotionEvent.ACTION_CANCEL: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(0, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                }
                                return true;
                            }
                        });
                        break;
                    case 4:
                        holder.img.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(50, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                    case MotionEvent.ACTION_UP:
                                        c.startActivity(new Intent(c, RequestTicketActivity.class));

                                    case MotionEvent.ACTION_CANCEL: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(0, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                }
                                return true;
                            }
                        });
                        break;
                    case 5:
                        holder.img.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(50, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                    case MotionEvent.ACTION_UP:
                                        c.startActivity(new Intent(c, ShoppingActivity.class));

                                    case MotionEvent.ACTION_CANCEL: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(0, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                }
                                return true;
                            }
                        });
                        break;
                    case 6:
                        holder.img.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(50, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                    case MotionEvent.ACTION_UP:
                                        c.startActivity(new Intent(c, EcardActivity.class));

                                    case MotionEvent.ACTION_CANCEL: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(0, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                }
                                return true;
                            }
                        });
                        break;
                    case 7:
                        holder.img.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(50, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                    case MotionEvent.ACTION_UP:
                                        c.startActivity(new Intent(c, ViewBillActivity.class));

                                    case MotionEvent.ACTION_CANCEL: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(0, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                }
                                return true;
                            }
                        });
                        break;
                    case 8:
                        holder.img.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(50, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                    case MotionEvent.ACTION_UP:
                                        c.startActivity(new Intent(c, SurveyActivity.class));

                                    case MotionEvent.ACTION_CANCEL: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(0, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                }
                                return true;
                            }
                        });
                        break;
                    case 9:
                        holder.img.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(50, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                    case MotionEvent.ACTION_UP:
                                        c.startActivity(new Intent(c, RecommendActivity.class));

                                    case MotionEvent.ACTION_CANCEL: {
                                        ImageView image = (ImageView) v;
                                        image.setColorFilter(Color.argb(0, 0, 0, 0));
                                        v.invalidate();
                                        break;
                                    }
                                }
                                return true;
                            }
                        });
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
