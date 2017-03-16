package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Price;

/**
 * Created by ThanhLDTSE61575 on 3/16/2017.
 */

public class PriceAdapter extends ArrayAdapter<Price> {

    private Context context;
    private List<Price> priceList;

    public PriceAdapter(Context context, int textViewResourceId,
                         List<Price> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.priceList = values;
    }

    public int getCount(){
        return priceList.size();
    }

    public Price getItem(int position){
        return priceList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(context);
        view.setTextColor(Color.WHITE);
        view.setGravity(Gravity.CENTER);
        view.setText(priceList.get(position).getPrice());
        view.setTextSize(30);

        return view;
    }

    //View of Spinner on dropdown Popping

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView view = new TextView(context);
        view.setTextColor(Color.BLACK);
        view.setText(priceList.get(position).getPrice());
        view.setHeight(60);
        view.setTextSize(20);

        return view;
    }
}

