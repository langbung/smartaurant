package com.smartaurant_kmutt.smartaurant.fragment.owner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.owner.OwnerActivity;
import com.smartaurant_kmutt.smartaurant.adapter.OwnerPagerAdapter;


@SuppressWarnings("unused")
public class OwnerFragment extends Fragment{

    int item;
    ViewPager viewPager;
    Toolbar toolbar;
    OwnerPagerAdapter ownerPagerAdapter;





    public OwnerFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OwnerFragment newInstance(int item) {
        OwnerFragment fragment = new OwnerFragment();
        Bundle args = new Bundle();
        args.putInt("item",item);
        fragment.setArguments(args);
        return fragment;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        setItem(getArguments().getInt("item"));
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        toolbar= rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Owner");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        viewPager = rootView.findViewById(R.id.viewPager);
        ownerPagerAdapter = new OwnerPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(ownerPagerAdapter);
        viewPager.setCurrentItem(getItem(),true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

}
