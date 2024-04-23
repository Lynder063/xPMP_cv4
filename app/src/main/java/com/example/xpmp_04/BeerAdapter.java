package com.example.xpmp_04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BeerAdapter extends ArrayAdapter<BeerItem> {

    private LayoutInflater inflater;

    public BeerAdapter(Context context, List<BeerItem> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            holder = new ViewHolder();
            holder.text1 = convertView.findViewById(android.R.id.text1);
            holder.text2 = convertView.findViewById(android.R.id.text2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BeerItem item = getItem(position);
        if (item != null) {
            // Zobrazení názvu stupně v prvním poli
            holder.text1.setText(item.getStupen());
            // Zobrazení množství v druhém poli
            holder.text2.setText(String.valueOf(item.getAmount()) + " ml");
        }

        return convertView;
    }


    private static class ViewHolder {
        TextView text1;
        TextView text2;
    }
}
