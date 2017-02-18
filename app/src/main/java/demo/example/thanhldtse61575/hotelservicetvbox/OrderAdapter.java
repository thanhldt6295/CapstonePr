package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;

/**
 * Created by ThanhLDTSE61575 on 2/10/2017.
 */

public class OrderAdapter extends BaseAdapter {

    private Context ctx;
    private ListView orderListView;
    private List<CartItem> cart;
    private LayoutInflater layoutInflater;
    private TextView total;
    private Button finalize;
    private Button clear;

    public OrderAdapter(Context ctx, ListView orderListView, List<CartItem> cart, TextView total, Button finalize, Button clear) {
        this.ctx = ctx;
        this.orderListView = orderListView;
        this.cart = cart;
        this.total = total;
        this.finalize = finalize;
        this.clear = clear;
        layoutInflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return cart.size();
    }

    @Override
    public Object getItem(int position) {
        return cart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.layout_orderitem, null);

        ImageView image = (ImageView) convertView.findViewById(R.id.imageViewDetail);
        image.setImageResource(R.drawable.demo);
//        String url = cart.get(position).getImage();
//        Picasso.with(ctx)
//                .load(url)
//                .placeholder(R.drawable.loading)
//                .fit()
//                .centerCrop().into(image);
        TextView name = (TextView) convertView.findViewById(R.id.txtServiceName);
        name.setText(cart.get(position).getServiceName());
        TextView unitPrice = (TextView) convertView.findViewById(R.id.txtUnitPrice);
        DecimalFormat format = new DecimalFormat("###,###.#");
        unitPrice.setText(format.format(cart.get(position).getUnitPrice()) + "đ");

        final EditText quantity = (EditText) convertView.findViewById(R.id.txtQuantity);
        quantity.setText(cart.get(position).getQuantity()+"");

        float t = 0;
        for (int i = 0; i < cart.size(); i++){
            t += cart.get(i).getQuantity() * cart.get(i).getUnitPrice();
        }
        total.setText(format.format(t) + "đ");

        Button btnPlus = (Button) convertView.findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(quantity.getText().toString());
                if (n < 100) {
                    StringBuilder qty = new StringBuilder();
                    qty.append(n + 1);
                    quantity.setText(qty);
                    cart.get(position).setQuantity(n+1);
                }
            }
        });

        Button btnMinus = (Button) convertView.findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(quantity.getText().toString());
                if (n > 1) {
                    StringBuilder qty = new StringBuilder();
                    qty.append(n - 1);
                    quantity.setText(qty);
                    cart.get(position).setQuantity(n-1);
                }
            }
        });

        Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.remove(position);
                if(cart.size() == 0){
                    total.setText("0đ");
                }
                notifyDataSetChanged();
            }
        });

        final EditText comment = (EditText) convertView.findViewById(R.id.txtComment);
        comment.setText(cart.get(position).getComment());
        if(comment!=null) {
            comment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    cart.get(position).setComment(s.toString());
                }
            });
        }

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(ctx)
                        .setTitle("Confirm Clear Your Order")
                        .setMessage("Do you really want to whatever?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                cart.clear();
                                total.setText("0đ");
                                notifyDataSetChanged();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        finalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.size()!=0) {
                    new AlertDialog.Builder(ctx)
                            .setTitle("Confirm Order")
                            .setMessage("Are you sure?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    class SendDataToServer extends AsyncTask<String, Void, Integer> {

                                        @Override
                                        protected Integer doInBackground(String... params) {
                                            CommonService commonService = new CommonService();
                                            int returnValue = commonService.sendData(params[0], params[1]);
                                            return returnValue;
                                        }

                                        protected void onPostExecute(Integer response) {
                                            //
                                        }
                                    }
                                    String returnList = new Gson().toJson(cart);
                                    new SendDataToServer().execute("http://localhost:49457/api/getapp/", "roomid=201&list=" + returnList + "&deliveryTime=1232323232323");
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }
        });

        return convertView;
    }
}
