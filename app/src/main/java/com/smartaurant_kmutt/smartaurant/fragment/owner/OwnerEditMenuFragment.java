package com.smartaurant_kmutt.smartaurant.fragment.owner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.internal.Util;


@SuppressWarnings("unused")
public class OwnerEditMenuFragment extends Fragment {
    ArrayList<String> typeList = new ArrayList<>();
    String title;
    String type;
    String menuName;
    String menuPriceText;
    String menuPromotion;
    String menuType;
    String allergen;
    boolean recommended;
    boolean checkSetImageView = false;
    int maxMenuId;
    MenuItemDao menuItemDao;
    EditText etName;
    EditText etPrice;
    EditText etPromotion;
    EditText etTest1, etTest2;
    CheckBox cbRecommended;
    CheckBox cbBean;
    CheckBox cbEgg;
    CheckBox cbMilk;
    CheckBox cbSeaFood;
    BetterSpinner snType;
    Button btSave;
    Activity activity;
    ImageView imageView;
    StorageReference storageReference;
    FrameLayout frameLayout;
    Uri imageUriLocal;
    Uri imageUriWeb;

    public OwnerEditMenuFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OwnerEditMenuFragment newInstance(Bundle bundle) {
        OwnerEditMenuFragment fragment = new OwnerEditMenuFragment();
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
        menuItemDao = bundle.getParcelable("menu");
        title = bundle.getString("title");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner_edit_menu, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        initEditText(rootView);
        initCheckBox(rootView);
        initSpinner(rootView);
        initImageView(rootView);
        btSave = rootView.findViewById(R.id.btSave);
        frameLayout = rootView.findViewById(R.id.frameLayout);
        btSave.setOnClickListener(onClickListener);
        activity = getActivity();

        if (menuItemDao != null) {
            initForm();

        } else {
            activity.setTitle("Add menu");
            btSave.setText("Add");
        }

        activity = getActivity();
    }

    private void initImageView(View rootView) {
        imageView = rootView.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
    }

    private void initForm() {
        title = title + ": " + menuItemDao.getName();
        String imageUri = menuItemDao.getImageUri();
        String name = menuItemDao.getName();
        String price = menuItemDao.getPrice() + "";
        String promotion = menuItemDao.getPromotion() + "";
        String type = menuItemDao.getType() + "";
        boolean recommended = menuItemDao.isRecommended();
        String allergen = menuItemDao.getAllergen();

        getActivity().setTitle(title);
        setImage(imageUri);
        etName.setText(name);
        etName.setEnabled(false);
        etPrice.setText(price);
        etPromotion.setText(promotion);
        snType.setText(type);
        cbRecommended.setChecked(recommended);
        if (allergen.contains("bean"))
            cbBean.setChecked(true);
        if (allergen.contains("egg"))
            cbEgg.setChecked(true);
        if (allergen.contains("seafood"))
            cbSeaFood.setChecked(true);
        if (allergen.contains("milk"))
            cbMilk.setChecked(true);
        btSave.setText("Save");
        checkSetImageView = true;
    }

