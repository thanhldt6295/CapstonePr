package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.OrderDetail;

/**
 * Created by ThanhLDTSE61575 on 2/18/2017.
 */

public class PendingAdapter extends BaseAdapter{

    private String str = "";
    private Context ctx;
    private ListView orderListView;
    private List<OrderDetail> cart;
    private LayoutInflater layoutInflater;
    private TextView total;

    public PendingAdapter(Context ctx, ListView orderListView, List<OrderDetail> cart, TextView total) {
        this.ctx = ctx;
        this.orderListView = orderListView;
        this.cart = cart;
        this.total = total;
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

        convertView = layoutInflater.inflate(R.layout.layout_pendingitem, null);

        ImageView image = (ImageView) convertView.findViewById(R.id.imageViewDetail);
        TextView name = (TextView) convertView.findViewById(R.id.txtServiceName);
        TextView unitPrice = (TextView) convertView.findViewById(R.id.txtUnitPrice);
        TextView quantity = (TextView) convertView.findViewById(R.id.txtQuantity);
        TextView deliveryTime = (TextView) convertView.findViewById(R.id.txtDeliveryTime);
        EditText comment = (EditText) convertView.findViewById(R.id.txtComment);
        Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);

        String url = cart.get(position).getImage();
        Picasso.with(ctx)
           .load(url)
           .placeholder(R.drawable.loading)
           .fit()
           .centerCrop().into(image);

        name.setText(cart.get(position).getServiceName());
        DecimalFormat format = new DecimalFormat("###,###.#");
        unitPrice.setText(format.format(cart.get(position).getUnitPrice()) + "đ");
        quantity.setText(cart.get(position).getQuantity()+"");

        SimpleDateFormat isoFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        isoFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
        String normalDate = isoFormat.format(new java.util.Date(cart.get(position).getDeliverTime()*1000));;
        deliveryTime.setText(normalDate);

        comment.setEnabled(false);
        comment.setText(cart.get(position).getNote());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = cart.get(position).getOrderDetailID();
                str = a + "";
                new AlertDialog.Builder(ctx)
                        .setTitle("Confirm Cancel")
                        .setMessage("Are you sure?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                class SendDataToServer extends AsyncTask<String, String, String> {
                                    @Override
                                    protected String doInBackground(String... params) {
                                        CommonService commonService = new CommonService();
                                        return commonService.getData(params[0]);
                                    }
                                }
                                new SendDataToServer().execute("http://capstoneserver2017.azurewebsites.net/api/OrderDetailsApi/DeletePending/" + str);
                                cart.remove(position);
                                if(cart.size() == 0){
                                    total.setText("0đ");
                                }
                                notifyDataSetChanged();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        float t = 0;
        for (int i = 0; i < cart.size(); i++){
            t += cart.get(i).getQuantity() * cart.get(i).getUnitPrice();
        }
        total.setText(format.format(t) + "đ");

        return convertView;
    }
}
