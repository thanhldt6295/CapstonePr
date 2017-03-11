package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

/**
 * Created by ThanhLDTSE61575 on 3/10/2017.
 */

public class FoodyAdapter extends BaseAdapter{
    private List<Service> list;
    private Context context;
    private LayoutInflater inflater;
    private RelativeLayout relativeLayout;
    private int quantity;
    private List<CartItem> cart = new ArrayList<CartItem>();

    FoodyAdapter(Context context, List<Service> list, RelativeLayout relativeLayout, List<CartItem> cart){
        this.context=context;
        this.list = list;
        this.relativeLayout = relativeLayout;
        this.cart = cart;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        View gridView = convertView;
        if(convertView==null){
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.layout_griditem,null);
            holder = new ViewHolder();

            holder.image = (ImageView) gridView.findViewById(R.id.imageViewGrid);
            holder.name = (TextView) gridView.findViewById(R.id.textViewGrid);
            holder.price = (TextView) gridView.findViewById(R.id.txtUnitPrice);
            holder.btnDetail = (Button) gridView.findViewById(R.id.btnDetail);

            holder.btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    ViewGroup container = (ViewGroup) holder.layoutInflater.inflate(R.layout.layout_storeitem, null);
                    holder.popup = new PopupWindow(container, 600, 600, true);
                    holder.popup.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

                    Button btnOrder = (Button) container.findViewById(R.id.btnOrder);
                    ImageView icon = (ImageView) container.findViewById(R.id.imageViewDetail);
                    TextView item = (TextView) container.findViewById(R.id.txtServiceName);
                    TextView price = (TextView) container.findViewById(R.id.txtUnitPrice);
                    TextView description = (TextView) container.findViewById(R.id.txtDescription);

                    String url = list.get(position).getImage();
                    Picasso.with(context)
                            .load(url)
                            .placeholder(R.drawable.loading)
                            .fit()
                            .centerCrop().into(icon);
                    item.setText(list.get(position).getServiceName());
                    price.setText(list.get(position).getUnitPrice() + "");
                    description.setText(list.get(position).getDescription());

                    btnOrder.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN: {
                                    Button view = (Button) v;
                                    view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                                    v.invalidate();
                                    break;
                                }
                                case MotionEvent.ACTION_UP:
                                    if(cart.size()==0) quantity = 0;
                                    quantity = quantity + 1;
                                    Toast toast = Toast.makeText(context, quantity+"", Toast.LENGTH_SHORT);
                                    TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                    vToast.setTextColor(Color.WHITE);
                                    vToast.setTextSize(30);
                                    toast.show();

                                    Service sv = list.get(position);
                                    if (cart.size() == 0) {
                                        cart.add(new CartItem(sv.getServiceID(), sv.getServiceName(), sv.getCategoryID(),
                                                sv.getUnitPrice(), sv.getDescription(), sv.getImage(), 1, ""));
                                    }
                                    else {
                                        boolean isHave = false;
                                        for (CartItem od : cart) {
                                            if (od.getServiceID() == sv.getServiceID()) {
                                                isHave = true;
                                                od.setQuantity(od.getQuantity() + 1);
                                            }
                                        }
                                        if (!isHave) {
                                            cart.add(new CartItem(sv.getServiceID(), sv.getServiceName(), sv.getCategoryID(),
                                                    sv.getUnitPrice(), sv.getDescription(), sv.getImage(), 1, ""));
                                        }
                                    }

                                case MotionEvent.ACTION_CANCEL: {
                                    Button view = (Button) v;
                                    view.getBackground().clearColorFilter();
                                    view.invalidate();
                                    break;
                                }
                            }
                            return true;
                        }
                    });

                    container.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            holder.popup.dismiss();
                            return true;
                        }
                    });
                }
            });
            gridView.setTag(holder);
        } else
            holder = (ViewHolder) gridView.getTag();

        String url = list.get(position).getImage();
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop().into(holder.image);
        holder.name.setText(list.get(position).getServiceName());
        holder.price.setText(list.get(position).getUnitPrice()+"");
        return gridView;
    }

    class ViewHolder {
        ImageView image;
        TextView name;
        TextView price;
        Button btnDetail;
        PopupWindow popup;
        LayoutInflater layoutInflater;
    }
}
