package com.app.teampayup.compoundcalculator;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private final Activity context;
    private final ArrayList<Interest> interestList;
    TextView txtData1, txtData2, txtData3,txtData4;

    public ListAdapter(Activity context, ArrayList<Interest> interestList) {
        this.context = context;
        this.interestList = interestList;
    }

    @Override
    public int getCount() {
        return interestList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.tablelistview, null,true);
        txtData1 = (TextView)rowView.findViewById(R.id.txtData1);
        txtData2 = rowView.findViewById(R.id.txtData2);
        txtData3 = rowView.findViewById(R.id.txtData3);
        txtData4 = rowView.findViewById(R.id.txtData4);
        txtData1.setText(String.valueOf(position));
        txtData2.setText(String.format("$%.2f",interestList.get(position).getIterateInterest()));
        txtData3.setText(String.format("$%.2f",interestList.get(position).getTotalInterest()));
        txtData4.setText(String.format("$%.2f",interestList.get(position).getFinalBalance()));

        return rowView;
    };

}
