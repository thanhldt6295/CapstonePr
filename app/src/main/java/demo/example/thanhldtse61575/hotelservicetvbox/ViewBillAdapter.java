package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.content.Intent;
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

    public ViewBillAdapter(Context c, List<OrderDetail> details) {
        this.c = c;
        this.details = details;
        layoutInflater = LayoutInflater.from(c);
    }

    public ViewBillAdapter(Context c) {
        this.c = c;
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

        serviceName.setText(getItem(position).getName());
        category.setText(getItem(position).getCategory());
        DecimalFormat format = new DecimalFormat("###,###.#");
        unitPrice.setText(format.format(getItem(position).getUnitPrice()) + "đ");
        quantity.setText(getItem(position).getQuantity() + "");
        itemTotal.setText(format.format(getItem(position).getUnitPrice() * getItem(position).getQuantity()) + "đ");


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return convertView;
    }
}
