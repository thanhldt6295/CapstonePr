package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.OrderDetail;

/**
 * Created by VULHSE61532 on 1/15/2017.
 */

public class ViewBillAdapter extends BaseAdapter {

    private Context c;
    private List<OrderDetail> details;
    LayoutInflater layoutInflater;
    private TextView total;

    public ViewBillAdapter(Context c, List<OrderDetail> details, TextView total) {
        this.c = c;
        this.details = details;
        this.total = total;
        layoutInflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return details.size();
    }

    @Override
    public OrderDetail getItem(int position) {
        return details.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.layout_billitem, null);
        TextView serviceName = (TextView) convertView.findViewById(R.id.txtServiceName);
        TextView category = (TextView) convertView.findViewById(R.id.txtCategory);
        TextView unitPrice = (TextView) convertView.findViewById(R.id.txtUnitPrice);
        TextView quantity = (TextView) convertView.findViewById(R.id.txtQuantity);
        TextView itemTotal = (TextView) convertView.findViewById(R.id.txtItemTotal);

        serviceName.setText(getItem(position).getServiceName());
        category.setText(getItem(position).getCategoryName());
        DecimalFormat format = new DecimalFormat("###,###,###.#");
        unitPrice.setText(format.format(getItem(position).getUnitPrice()) +" "+ c.getResources().getString(R.string.USD));

        if(getItem(position).getServiceName().equals(c.getResources().getString(R.string.room_order))){
            if(getItem(position).getQuantity()==1) quantity.setText(getItem(position).getQuantity() + " " + c.getResources().getString(R.string.day));
            else quantity.setText(getItem(position).getQuantity() + " " + c.getResources().getString(R.string.days));
        } else {
            quantity.setText(getItem(position).getQuantity() + "");
        }

        itemTotal.setText(format.format(getItem(position).getUnitPrice() * getItem(position).getQuantity()) +" "+ c.getResources().getString(R.string.USD));

        float t = 0;
        for (int i = 0; i < details.size(); i++){
            t += details.get(i).getQuantity() *details.get(i).getUnitPrice();
        }
        total.setText(format.format(t) +" "+ c.getResources().getString(R.string.USD));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return convertView;
    }
}
