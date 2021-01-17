package users.com.mealzip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import users.com.mealzip.Helper.PrefsApplication;
import users.com.mealzip.Helper.helper;
import users.com.mealzip.Interface.Retroclient;

public class AdditemActivity extends AppCompatActivity {
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> platesize = new ArrayList<>();
    SpinnerDialog spinnerDialog;
    SpinnerDialog spinnerDialog_size;
    Context mcontext;
    TextView et_category, et_type, tvpricefinal, tvsizefinal, tvpricefinal2, tvsizefinal2;
    String selected_cat, selected_size;
    Dialog dialog;
    Dialog dialog2;
    Button btn_photo, btn_upload;
    ImageView imageView, addrates;
    Bitmap bitmap;
    private static final int MY_PERMISSIONS_REQUEST = 100;
    StorageReference mStorageRef;
    Uri img_uri;
    String imgUrl;
    boolean type;
    EditText foodname;
    String final_price, final_size;
    LinearLayout linearLayout, ll2;
    String temp;
    helper pb;
    helper.PbFn obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        pb = new helper(this);
        obj = pb.new PbFn();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        dialog = new Dialog(AdditemActivity.this);
        dialog2 = new Dialog(AdditemActivity.this);
        et_category = findViewById(R.id.etcategory);
        et_type = findViewById(R.id.ettype);
        imageView = findViewById(R.id.img);
        btn_photo = findViewById(R.id.add_img);
        btn_upload = findViewById(R.id.button4);
        foodname = findViewById(R.id.etfoodname);
        addrates = findViewById(R.id.img_addrates);
        tvpricefinal = findViewById(R.id.tv_price);
        tvsizefinal = findViewById(R.id.tv_platesize1);
        tvpricefinal2 = findViewById(R.id.tv_price2);
        tvsizefinal2 = findViewById(R.id.tv_platesize2);
        linearLayout = findViewById(R.id.linear_lay_one);
        ll2 = findViewById(R.id.linear_lay_two);

        //To get read storage permissions
        if (ContextCompat.checkSelfPermission(AdditemActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AdditemActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
        }

        btn_photo.setOnClickListener(v -> {

            Intent myfileintent = new Intent();
            myfileintent.setAction(Intent.ACTION_GET_CONTENT);
            myfileintent.setType("image/*");
            // myfileintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(myfileintent, "image"), 10);
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.showPb();
                upload_img();

                //add_item();
            }
        });

