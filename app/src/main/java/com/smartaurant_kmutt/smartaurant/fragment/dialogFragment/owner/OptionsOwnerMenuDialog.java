package com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.owner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;

import java.util.ArrayList;
import java.util.Arrays;


@SuppressWarnings("unused")
public class OptionsOwnerMenuDialog extends DialogFragment {
    MenuItemDao menu;
    ListView listMenu;
    TextView tvTitle;
    ArrayList<String> optionsList;

    public OptionsOwnerMenuDialog() {
        super();
    }

    @SuppressWarnings("unused")
    public static OptionsOwnerMenuDialog newInstance(MenuItemDao menu) {
        OptionsOwnerMenuDialog fragment = new OptionsOwnerMenuDialog();
        Bundle args = new Bundle();
        args.putParcelable("menu",menu);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(savedInstanceState);
        menu = getArguments().getParcelable("menu");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_options_in_menu_dialog, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here

    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById
        listMenu = rootView.findViewById(R.id.lvListMenu);
        optionsList = new ArrayList<>(Arrays.asList("Edit","Delete"));
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, optionsList);
        listMenu.setAdapter(arrayAdapter);
        tvTitle = rootView.findViewById(R.id.tvTitle);
        tvTitle.setText(menu.getName());

        listMenu.setOnItemClickListener(onItemClickListener);

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

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            DatabaseReference menuDatabase = FirebaseDatabase.getInstance().getReference().child("menu/"+menu.getName());
            if(optionsList.get(position).equals("Delete")){
                menuDatabase.addListenerForSingleValueEvent(removingMenu);
            }
            else if (optionsList.get(position).equals("Edit")){
                dismiss();
                OnOptionsMenuDialogListener onOptionsMenuDialogListener = (OnOptionsMenuDialogListener) getActivity();
                Bundle bundle = new Bundle();
                bundle.putParcelable("menu",menu);
                bundle.putString("title","Edit menu");
                onOptionsMenuDialogListener.onSelectEditMenuOptionDialog(bundle);
            }

        }
    };



    ValueEventListener removingMenu = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            MenuItemDao menuItemDao = dataSnapshot.getValue(MenuItemDao.class);
            Toast.makeText(getContext(),menuItemDao.getName()+" deleted",Toast.LENGTH_LONG).show();
            dataSnapshot.getRef().removeValue();
            dismiss();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getContext(),"can't delete",Toast.LENGTH_LONG).show();
        }
    };

    public interface OnOptionsMenuDialogListener{
        void onSelectEditMenuOptionDialog(Bundle bundle);
    }
}
