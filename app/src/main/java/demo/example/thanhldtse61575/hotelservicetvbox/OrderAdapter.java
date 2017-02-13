package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;

/**
 * Created by ThanhLDTSE61575 on 2/10/2017.
 */

public class OrderAdapter extends BaseAdapter {

        private Context ctx;
        private List<CartItem> list;
        private LayoutInflater inflater;
        private Button delete;

        public OrderAdapter(Context ctx, List<CartItem> list) {
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
        final ViewItem item;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_orderitem,
                    null);
            item = new ViewItem();

            item.productImageView = (ImageView) convertView
                    .findViewById(R.id.imageViewDetail);

            item.productTitle = (TextView) convertView.findViewById(R.id.txtServiceName);

            item.productPrice = (TextView) convertView.findViewById(R.id.txtUnitPrice);

            item.productQuantity = (EditText) convertView.findViewById(R.id.txtQuantity);

            item.productDelete = (Button) convertView.findViewById(R.id.btnDelete);

            item.productDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Remove
                }
            });

            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }

        return convertView;
    }

        private class ViewItem {
            ImageView productImageView;
            TextView productTitle;
            TextView productPrice;
            EditText productQuantity;
            Button productDelete;
        }
}
