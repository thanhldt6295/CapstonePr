package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

/**
 * Created by ThanhLDTSE61575 on 3/1/2017.
 */

public class MaintenanceAdapter extends BaseAdapter {
    private Context ctx;
    private ListView listView;
    private List<Service> list;
    private LayoutInflater layoutInflater;

    MaintenanceAdapter(Context c, ListView listView, List<Service> list){
        this.ctx = c;
        this.listView = listView;
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.layout_maintenanceitem, null);

        ImageView image = (ImageView) convertView.findViewById(R.id.imageViewDetail);

        String url = list.get(position).getImage();
        Picasso.with(ctx)
                .load(url)
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop().into(image);
        TextView name = (TextView) convertView.findViewById(R.id.txtRequestName);
        name.setText(list.get(position).getServiceName());

        return  convertView;
    }
}
