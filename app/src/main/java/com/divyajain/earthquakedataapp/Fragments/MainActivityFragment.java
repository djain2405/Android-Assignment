package com.divyajain.earthquakedataapp.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divyajain.earthquakedataapp.Data.Earthquake;
import com.divyajain.earthquakedataapp.Adapters.MyAdapter;
import com.divyajain.earthquakedataapp.R;

import java.util.ArrayList;

public class MainActivityFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private DividerItemDecoration divider;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ArrayList<Earthquake> temp = bundle.getParcelableArrayList("earthquakelist");
            adapter = new MyAdapter(temp, getContext());
            recyclerView.setAdapter(adapter);
        }
        return rootView;
    }
}