        et_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                dialog_type();
            }
        });

        addrates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.show();
                dialog_rates();
            }
        });


        et_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_category();
                spinnerDialog = new SpinnerDialog(AdditemActivity.this, category, "Select category", "");// With No Animation
                //spinnerDialog=new SpinnerDialog(AdditemActivity.this,category,"Select category",R.style.DialogAnimations_SmileWindow,"Close Button Text");// With Animation

                spinnerDialog.setCancellable(false);
                spinnerDialog.setShowKeyboard(false);// for open keyboard by default
                // spinnerDialog.setItemColor(getResources().getColor(R.color.colorPrimary));
                spinnerDialog.showSpinerDialog();
                // category.clear();
                spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                        selected_cat = item;
                        category.clear();
                        AdditemActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                et_category.setText(selected_cat);
                                et_category.setTextColor(getResources().getColor(R.color.black));
                            }
                        });
                    }
                });

            }
        });

    }

    private void upload_img() {
        final StorageReference ImageName = mStorageRef.child("Fooditems").child("img" + img_uri.getLastPathSegment());
        ImageName.putFile(img_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imgUrl = String.valueOf(uri);
                        //  Toast.makeText(AdditemActivity.this, "Url is "+imgUrl, Toast.LENGTH_LONG).show();
                        add_item();

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void add_category() {
        Call<ResponseBody> call = Retroclient
                .getInstance()
                .getapi()
                .getcategory();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        JSONArray jsonArray = new JSONArray(str);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String foodcategory = jsonObject.getString("category");
                            category.add(foodcategory);
                        }
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void add_item() {
        String category = et_category.getText().toString();
        String imgurl = imgUrl;
        String email = Prefs.getString("email", "");
        String veg;
        if (type)
            veg = "true";
        else
            veg = "false";
        String itemname = foodname.getText().toString();
        String price = tvpricefinal.getText().toString();
        String size = tvsizefinal.getText().toString();
        String price2 = tvpricefinal2.getText().toString();
        String size2 = tvsizefinal2.getText().toString();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("imgUrl", imgurl);
        jsonObject.addProperty("category", category);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("isveg", veg);
        jsonObject.addProperty("itemName", itemname);
        JsonArray array = new JsonArray();
        JsonObject data = new JsonObject();
        data.addProperty("price", price);
        data.addProperty("size", size);
        array.add(data);
        //jsonObject.add("price",data);
        if (!(TextUtils.isEmpty(price2) && TextUtils.isEmpty(size2))) {
            JsonObject data1 = new JsonObject();
            data1.addProperty("price", price2);
            data1.addProperty("size", size2);
            array.add(data1);
        }
        jsonObject.add("price", array);
        Log.d("TUINNG", "data is : " + jsonObject);
        Call<ResponseBody> call = Retroclient
                .getInstance()
                .getapi()
                .additem(jsonObject, "Bearer " + Prefs.getString("access_token", ""));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                obj.hidePb();
                if (response.isSuccessful()) {
                    Toasty.success(AdditemActivity.this, "Item added successfully", Toast.LENGTH_LONG, true).show();
                    startActivity(new Intent(AdditemActivity.this,MainActivity.class));
                    finish();
                } else if (response.code() == 401) {
                    String token1 = Prefs.getString("refresh_token", "");
                    PrefsApplication prefsApplication = new PrefsApplication();
                    prefsApplication.refreshToken(token1);
                    add_item();
                } else {
                    String str = null;
                    try {
                        str = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toasty.normal(AdditemActivity.this, str, Toast.LENGTH_LONG).show();
                }
                //  Toast.makeText(AdditemActivity.this, "res_code: "+response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                obj.hidePb();
                Toasty.normal(AdditemActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Uri uri;
        if (resultCode == RESULT_OK && requestCode == 10 && data != null) {

            uri = data.getData();
            img_uri = uri;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(AdditemActivity.this.getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
                btn_photo.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void add_size() {
        platesize.add("Full");
        platesize.add("Half");
        platesize.add("None");
    }

    private void dialog_rates() {
        dialog2.setContentView(R.layout.platesize_dialog);
        dialog2.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(AdditemActivity.this, R.drawable.bg2));
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.setCancelable(false);

        final EditText et_price;
        final TextView tv_size;
        Button addplate, cancel;

        et_price = dialog2.findViewById(R.id.price);
        tv_size = dialog2.findViewById(R.id.tv_platesize);
        addplate = dialog2.findViewById(R.id.btn_addrate);
        cancel = dialog2.findViewById(R.id.btn_cancel);

        tv_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_size();
                spinnerDialog_size = new SpinnerDialog(AdditemActivity.this, platesize, "Select category", null);// With No Animation
                //spinnerDialog=new SpinnerDialog(AdditemActivity.this,category,"Select category",R.style.DialogAnimations_SmileWindow,"Close Button Text");// With Animation

                spinnerDialog_size.setCancellable(false);
                spinnerDialog_size.setShowKeyboard(false);// for open keyboard by default
                spinnerDialog_size.showSpinerDialog();
                spinnerDialog_size.bindOnSpinerListener(new OnSpinerItemClick() {

                    @Override
                    public void onClick(String item, int position) {
                        platesize.clear();
                        selected_size = item;
                        boolean selected = true;
                        AdditemActivity.this.runOnUiThread(() -> {
                            tv_size.setText(selected_size);
                            tv_size.setTextColor(getResources().getColor(R.color.black));
                            if (!TextUtils.isEmpty(temp) && !temp.equalsIgnoreCase(selected_size)) {
                                tvsizefinal2.setText(tv_size.getText().toString());
                            } else {
                                tvsizefinal.setText(tv_size.getText().toString());
                            }
                            final_size = tv_size.getText().toString();
                        });
                    }
                });
            }
        });

        final String price;
        price = et_price.getText().toString();
        addplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setpricelist();
                // Toast.makeText(AdditemActivity.this, et_price.getText().toString(), Toast.LENGTH_SHORT).show();
                if (!et_price.getText().toString().isEmpty()) {

                    dialog2.dismiss();
                    if (!TextUtils.isEmpty(temp) && !temp.equalsIgnoreCase(selected_size)) {

                        ll2.setVisibility(View.VISIBLE);
                        tvpricefinal2.setText(et_price.getText().toString());
                    } else {
                        temp = selected_size;
                        linearLayout.setVisibility(View.VISIBLE);
                        tvpricefinal.setText(et_price.getText().toString());
                    }
                } else {
                    et_price.setError("Price must be provided");
                    et_price.requestFocus();
                }

            }
        });
        final_price = price;
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
    }

    private void setpricelist() {
    }


    private void dialog_type() {
        dialog.setContentView(R.layout.type_dialog);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(AdditemActivity.this, R.drawable.bg2));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        final TextView et_veg, et_nonveg;
        Button done_type;
        et_veg = dialog.findViewById(R.id.veg);
        et_nonveg = dialog.findViewById(R.id.non_veg);
        done_type = dialog.findViewById(R.id.button4);
        final String type_final;
        Drawable drawable_final;
        et_veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String type = "Vegetarian";
                // Drawable drawable= ContextCompat.getDrawable(mcontext,R.drawable.ic_veg);
                et_veg.setBackground(ContextCompat.getDrawable(AdditemActivity.this, R.drawable.buttonbg1));

                et_type.setText("Veg");
                et_type.setCompoundDrawables(ContextCompat.getDrawable(AdditemActivity.this, R.drawable.ic_veg), null, null, null);
                et_type.setCompoundDrawablePadding(10);
                type = true;
            }
        });
        et_nonveg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_nonveg.setBackground(ContextCompat.getDrawable(AdditemActivity.this, R.drawable.buttonbg1));

                et_type.setText("Non-veg");
                et_type.setCompoundDrawables(ContextCompat.getDrawable(AdditemActivity.this, R.drawable.ic_nonveg), null, null, null);
                et_type.setCompoundDrawablePadding(10);
            }
        });

        done_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
