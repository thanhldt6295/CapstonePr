package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.App;

/**
 * Created by ThanhLDTSE61575 on 2/17/2017.
 */

public class AppsAdapter extends BaseAdapter {

    private List<App> list;
    private Context context;
    private LayoutInflater inflater;

    public AppsAdapter(Context context, List<App> list){
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
            gridView = inflater.inflate(R.layout.layout_appitem,null);
        }

        ImageView icon = (ImageView) gridView.findViewById(R.id.imageViewGrid);
        TextView item = (TextView) gridView.findViewById(R.id.textViewGrid);

        icon.setImageResource(list.get(position).getImg());
        item.setText(list.get(position).getName());

        return gridView;
    }
}
