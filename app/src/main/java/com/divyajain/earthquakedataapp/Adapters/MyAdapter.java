package com.divyajain.earthquakedataapp.Adapters;



import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.divyajain.earthquakedataapp.Data.Earthquake;
import com.divyajain.earthquakedataapp.Fragments.MapDialogFragment;
import com.divyajain.earthquakedataapp.R;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Earthquake> values;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView magnitude;
        public TextView depth;
        public TextView primary_state;
        public TextView date;
        public TextView time;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            magnitude = (TextView) v.findViewById(R.id.magnitude);
            depth = (TextView) v.findViewById(R.id.depth);
            primary_state = (TextView) v.findViewById(R.id.primary_state);
            date = (TextView) v.findViewById(R.id.date);
            time = (TextView) v.findViewById(R.id.time);
        }
    }


    public MyAdapter(ArrayList<Earthquake> myDataset, Context context) {

       values = myDataset;
        this.context = context;
   }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Earthquake name = values.get(position);
        holder.magnitude.setText(name.getMagnitude());
        if(Float.valueOf(name.getMagnitude()) >= 8)
        {
            holder.magnitude.setBackgroundColor(Color.RED);
            holder.magnitude.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            holder.magnitude.setBackgroundColor(Color.rgb(228,160,136));
        }
        holder.depth.setText("Depth : " + name.getDepth());
        holder.date.setText(name.getDate());
        holder.time.setText(name.getTime());
        holder.primary_state.setText(name.getSubArea());
        SpannableString spannableString = new SpannableString(holder.primary_state.getText());
        holder.primary_state.setMovementMethod(LinkMovementMethod.getInstance());
        ClickableSpan click = new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }
        };
        spannableString.setSpan(click, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.primary_state.setText(spannableString, TextView.BufferType.SPANNABLE);


        holder.primary_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Double latitude = Double.valueOf(name.getLatitude());
                Double longitude = Double.valueOf(name.getLongitude());
                Bundle bundle = new Bundle();
                bundle.putDouble("latitude",latitude);
                bundle.putDouble("longitude",longitude);
                bundle.putString("magnitude", name.getMagnitude());
                MapDialogFragment mapfragment = new MapDialogFragment();
                mapfragment.setArguments(bundle);
                FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content_main,mapfragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
