package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Image;

/**
 * Created by ThanhLDTSE61575 on 2/25/2017.
 */

public class EcardAdapter extends BaseAdapter {

    private List<Image> list;
    private Context context;
    private LayoutInflater inflater;

    public EcardAdapter(Context context, List<Image> list){
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

        ImageView imageView = (ImageView) gridView.findViewById(R.id.imageViewGrid);

        String url = list.get(position).getImage();
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop().into(imageView);

        return gridView;
    }
}
