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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.dao.StaffItemDao;

import java.util.ArrayList;
import java.util.Arrays;


@SuppressWarnings("unused")
public class OptionsStaffDialog extends DialogFragment {
    StaffItemDao staffItemDao;
    ListView listMenu;
    TextView tvTitle;
    ArrayList<String> optionsList;

    public OptionsStaffDialog() {
        super();
    }

    @SuppressWarnings("unused")
    public static OptionsStaffDialog newInstance(Bundle bundle) {
        OptionsStaffDialog fragment = new OptionsStaffDialog();
        Bundle args = new Bundle();
        args.putParcelable("bundle",bundle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(savedInstanceState);
        staffItemDao = getArguments().getBundle("bundle").getParcelable("staffItemDao");
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
        tvTitle.setText(staffItemDao.getName());

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
            DatabaseReference staffDatabase = FirebaseDatabase.getInstance().getReference().child("staff/"+ staffItemDao.getId());
            if(optionsList.get(position).equals("Delete")){
                staffDatabase.removeValue();
                dismiss();
            }
            else if (optionsList.get(position).equals("Edit")){
                dismiss();
                OnOptionsStaffDialogListener onOptionsMenuDialogListener = (OnOptionsStaffDialogListener) getActivity();
                Bundle bundle = new Bundle();
                bundle.putParcelable("staffItemDao", staffItemDao);
                bundle.putString("title","Edit staff");
                onOptionsMenuDialogListener.onSelectEditStaffOptionDialog(bundle);
            }
        }
    };


    public interface OnOptionsStaffDialogListener{
        void onSelectEditStaffOptionDialog(Bundle bundle);
    }
}
