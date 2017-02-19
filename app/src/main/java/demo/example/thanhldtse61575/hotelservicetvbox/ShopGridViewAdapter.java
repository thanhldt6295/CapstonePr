package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URL;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

/**
 * Created by ThanhLDTSE61575 on 2/14/2017.
 */
public class ShopGridViewAdapter extends BaseAdapter {

//    private int icons[];
//    private String items[];
    private List<Service> list;
    private Context context;
    private LayoutInflater inflater;

    public ShopGridViewAdapter(Context context, List<Service> list){
        this.context=context;
        this.list = list;
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

        View gridView = convertView;
        if(convertView==null){
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.layout_griditem,null);
        }

        ImageView icon = (ImageView) gridView.findViewById(R.id.imageViewGrid);
        TextView item = (TextView) gridView.findViewById(R.id.textViewGrid);

        //icon.setImageResource(R.drawable.demo);
        Picasso.with(context)
                .load("http://files.softicons.com/download/system-icons/apple-logo-icons-by-thvg/png/128/Apple%20logo%20icon%20-%20Classic.png")
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop().into(icon);
        item.setText(list.get(position).getServiceName());

        return gridView;
    }
}
