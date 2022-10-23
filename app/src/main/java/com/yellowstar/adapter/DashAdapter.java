package com.yellowstar.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yellowstar.Callfunction;

import java.util.ArrayList;

import com.yellowstar.R;


public class DashAdapter extends RecyclerView.Adapter<DashAdapter.ContactHolder> {

    // List to store all the contact details
    private ArrayList<Dash> contactsList;
    private Context mContext;
    private Activity activity = (Activity) mContext;
    Callfunction callfunction;
    // Counstructor for the Class
    public DashAdapter(ArrayList<Dash> contactsList_, Activity activity_, Callfunction callfunction_) {
        contactsList = contactsList_;
        activity = activity_;
        callfunction = callfunction_;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.dash_list, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
        final Dash dash = contactsList.get(position);

        // Set the data to the views here
        holder.txtsection.setText(dash.getName());
        holder.tvdescription.setText(dash.getDesc());

      /*  if(position==0) {holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.scan_icon));}
        else if(position==1) {holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.scan_icon));}
        else if(position==2) {holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.scan_icon));}
        else if(position==3) {holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.scan_icon));}
*/
        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                callfunction.ScanData();
            /*    Intent dash_intent =  new Intent(activity, Performance.class);
                activity.startActivity(dash_intent);
                activity.overridePendingTransition(R.anim.fade_in_fragment,R.anim.fade_out_fragment);*/
            }
        });

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relative;
        private TextView txtsection,tvdescription;
        private ImageView image;

        public ContactHolder(View itemView) {
            super(itemView);

            relative = itemView.findViewById(R.id.relative);
            image = itemView.findViewById(R.id.image);
            txtsection = itemView.findViewById(R.id.txtsection);
            tvdescription = itemView.findViewById(R.id.tvdescription);


        }

    }
}
