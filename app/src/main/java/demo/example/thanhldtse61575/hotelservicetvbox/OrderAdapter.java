package demo.example.thanhldtse61575.hotelservicetvbox;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.ToServer;

/**
 * Created by ThanhLDTSE61575 on 2/10/2017.
 */

public class OrderAdapter extends BaseAdapter {

    private String[] arraySpinner;

    private Context ctx;
    private ListView orderListView;
    private List<CartItem> cart;
    private LayoutInflater layoutInflater;
    private TextView total;
    private Button finalize;
    private Button clear;
    private Spinner spin;

    public OrderAdapter(Context ctx, ListView orderListView, List<CartItem> cart, TextView total, Button finalize, Button clear, Spinner spin) {
        this.ctx = ctx;
        this.orderListView = orderListView;
        this.cart = cart;
        this.total = total;
        this.finalize = finalize;
        this.clear = clear;
        this.spin = spin;
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

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.layout_cartitem, null);
        final View view = convertView;

        this.arraySpinner = new String[] {
                "15", "30", "45", "60", "100"
        };
        SpinnerAdapter adapter = new SpinnerAdapter(ctx,
                android.R.layout.simple_spinner_item, arraySpinner);
        spin.setAdapter(adapter);

        ImageView image = (ImageView) view.findViewById(R.id.imageViewDetail);

        String url = cart.get(position).getImage();
        Picasso.with(ctx)
                .load(url)
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop().into(image);
        TextView name = (TextView) view.findViewById(R.id.txtServiceName);
        name.setText(cart.get(position).getServiceName());
        TextView unitPrice = (TextView) view.findViewById(R.id.txtUnitPrice);
        final DecimalFormat format = new DecimalFormat("###,###.#");
        unitPrice.setText(format.format(cart.get(position).getUnitPrice()) +" "+ view.getResources().getString(R.string.USD));

        final EditText quantity = (EditText) view.findViewById(R.id.txtQuantity);
        quantity.setText(cart.get(position).getQuantity()+"");

        float t = getTotal(cart);
        total.setText(format.format(t) +" "+ view.getResources().getString(R.string.USD));

        Button btnPlus = (Button) view.findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(quantity.getText().toString());
                if (n < 100) {
                    n+=1;
                    quantity.setText(n+"");
                    cart.get(position).setQuantity(n);
                    float t1 = getTotal(cart);
                    total.setText(format.format(t1) +" "+ view.getResources().getString(R.string.USD));
                }
            }
        });

        Button btnMinus = (Button) view.findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(quantity.getText().toString());
                if (n > 1) {
                    n -=1;
                    quantity.setText(n+"");
                    cart.get(position).setQuantity(n);
                    float t2 = getTotal(cart);
                    total.setText(format.format(t2) +" "+ view.getResources().getString(R.string.USD));
                }
            }
        });

        Button btnDelete = (Button) view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.remove(position);
                float t3 = getTotal(cart);
                total.setText(format.format(t3) + view.getResources());
                if(cart.size() == 0){
                    total.setText("0 "+ view.getResources().getString(R.string.USD));
                }
                notifyDataSetChanged();
            }
        });

        final EditText comment = (EditText) view.findViewById(R.id.txtComment);
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
                if(cart.size()!=0) {
                    new AlertDialog.Builder(ctx)
                            .setTitle(R.string.confirm_clear)
                            .setMessage(R.string.confirm_question_do)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    cart.clear();
                                    total.setText("0 "+ view.getResources().getString(R.string.USD));
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }
        });

        finalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.size()!=0) {
                    new AlertDialog.Builder(ctx)
                            .setTitle(R.string.confirm_order)
                            .setMessage(R.string.confirm_question_do)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    final String returnList = new Gson().toJson(cart);
                                    final long time=60*Long.parseLong(spin.getSelectedItem().toString());
                                    final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
                                    final long time2Serv = calendar.getTimeInMillis()/1000 + time;
                                    class SendDataToServer extends AsyncTask<String, String, String> {

                                        @Override
                                        protected String doInBackground(String... params) {
                                            CommonService commonService = new CommonService();

                                            List<CartItem> acc = new Gson().fromJson(params[2], new TypeToken<List<CartItem>>() {}.getType());
                                            ToServer toServer = new ToServer( Double.parseDouble(params[1]), acc , Integer.parseInt(params[3]));

                                            return commonService.sendData(params[0], toServer)+"";
                                        }

                                        protected void onPostExecute(String response) {
                                            if(response.equals("200")){
                                                cart.clear();
                                                total.setText("0 "+ view.getResources().getString(R.string.USD));
                                                notifyDataSetChanged();

                                                Toast toast = Toast.makeText(ctx, R.string.confirm_order_accepted, Toast.LENGTH_SHORT);
                                                TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                                vToast.setTextColor(Color.WHITE);
                                                vToast.setTextSize(30);
                                                toast.show();
                                            }
                                            else{
                                                Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    new SendDataToServer().execute("http://capstoneserver2017.azurewebsites.net/api/OrderDetailsApi/SendListCart", time2Serv+"" , returnList+"", getDataFromSharedPreferences());
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }
        });

        return view;
    }

    private String getDataFromSharedPreferences(){
        SharedPreferences sharedPref = ctx.getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");
        return jsonPreferences;
    }

    private float getTotal(List<CartItem> cart){
        float total = 0;
        for (int i = 0; i < cart.size(); i++){
            total += cart.get(i).getQuantity() * cart.get(i).getUnitPrice();
        }
        return total;
    }
}
