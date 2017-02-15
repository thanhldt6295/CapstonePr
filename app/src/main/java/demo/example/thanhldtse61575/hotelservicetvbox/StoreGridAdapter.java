package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

/**
 * Created by ThanhLDTSE61575 on 1/9/2017.
 */
public class StoreGridAdapter extends BaseAdapter {

    private List<Service> listService;
    private int listSize;
    private Context context;
    private List<CartItem> cart;
    LayoutInflater layoutInflater;

    ImageView image;
    TextView name;
    TextView price;
    TextView description;
    EditText quantity;
    Button btnPlus;
    Button btnMinus;
    Button btnOrder;

    public StoreGridAdapter(List<Service> listService, int listSize, Context context, List<CartItem> cart) {
        this.listService = listService;
        this.listSize = listSize;
        this.context = context;
        this.cart = cart;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listSize;
    }

    @Override
    public Service getItem(int position) {
        return listService.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.layout_griditem, null);
        TextView tv = (TextView) convertView.findViewById(R.id.textViewGrid);
        ImageView iv = (ImageView) convertView.findViewById(R.id.imageViewGrid);
        String url = getItem(position).getImage();
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop().into(iv);
        tv.setText(getItem(position).getServiceName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                image = (ImageView) v.findViewById(R.id.imageViewDetail);
                name = (TextView) v.findViewById(R.id.txtServiceName);
                price = (TextView) v.findViewById(R.id.txtUnitPrice);
                description = (TextView) v.findViewById(R.id.txtDescription);
                quantity = (EditText) v.findViewById(R.id.txtQuantity);
                btnPlus = (Button) v.findViewById(R.id.btnPlus);
                btnMinus = (Button) v.findViewById(R.id.btnMinus);
                btnOrder = (Button) v.findViewById(R.id.btnOrder);


                name.setText(getItem(position).getServiceName());
                String url = getItem(position).getImage();
                Picasso.with(context)
                        .load(url)
                        .placeholder(R.drawable.loading)
                        .fit()
                        .centerCrop().into(image);
                DecimalFormat format = new DecimalFormat("###,###.#");
                price.setText(format.format(getItem(position).getUnitPrice()) + "đ");
                description.setText(getItem(position).getDescription());
                quantity.setText("1");
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
                                CartItem cartItem = new CartItem(getItem(position).getServiceID(),
                                        getItem(position).getServiceName(), getItem(position).getCategoryID(),
                                        getItem(position).getUnitPrice(), getItem(position).getDescription(),
                                        getItem(position).getImage(), Integer.parseInt(quantity.getText().toString()));
                                cart.add(cartItem);

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
                btnMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int n = Integer.parseInt(quantity.getText().toString());
                        if (n > 1) {
                            StringBuilder qty = new StringBuilder();
                            qty.append(n - 1);
                            quantity.setText(qty);
                        }
                    }
                });
                btnPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int n = Integer.parseInt(quantity.getText().toString());
                        if (n < 100) {
                            StringBuilder qty = new StringBuilder();
                            qty.append(n + 1);
                            quantity.setText(qty);
                        }
                    }
                });
            }
        });

        return convertView;
    }
}
