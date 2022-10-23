package com.yellowstar.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.yellowstar.R;
import com.yellowstar.activity.DetailPending;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ContactHolder> {

    // List to store all the contact details
    private ArrayList<Contact> contactsList;
    private Context mContext;
    private Activity activity = (Activity) mContext;

    // Counstructor for the Class
    public MyAdapter(ArrayList<Contact> contactsList, Activity activity_) {
        this.contactsList = contactsList;
        this.activity = activity_;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.performance_list, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        final Contact contact = contactsList.get(position);

        // Set the data to the views here
        holder.txtName.setText(contact.getLocation());
        holder.tvtype.setText(contact.getDate());
        holder.tvread.setText(contact.getLuminary());
        holder.tvwrite.setText(contact.getBattery());
        holder.tvlisten.setText(contact.getPanel());

       byte[] bitmapdata = contact.getPhoto();

        if(bitmapdata!=null)
        {
            Bitmap  bitmap = convertCompressedByteArrayToBitmap(bitmapdata);
            Log.e("kukyukyky__vvvv_",""+bitmapdata);
            Log.e("kukyukyky__vv_",""+bitmap);
            holder.image.setImageBitmap(bitmap);
        }
        else
        {
            holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.no_image));
        }

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String ID = contactsList.get(position).getId();
                Intent intent = new Intent(activity, DetailPending.class);
                intent.putExtra("ID",""+ID);
                activity.startActivity(intent);
            }
        });

    }

    public static Bitmap convertCompressedByteArrayToBitmap(byte[] src){
        return BitmapFactory.decodeByteArray(src, 0, src.length);
    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private TextView txtName,tvtype,tvread,tvlisten,tvwrite;
        LinearLayout linear;
        private ImageView image;

        public ContactHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            txtName = itemView.findViewById(R.id.tvname);
            tvtype = itemView.findViewById(R.id.tvtype);
            tvread = itemView.findViewById(R.id.tvread);
            tvlisten = itemView.findViewById(R.id.tvlisten);
            tvwrite = itemView.findViewById(R.id.tvwrite);
            linear = itemView.findViewById(R.id.linear);

        }

    }
}
