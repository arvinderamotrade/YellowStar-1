package com.yellowstar.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yellowstar.adapter.Contact;
import com.yellowstar.adapter.MyAdapter;
import com.yellowstar.database.YellowStarDb;

import java.util.ArrayList;

import com.yellowstar.R;

public class Performance extends BaseActivity
{
    private MyAdapter listAdapter;
    private ArrayList<Contact> contactsList = new ArrayList<>();
    private RecyclerView recycler;
    ImageView backBtn;
    YellowStarDb indiaSolarDb;
    TextView no_data;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

   //     Utils utils = new Utils();
   //     utils.setStatusBarGradiant(Performance.this);

        indiaSolarDb = new YellowStarDb(getApplicationContext());
        int count = indiaSolarDb.GETLOCATION().size();
        Log.e("sizee__db",""+count);

        recycler = findViewById(R.id.recycler_view);
        no_data = findViewById(R.id.no_data);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        listAdapter = new MyAdapter(contactsList, this);
        recycler.setAdapter(listAdapter);

        //Load the date from the network or other resources
        //into the array list asynchronously

        if(count>0) {
            for (int k = 0; k <count; k++)
            {
                Contact contact = new Contact(indiaSolarDb.getAllData().get(k).getId(), indiaSolarDb.getAllData().get(k).getLocation(),
                        indiaSolarDb.getAllData().get(k).getDateTime(), indiaSolarDb.getAllData().get(k).getLuminary()
                        ,   indiaSolarDb.getAllData().get(k).getBattery(),    indiaSolarDb.getAllData().get(k).getPanel(),   indiaSolarDb.getAllData().get(k).getPhoto());

                contactsList.add(contact);

                Log.e("kukyukyky",indiaSolarDb.getAllData().get(k).getId()+"----"+indiaSolarDb.getAllData().get(k).getPhoto());
            }
            listAdapter.notifyDataSetChanged();
            recycler.setVisibility(View.VISIBLE);
        }
        else
        {
            no_data.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.backBtn:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent dash_intent =  new Intent(getApplicationContext(), HomeScreen.class);
        startActivity(dash_intent);
        overridePendingTransition(R.anim.fade_in_fragment,R.anim.fade_out_fragment);
        finish();
        finishAffinity();
    }
}