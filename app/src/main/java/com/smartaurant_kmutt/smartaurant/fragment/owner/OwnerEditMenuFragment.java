package com.smartaurant_kmutt.smartaurant.fragment.owner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.io.IOException;
import java.util.Locale;


@SuppressWarnings("unused")
public class OwnerEditMenuFragment extends Fragment {
    String title;
    int maxMenuId;
    MenuItemDao menuItemDao;
    String menuName;
    String menuPriceCheck;
    EditText etName;
    EditText etPrice;
    Button btSave;
    Activity activity;
    ImageView imageView;
    StorageReference storageReference;
    FrameLayout frameLayout;
    Uri imageUriLocal;
    Uri imageUriWeb;
    EditText etTest1,etTest2;
    public OwnerEditMenuFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OwnerEditMenuFragment newInstance(Bundle bundle) {
        OwnerEditMenuFragment fragment = new OwnerEditMenuFragment();
        Bundle args = new Bundle();
        args.putBundle("bundle",bundle);
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
        etName = rootView.findViewById(R.id.etName);
        etPrice = rootView.findViewById(R.id.etPrice);
        btSave = rootView.findViewById(R.id.btSave);
        imageView = rootView.findViewById(R.id.imageView);
        frameLayout = rootView.findViewById(R.id.frameLayout);
        etTest1 = rootView.findViewById(R.id.etTest1);
        etTest2 = rootView.findViewById(R.id.etTest2);

        btSave.setOnClickListener(onClickListener);
        activity = getActivity();

        if(menuItemDao != null){
            getActivity().setTitle(title+": "+menuItemDao.getName());
            etPrice.setText(String.valueOf(menuItemDao.getPrice()));
            etName.setText(menuItemDao.getName());
            btSave.setText("Save");
            setImage(menuItemDao.getImageUri());
            etTest1.setText(menuItemDao.getImageUri());

        }else{
            activity.setTitle(title);
            btSave.setText("Add");
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
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

    boolean checkText(String text){
        if(!text.equals("")){
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

    void showText(String text){
        Toast.makeText(Contextor.getInstance().getContext(),text,Toast.LENGTH_LONG).show();
    }

    public void setImage(String url){

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
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), imageUriLocal);
                // Log.d(TAG, String.valueOf(bitmap));
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void uploadImageAndSetDatabase(Uri imageUriLocal){
        StorageReference riversRef = storageReference.child("images/"+imageUriLocal.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(imageUriLocal);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                MyUtil.showText("on failure");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                imageUriWeb = taskSnapshot.getDownloadUrl();

                if(menuItemDao == null){
                    float menuPrice = Float.parseFloat(menuPriceCheck);
                    String imageUri = imageUriWeb.toString();
                    MenuItemDao menuItemDao = new MenuItemDao(menuName,menuPrice,imageUri,true);
                    DatabaseReference menuDatabase = UtilDatabase.getMenu();
                    menuDatabase.child(menuItemDao.getName()).setValue(menuItemDao).addOnCompleteListener(onCompleteListener);
                }else{
                    DatabaseReference menuDatabase = UtilDatabase.getMenu();
                    menuItemDao.setName(menuName);
                    menuItemDao.setPrice(Float.parseFloat(menuPriceCheck));
                    menuItemDao.setImageUri(imageUriWeb.toString());
                    menuDatabase.child(menuItemDao.getName()).setValue(menuItemDao).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
//                                activity.finish();
                                etTest2.setText(String.valueOf(imageUriWeb));
                            }
                        }
                    });
                }
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v==btSave){
                menuName = etName.getText().toString().trim();
                menuPriceCheck = etPrice.getText().toString().trim();
                if(checkText(menuName)&& checkText(menuPriceCheck)&&imageUriLocal!=null){
                    boolean b = checkText(menuName)&& checkText(menuPriceCheck)&&imageUriLocal!=null;
                    MyUtil.showText(String.valueOf(b));
                    frameLayout.setVisibility(View.VISIBLE);
                    uploadImageAndSetDatabase(imageUriLocal);
                }

                else if(checkText(menuName)&& checkText(menuPriceCheck)&&imageUriLocal==null){
                    frameLayout.setVisibility(View.VISIBLE);
                    DatabaseReference menuDatabase = UtilDatabase.getMenu();
                    menuItemDao.setName(menuName);
                    menuItemDao.setPrice(Float.parseFloat(menuPriceCheck));
                    menuDatabase.child(menuItemDao.getName()).setValue(menuItemDao).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            activity.finish();
                        }
                    });
                }
            }
            else if (v==imageView){

            }
        }
    };

    OnCompleteListener onCompleteListener = new OnCompleteListener() {
        @Override
        public void onComplete(@NonNull Task task) {
            if(task.isSuccessful()){
                activity.finish();
            }
        }
    };



}
