package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cabanettes.axel.nguyen.eric.depoker.R;

import java.util.List;

/**
 * Created by Eric on 14/03/2018.
 */

public class CustomListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int id;
    private List<String> items;

    public CustomListAdapter(Context context, int textViewResourceId, List<String> list) {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        View mView = v;
        if (mView == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        TextView text = (TextView) mView.findViewById(R.id.textView);

        if (items.get(position) != null) {
            text.setText(items.get(position));
            text.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, MainActivity.class));
                }
            });

        }

        return mView;
    }

}
