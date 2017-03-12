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
 * Created by ThanhLDTSE61575 on 3/12/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] spinner;

    public SpinnerAdapter(Context context, int textViewResourceId, String[] spinner) {
        super(context, textViewResourceId, spinner);
        this.context = context;
        this.spinner = spinner;
    }

    public int getCount() {
        return spinner.length;
    }

    public String getItem(int position) {
        return spinner[position];
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(context);
        view.setTextColor(Color.CYAN);
        view.setText(spinner[position]);
        view.setTextSize(30);

        return view;
    }

    //View of Spinner on dropdown Popping

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView view = new TextView(context);
        view.setTextColor(Color.BLACK);
        view.setText(spinner[position]);
        view.setHeight(60);
        view.setTextSize(20);

        return view;
    }
}