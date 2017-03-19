package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    private RelativeLayout relativeLayout;
    private PopupWindow popup;
    private LayoutInflater popupInflater;
    private String str = "";
    private Context ctx;
    private ListView orderListView;
    private List<OrderDetail> cart;
    private LayoutInflater layoutInflater;
    private TextView total;

    public PendingAdapter(Context ctx, ListView orderListView, List<OrderDetail> cart, TextView total, RelativeLayout relativeLayout) {
        this.ctx = ctx;
        this.orderListView = orderListView;
        this.cart = cart;
        this.total = total;
        this.relativeLayout = relativeLayout;
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
        TextView orderTime = (TextView) convertView.findViewById(R.id.txtOrderTime);
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
        unitPrice.setText(format.format(cart.get(position).getUnitPrice()) +" "+ ctx.getResources().getString(R.string.USD));
        quantity.setText(cart.get(position).getQuantity()+"");

        SimpleDateFormat isoFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        isoFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));

        String normalDate2 = isoFormat.format(new java.util.Date(cart.get(position).getOrderTime()*1000));;
        orderTime.setText(normalDate2);
        String normalDate = isoFormat.format(new java.util.Date(cart.get(position).getDeliverTime()*1000));;
        deliveryTime.setText(normalDate);

        comment.setEnabled(false);
        comment.setText(cart.get(position).getNote());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) popupInflater.inflate(R.layout.confirm_popup, null);

//                LinearLayout layoutPopup = (LinearLayout) container.findViewById(R.id.layoutPopup);
//                layoutPopup.getBackground().setAlpha(126);
//                LinearLayout layoutTitle = (LinearLayout) container.findViewById(R.id.layoutTitle);
//                layoutTitle.getBackground().setAlpha(200);
//                LinearLayout layoutContent = (LinearLayout) container.findViewById(R.id.layoutContent);
//                layoutContent.getBackground().setAlpha(238);
//                LinearLayout layoutBtn = (LinearLayout) container.findViewById(R.id.layoutBtn);
//                layoutBtn.getBackground().setAlpha(200);

                popup = new PopupWindow(container, 600, 300, true);
                popup.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

                popup.setOutsideTouchable(true);
                popup.getContentView().setFocusableInTouchMode(true);
                popup.getContentView().setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            popup.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popup.dismiss();
                        return true;
                    }
                });
                TextView confirm = (TextView) container.findViewById(R.id.tvConfirm);
                confirm.setText(container.getResources().getString(R.string.confirm_cancel));
                TextView content = (TextView) container.findViewById(R.id.tvContent);
                content.setText(container.getResources().getString(R.string.confirm_content));
                Button cancel = (Button) container.findViewById(R.id.btnCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup.dismiss();
                    }
                });
                Button okyes = (Button) container.findViewById(R.id.btnOK);
                okyes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int a = cart.get(position).getOrderDetailID();
                        str = a + "";
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
                            total.setText("0 " + ctx.getResources().getString(R.string.USD));
                        }
                        notifyDataSetChanged();
                        popup.dismiss();
                        Toast toast = Toast.makeText(ctx, R.string.cancelled, Toast.LENGTH_SHORT);
                        TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                        vToast.setTextColor(Color.WHITE);
                        vToast.setTextSize(20);
                        vToast.setTypeface(null, Typeface.BOLD);
                        toast.show();
                    }
                });
            }
        });

        float t = 0;
        for (int i = 0; i < cart.size(); i++){
            t += cart.get(i).getQuantity() * cart.get(i).getUnitPrice();
        }
        total.setText(format.format(t) +" "+ ctx.getResources().getString(R.string.USD));

        return convertView;
    }
}
