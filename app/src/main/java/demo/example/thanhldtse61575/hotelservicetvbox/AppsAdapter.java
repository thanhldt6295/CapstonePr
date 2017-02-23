package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ThanhLDTSE61575 on 2/17/2017.
 */

public class AppsAdapter extends BaseAdapter {

    private int icons[];
    private String items[];
    private Context context;
    private LayoutInflater inflater;

    public AppsAdapter(Context context, int icons[], String items[]){
        this.context=context;
        this.icons = icons;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
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

        icon.setImageResource(icons[position]);
        item.setText(items[position]);

        return gridView;
    }
}
