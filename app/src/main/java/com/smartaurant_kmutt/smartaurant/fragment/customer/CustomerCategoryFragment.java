package com.smartaurant_kmutt.smartaurant.fragment.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.customer.CustomerMenuActivity;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;


@SuppressWarnings("unused")
public class CustomerCategoryFragment extends Fragment {
    int table;
    OrderItemDao orderItemDao;
    Button btRecommended;
    Button btPromotion;
    Button btAppetizer;
    Button btMainDish;
    Button btDessert;
    Button btDrinks;

    public CustomerCategoryFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CustomerCategoryFragment newInstance(Bundle bundle) {
        CustomerCategoryFragment fragment = new CustomerCategoryFragment();
        Bundle args = new Bundle();
        args.putBundle("bundle", bundle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        Bundle bundle = getArguments().getBundle("bundle");
        table = bundle.getInt("table");
        orderItemDao = bundle.getParcelable("orderItemDao");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_category, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        initButton(rootView);
    }

    private void initButton(View rootView) {
        btRecommended = rootView.findViewById(R.id.btRecommended);
        btPromotion = rootView.findViewById(R.id.btPromotion);
        btAppetizer = rootView.findViewById(R.id.btAppetizer);
        btMainDish = rootView.findViewById(R.id.btMainDish);
        btDessert = rootView.findViewById(R.id.btDessert);
        btDrinks = rootView.findViewById(R.id.btDrink);

        btRecommended.setOnClickListener(onClickListener);
        btPromotion.setOnClickListener(onClickListener);
        btAppetizer.setOnClickListener(onClickListener);
        btMainDish.setOnClickListener(onClickListener);
        btDessert.setOnClickListener(onClickListener);
        btDrinks.setOnClickListener(onClickListener);
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("table", table);
            bundle.putParcelable("orderItemDao", orderItemDao);
            if (v == btAppetizer) {
                bundle.putString("menuType", "enableAppetizer");
                bundle.putString("title", "Appetizer menu");
            } else if (v == btDessert) {
                bundle.putString("menuType", "enableDessert");
                bundle.putString("title", "Dessert menu");
            } else if (v == btDrinks) {
                bundle.putString("menuType", "enableDrinks");
                bundle.putString("title", "Drinks menu");
            } else if (v == btMainDish) {
                bundle.putString("menuType", "enableMainDish");
                bundle.putString("title", "Main dish menu");
            } else if (v == btPromotion) {
                bundle.putString("menuType", "enablePromotion");
                bundle.putString("title", "Promotion menu");
            } else if (v == btRecommended) {
                bundle.putString("menuType", "enableRecommended");
                bundle.putString("title", "Recommended menu");
            }

            CustomerCategoryFragmentListener categoryFragmentListener = (CustomerCategoryFragmentListener) getActivity();
            categoryFragmentListener.onClickCategoryButtonCustomerCategoryFragment(bundle);
        }
    };

    public interface CustomerCategoryFragmentListener {
        void onClickCategoryButtonCustomerCategoryFragment(Bundle bundle);
    }

}
