package com.app.teampayup.compoundcalculator;

import android.content.Context;
import android.media.session.PlaybackState;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dd.processbutton.FlatButton;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class TableView extends AppCompatActivity {
    final Context context = this;
    ArrayList<Interest> interestList, tempList;
    TextView txtcol1, txtcol2, txtcol3, txtcol4;
    ListView lvTable, lvFilter;
    FloatingActionButton Fab;
    String dinterval, cinterval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_view);

        //find views
        Fab = findViewById(R.id.fab);
        txtcol1 = findViewById(R.id.txtcol1);
        txtcol2 = findViewById(R.id.txtcol2);
        txtcol3 = findViewById(R.id.txtcol3);
        txtcol4 = findViewById(R.id.txtcol4);
        lvTable = findViewById(R.id.lvTable);

        final Animation fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        final Animation fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        final Animation rotateForward = AnimationUtils.loadAnimation(this,R.anim.roate_forward);
        final Animation rotateBackward = AnimationUtils.loadAnimation(this,R.anim.roate_backward);



        //set up the Slidr
        int primary = getResources().getColor(R.color.colorPrimaryDark);
        int secondary = getResources().getColor(R.color.colorAccent);
        Slidr.attach(this, primary, secondary);

        //get data from intent
        Bundle extras = getIntent().getExtras();
        if (extras  != null){
            interestList = (ArrayList<Interest>) extras.getSerializable("interestList");
            dinterval = (String) extras.getString("durationInterval");
            cinterval = (String) extras.getString("compoundInterval");
        }
        setListView(interestList);


        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.Dialog dialog = new android.app.Dialog(context);
                dialog.setContentView(R.layout.filter_view);
                lvFilter = dialog.findViewById(R.id.lvFilter);
                // if button is clicked, close the custom dialog
                final String[]list = {"Yearly", "Monthly"};
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, list);
                lvFilter.setAdapter(adapter);
                lvFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        tempList = new ArrayList<Interest>();
                        int intervalCount = 0;
                        switch (list[i]){
                            case "Yearly":
                                intervalCount = 12;
                                break;
                            case "Monthly":
                                intervalCount = 1;
                                break;
                        }

                        Log.d("LISTCHECK", interestList.toString());
                        for (int c = 0; c < interestList.size(); c++){
                            if (c % intervalCount == 0){
                                if (list[i].equals("Yearly") && c == 1){

                                }
                                else{
                                    tempList.add(interestList.get(c));
                                }
                            }

                        }
                        Log.d("LISTCHECK", tempList.toString());
                        setListView(tempList);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }


    protected void setListView(ArrayList<Interest>interestList){

        switch (cinterval){
            case "Yearly":
                txtcol1.setText("Year");
                break;
            case "Half-Yearly":
                txtcol1.setText("Half-Year");
                break;
            case "Quarterly":
                txtcol1.setText("Quarter");
                break;
            case "Monthly":
                txtcol1.setText("Month");
                break;
            default:
                txtcol1.setText("Day");
                break;
        }
        txtcol2.setText("Current Interest");
        txtcol3.setText("Total Interest");
        txtcol4.setText("Final Balance");
        //create adapter and link
        ListAdapter adapter = new ListAdapter(this, interestList);
        lvTable.setAdapter(adapter);

    }
}
