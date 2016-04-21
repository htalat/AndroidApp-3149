package com.buffapps.hassan.app3149;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hassan on 4/21/16.
 */
public class ItemAdapter extends ArrayAdapter<Item> {
    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_item, parent, false);
        }
        // Lookup view for data population
        TextView txtItemName = (TextView) convertView.findViewById(R.id.itemName);
        TextView txtItemQuantity = (TextView) convertView.findViewById(R.id.itemQuantity);
        TextView txtItemPrice = (TextView) convertView.findViewById(R.id.itemQuantity);

        // Populate the data into the template view using the data object
        txtItemName.setText(item.getName());
        txtItemPrice.setText(item.getPrice()+"");
        txtItemQuantity.setText(item.getQuantity()+"");

        return convertView;
    }
}
