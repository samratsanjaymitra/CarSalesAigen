package com.example.carsales.Activities;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carsales.Adapters.GalleryAdapter;
import com.example.carsales.DbTables.DbCreation;
import com.example.carsales.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CreateAdActiity extends AppCompatActivity {

    private Button btn_submit, btn_image;

    EditText et_name;
    EditText et_year;
    EditText et_price;
    EditText et_description;

    int PICK_IMAGE_MULTIPLE = 1, CAMERA = 2;
    String imageEncoded;
    List<String> imagesEncodedList;
    private GridView gvGallery;
    private GalleryAdapter galleryAdapter;
    private static final String IMAGE_DIRECTORY = "/sam";
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);
        initView();

        selectImage();
        submitImage();


    }

    private void submitImage() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_name.getText().toString().equals("")) {
                    Toast.makeText(CreateAdActiity.this, "Please enter carName", Toast.LENGTH_SHORT).show();

                } else if (et_price.getText().toString().equals("")) {
                    Toast.makeText(CreateAdActiity.this, "Please enter price", Toast.LENGTH_SHORT).show();

                } else if (et_year.getText().toString().equals("")) {
                    Toast.makeText(CreateAdActiity.this, "Please enter year", Toast.LENGTH_SHORT).show();

                } else if (et_description.getText().toString().equals("")) {
                    Toast.makeText(CreateAdActiity.this, "Please enter some description for car", Toast.LENGTH_SHORT).show();

                } else {
                    String carName = et_name.getText().toString();
                    String price = et_price.getText().toString();
                    String year = et_year.getText().toString();
                    String description = et_description.getText().toString();


                    if (mArrayUri.size() == 0) {
                        Toast.makeText(CreateAdActiity.this, "Please select Image", Toast.LENGTH_SHORT).show();

                    } else {
                        DbCreation obj = new DbCreation(CreateAdActiity.this);
                        int id = obj.getCarId() + 1;
                        for (int i = 0; i < mArrayUri.size(); i++) {
                            obj.addCarDetails(id, carName, String.valueOf(mArrayUri.get(i)), price, year, description);

                        }
                        Intent intent = new Intent(CreateAdActiity.this, HomeNewActivity.class);
                        startActivity(intent);
                        Toast.makeText(CreateAdActiity.this, "Success", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }

    private void selectImage() {
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();

            }
        });

    }

    private void initView() {
        btn_image = findViewById(R.id.btn_image);
        gvGallery = (GridView) findViewById(R.id.gv);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_name = (EditText) findViewById(R.id.et_name);
        et_year = (EditText) findViewById(R.id.et_year);
        et_price = (EditText) findViewById(R.id.et_price);
        et_description = (EditText) findViewById(R.id.et_description);

    }

    private void showPictureDialog() {

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);

    }

    private void takePhotoFromCamera() {
        try {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (resultCode == this.RESULT_CANCELED) {
                return;
            }


            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {


                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();

                    String wholeID = DocumentsContract.getDocumentId(mImageUri);

                    String id = wholeID.split(":")[1];

                    String path = mImageUri.getPath();

                    String sel = MediaStore.Images.Media._ID + "=?";

                    Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            filePathColumn, sel, new String[]{id}, null);

                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    Bitmap bitmap = (BitmapFactory.decodeFile(imageEncoded));
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path1 = MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "Title", null);
                    Uri uri = Uri.parse(path1);
                    Log.e("ssf", uri.toString());
                    cursor.close();

                    mArrayUri.add(uri);
                    galleryAdapter = new GalleryAdapter(getApplicationContext(), mArrayUri, false, "");
                    gvGallery.setAdapter(galleryAdapter);
                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                            .getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            String wholeID = DocumentsContract.getDocumentId(uri);

                            String id = wholeID.split(":")[1];

                            String path = uri.getPath();

                            String sel = MediaStore.Images.Media._ID + "=?";

                            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    filePathColumn, sel, new String[]{id}, null);

                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            Bitmap bitmap = (BitmapFactory.decodeFile(imageEncoded));
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                            String path1 = MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "Title", null);
                            Uri uriNew = Uri.parse(path1);
                            mArrayUri.add(uriNew);
                            cursor.close();

                            galleryAdapter = new GalleryAdapter(getApplicationContext(), mArrayUri, false, "");
                            gvGallery.setAdapter(galleryAdapter);
                            gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                                    .getLayoutParams();
                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            }
            if (requestCode == CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                mArrayUri.add(saveImage(thumbnail));
                galleryAdapter = new GalleryAdapter(getApplicationContext(), mArrayUri, false, "");
                gvGallery.setAdapter(galleryAdapter);
                gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                        .getLayoutParams();
                mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);
                Toast.makeText(CreateAdActiity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public Uri saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Uri path = Uri.parse("");
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            path = Uri.parse(f.getPath());
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return path;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return path;
    }
}
