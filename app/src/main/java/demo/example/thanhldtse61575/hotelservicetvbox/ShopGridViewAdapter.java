package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

/**
 * Created by ThanhLDTSE61575 on 2/14/2017.
 */
public class ShopGridViewAdapter extends BaseAdapter {

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
        TextView price = (TextView) gridView.findViewById(R.id.txtUnitPrice);

        String url = list.get(position).getImage();
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop().into(icon);
        item.setText(list.get(position).getServiceName());
        price.setText(list.get(position).getUnitPrice()+"");

        return gridView;
    }
}
