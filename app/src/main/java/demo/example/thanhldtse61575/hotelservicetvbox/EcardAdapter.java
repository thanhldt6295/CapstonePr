package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ThanhLDTSE61575 on 2/25/2017.
 */

public class EcardAdapter extends BaseAdapter {

    private int icons[];
    private Context context;
    private LayoutInflater inflater;

    public EcardAdapter(Context context, int icons[]){
        this.context=context;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
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

        icon.setImageResource(icons[position]);

        return gridView;
    }
}