    private void initSpinner(View rootView) {
        snType = rootView.findViewById(R.id.snType);
        typeList = new ArrayList<>(Arrays.asList("Appetizer", "Main dish", "Dessert", "Drinks"));
        ArrayAdapter roleAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, typeList);
        snType.setAdapter(roleAdapter);
        snType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuType = typeList.get(position);
            }
        });
    }

    private void initCheckBox(View rootView) {
        cbBean = rootView.findViewById(R.id.cbBean);
        cbEgg = rootView.findViewById(R.id.cbEgg);
        cbMilk = rootView.findViewById(R.id.cbMilk);
        cbSeaFood = rootView.findViewById(R.id.cbSeaFood);
        cbRecommended = rootView.findViewById(R.id.cbRecommended);
    }

    private void initEditText(View rootView) {
        etTest1 = rootView.findViewById(R.id.etTest1);
        etTest2 = rootView.findViewById(R.id.etTest2);
        etName = rootView.findViewById(R.id.etName);
        etPrice = rootView.findViewById(R.id.etPrice);
        etPromotion = rootView.findViewById(R.id.etPromotion);
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

    boolean checkText(String text) {
        if (!text.equals("")) {
            return true;
        }
        return false;
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    void showText(String text) {
        Toast.makeText(Contextor.getInstance().getContext(), text, Toast.LENGTH_LONG).show();
    }

    public void setImage(String url) {

        RequestOptions requestOptions = RequestOptions
                .placeholderOf(R.drawable.loading)
                .error(android.R.drawable.ic_menu_gallery);
        Glide.with(getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .into(imageView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUriLocal = data.getData();
            checkSetImageView = true;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), imageUriLocal);
                // Log.d(TAG, String.valueOf(bitmap));
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void uploadImageAndSetDatabase(Uri imageUriLocal) {
        StorageReference imageRef = storageReference.child("images/" + imageUriLocal.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(imageUriLocal);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                MyUtil.showText("on failure");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageUriWeb = taskSnapshot.getDownloadUrl();
                if (menuItemDao == null) {
                    MenuItemDao menuItemDaoOnSuccessUpdate = new MenuItemDao();
                    menuItemDaoOnSuccessUpdate.setEnable(true);
                    menuItemDaoOnSuccessUpdate = getMenuItemDaoFormInput(menuItemDaoOnSuccessUpdate);
                    menuItemDaoOnSuccessUpdate.setImageUri(imageUriWeb.toString());
                    menuItemDao = menuItemDaoOnSuccessUpdate;
                } else {
                    menuItemDao = getMenuItemDaoFormInput(menuItemDao);
                    menuItemDao.setImageUri(imageUriWeb.toString());
                }
                Map<String, Object> updateMenu = new HashMap<>();
                updateMenu.put("menu/" + menuItemDao.getName(), menuItemDao);
                DatabaseReference databaseReference = UtilDatabase.getDatabase();
                databaseReference.updateChildren(updateMenu).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            activity.finish();
                        }
                    }
                });
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btSave) {
                if (checkRequireInput()) {
//                    Log.e("menu edit", "name = " + menuName + " price = " + menuPriceText + " promotion = " + menuPromotion + " type = " + menuType + " allergen = " + allergen);
                    if (imageUriLocal != null) {
                        frameLayout.setVisibility(View.VISIBLE);
                        uploadImageAndSetDatabase(imageUriLocal);
                    } else if (imageUriLocal == null) {
                        frameLayout.setVisibility(View.VISIBLE);
                        menuItemDao = getMenuItemDaoFormInput(menuItemDao);
                        Map<String, Object> updateMenu = new HashMap<>();
                        updateMenu.put("menu/" + menuItemDao.getName(), menuItemDao);
                        DatabaseReference databaseReference = UtilDatabase.getDatabase();
                        databaseReference.updateChildren(updateMenu).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    activity.finish();
                                }
                            }
                        });
                    }
                }
            }
        }
    };

    private boolean checkRequireInput() {
        menuName = etName.getText().toString().trim();
        menuPriceText = etPrice.getText().toString().trim();
        menuPromotion = etPromotion.getText().toString().trim();
        menuType = snType.getText().toString().trim();
        if (menuName.equals(""))
            return false;
        if (menuPriceText.equals(""))
            return false;
        if (menuPromotion.equals(""))
            return false;
        if (menuType.equals(""))
            return false;
        if (!checkSetImageView)
            return false;
        return true;
    }

    @NonNull
    private MenuItemDao getMenuItemDaoFormInput(MenuItemDao menuItemDao) {
        menuName = etName.getText().toString().trim();
        menuPriceText = etPrice.getText().toString().trim();
        menuPromotion = etPromotion.getText().toString().trim();
        menuType = snType.getText().toString().trim();
        recommended = cbRecommended.isChecked();
        String allergen = "";
        if (cbBean.isChecked())
            allergen += "bean ";
        if (cbEgg.isChecked())
            allergen += "egg ";
        if (cbMilk.isChecked())
            allergen += "milk ";
        if (cbSeaFood.isChecked())
            allergen += "seafood ";

        menuItemDao.setName(menuName);
        menuItemDao.setPrice(Float.parseFloat(menuPriceText));
        menuItemDao.setPromotion(Float.parseFloat(menuPromotion));
        menuItemDao.setType(menuType);
        menuItemDao.setRecommended(recommended);
        if (menuItemDao.isEnable())
            menuItemDao.setEnableRecommended(recommended);
        else
            menuItemDao.setEnableRecommended(false);

        if (Float.parseFloat(menuPromotion) > 0 && menuItemDao.isEnable())
            menuItemDao.setEnablePromotion(true);
        else
            menuItemDao.setEnablePromotion(false);

        if (menuItemDao.isEnable()) {
            switch (menuType) {
                case "Appetizer": {
                    menuItemDao.setEnableAppetizer(true);
                    menuItemDao.setEnableMainDish(false);
                    menuItemDao.setEnableDessert(false);
                    menuItemDao.setEnableDrinks(false);
                    break;
                }
                case "Main dish": {
                    menuItemDao.setEnableAppetizer(false);
                    menuItemDao.setEnableMainDish(true);
                    menuItemDao.setEnableDessert(false);
                    menuItemDao.setEnableDrinks(false);
                    break;
                }
                case "Dessert": {
                    menuItemDao.setEnableAppetizer(false);
                    menuItemDao.setEnableMainDish(false);
                    menuItemDao.setEnableDessert(true);
                    menuItemDao.setEnableDrinks(false);
                    break;
                }
                case "Drinks": {
                    menuItemDao.setEnableAppetizer(false);
                    menuItemDao.setEnableMainDish(false);
                    menuItemDao.setEnableDessert(false);
                    menuItemDao.setEnableDrinks(true);
                    break;
                }
            }
        }
        else{
            menuItemDao.setEnableAppetizer(false);
            menuItemDao.setEnableMainDish(false);
            menuItemDao.setEnableDessert(false);
            menuItemDao.setEnableDrinks(false);
        }
        menuItemDao.setAllergen(allergen);
        return menuItemDao;
    }

    OnCompleteListener onCompleteListener = new OnCompleteListener() {
        @Override
        public void onComplete(@NonNull Task task) {
            if (task.isSuccessful()) {
                activity.finish();
            }
        }
    };


}
