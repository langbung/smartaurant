package com.smartaurant_kmutt.smartaurant.fragment.staff;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import com.inthecheesefactory.thecheeselibrary.view.SlidingTabLayout;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.adapter.StaffPagerAdapter;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;


@SuppressWarnings("unused")
public class StaffFragment extends Fragment {
    int currentPage;
    String title;
    android.support.v7.widget.Toolbar toolbar;
    ViewPager viewPager;
    StaffPagerAdapter staffPagerAdapter;
    public StaffFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static StaffFragment newInstance(Bundle bundle) {
        StaffFragment fragment = new StaffFragment();
        Bundle args = new Bundle();
        args.putBundle("bundle",bundle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPage = getArguments().getBundle("bundle").getInt("currentPage",-1);
        title = getArguments().getBundle("bundle").getString("title","no title");
        init(savedInstanceState);
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_staff, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        initViewPager(rootView);
        if(title.equals("no title"))
            getActivity().setTitle("Customer call");
        else
            getActivity().setTitle(title);
    }



    private void initViewPager(View rootView) {
        staffPagerAdapter = new StaffPagerAdapter(getChildFragmentManager());
        viewPager = rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(staffPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0)
                    getActivity().setTitle("Customer call");
                else if(position==1)
                    getActivity().setTitle("Check order");
                else if(position==2)
                    getActivity().setTitle("Menu setting");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if(currentPage!=-1)
            viewPager.setCurrentItem(currentPage);
//        MyUtil.showText(title);
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
