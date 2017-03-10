package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Hobby;

/**
 * Created by ThanhLDTSE61575 on 2/24/2017.
 */

public class HobbyAdapter extends ArrayAdapter<Hobby> {

    private Context context;
    private List<Hobby> hobbyList;

    public HobbyAdapter(Context context, int textViewResourceId,
                        List<Hobby> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.hobbyList = values;
    }

    public int getCount(){
        return hobbyList.size();
    }

    public Hobby getItem(int position){
        return hobbyList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(context);
        view.setTextColor(Color.WHITE);
        view.setGravity(Gravity.CENTER);
        view.setText(hobbyList.get(position).getName());
        view.setTextSize(30);

        return view;
    }

    //View of Spinner on dropdown Popping

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView view = new TextView(context);
        view.setTextColor(Color.BLACK);
        view.setText(hobbyList.get(position).getName());
        view.setHeight(60);
        view.setTextSize(20);

        return view;
    }
}
