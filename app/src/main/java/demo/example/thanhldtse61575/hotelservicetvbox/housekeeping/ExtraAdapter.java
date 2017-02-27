package demo.example.thanhldtse61575.hotelservicetvbox.housekeeping;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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

import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.R;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

/**
 * Created by ThanhLDTSE61575 on 2/27/2017.
 */

public class ExtraAdapter extends BaseAdapter {

    private Context ctx;
    private ListView extraListView;
    private List<Service> list;
    private LayoutInflater layoutInflater;

    ExtraAdapter(Context c, ListView exList, List<Service> list){
        this.ctx = c;
        this.extraListView = exList;
        this.list = list;
        layoutInflater = LayoutInflater.from(ctx);
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

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.layout_extraitem, null);

        ImageView image = (ImageView) convertView.findViewById(R.id.imageViewDetail);

        String url = list.get(position).getImage();
        Picasso.with(ctx)
                .load(url)
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop().into(image);
        TextView name = (TextView) convertView.findViewById(R.id.txtRequestName);
        name.setText(list.get(position).getServiceName());

        final EditText quantity = (EditText) convertView.findViewById(R.id.txtQuantity);
        quantity.setText("1");

        Button btnPlus = (Button) convertView.findViewById(R.id.btnPlus);
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

        Button btnMinus = (Button) convertView.findViewById(R.id.btnMinus);
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

        return convertView;
    }
}
