package com.example.carsales.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carsales.Adapters.ImageAdapter;
import com.example.carsales.DbTables.DbCreation;
import com.example.carsales.Adapters.GalleryAdapter;
import com.example.carsales.POJO.ImagePOJO;
import com.example.carsales.R;

import java.util.ArrayList;

public class CarDetails extends AppCompatActivity {

    GalleryAdapter galleryAdapter;
    TextView tv_carName, tv_carPrice, tv_carYear, tv_description;
    ViewPager vp_cars;
    LinearLayout ll_contact;
    String carId;
    String carName, type;
    Button btn_image, btn_submit;
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    EditText et_description, et_year, et_price, et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        initViews();
        fetchIntent(getIntent());
        setData();
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(carName);

            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    private void submit() {
        if (et_name.getText().toString().equals("")) {
            Toast.makeText(CarDetails.this, "Please enter carName", Toast.LENGTH_SHORT).show();

        } else if (et_price.getText().toString().equals("")) {
            Toast.makeText(CarDetails.this, "Please enter price", Toast.LENGTH_SHORT).show();

        } else if (et_year.getText().toString().equals("")) {
            Toast.makeText(CarDetails.this, "Please enter year", Toast.LENGTH_SHORT).show();

        } else if (et_description.getText().toString().equals("")) {
            Toast.makeText(CarDetails.this, "Please enter some description for car", Toast.LENGTH_SHORT).show();

        } else {
            String carName = et_name.getText().toString();
            String price = et_price.getText().toString();
            String year = et_year.getText().toString();
            String description = et_description.getText().toString();


                DbCreation obj = new DbCreation(CarDetails.this);


                    obj.editCarDetails(carId, carName, price, year, description);
                Intent intent = new Intent(CarDetails.this, HomeNewActivity.class);
                startActivity(intent);
                Toast.makeText(CarDetails.this, "Success", Toast.LENGTH_SHORT).show();

        }

    }

    private void showDialog(String carName) {
        {
            try {
                final Dialog dialog = new Dialog(CarDetails.this);
                final String car = carName;
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_email);
                dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
                dialog.setCancelable(false);

                final EditText et_msg = (EditText) dialog.findViewById(R.id.et_msg);
                Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
                Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);


                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"developer@aigen.tech"});
                        email.putExtra(Intent.EXTRA_SUBJECT, "Message regarding " + car);
                        email.putExtra(Intent.EXTRA_TEXT, et_msg.getText().toString());

                        //need this to prompts email client only
                        email.setType("message/rfc822");

                        startActivity(Intent.createChooser(email, "Choose an Email client :"));
                        dialog.dismiss();

                    }
                });

                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void fetchIntent(Intent intent) {
        carId = intent.getStringExtra("id");
        carName = intent.getStringExtra("name");
        type = intent.getStringExtra("type");
    }


    private void setData() {
        DbCreation obj = new DbCreation(CarDetails.this);
        ArrayList<String> carUrl = obj.getCarImage(carId);
        ArrayList<ImagePOJO> carDetails = obj.getCarDescription(carId);

        if (!type.equalsIgnoreCase("edit")) {
            tv_carName.setText(carDetails.get(0).getCarName());
            tv_carPrice.setText(carDetails.get(0).getPrice());
            tv_carYear.setText(carDetails.get(0).getYear());
            tv_description.setText(carDetails.get(0).getDescription());
        } else {
            tv_carName.setVisibility(View.GONE);
            tv_carPrice.setVisibility(View.GONE);
            tv_carYear.setVisibility(View.GONE);
            tv_description.setVisibility(View.GONE);
            btn_image.setVisibility(View.GONE);

            et_name.setVisibility(View.VISIBLE);
            et_price.setVisibility(View.VISIBLE);
            et_description.setVisibility(View.VISIBLE);
            et_year.setVisibility(View.VISIBLE);
            btn_submit.setVisibility(View.VISIBLE);

            et_name.setText(carDetails.get(0).getCarName());
            et_price.setText(carDetails.get(0).getPrice());
            et_year.setText(carDetails.get(0).getYear());
            et_description.setText(carDetails.get(0).getDescription());

        }


        for (int i = 0; i < carUrl.size(); i++) {

            mArrayUri.add(Uri.parse(carUrl.get(i)));

        }
        ImageAdapter imageAdapter = new ImageAdapter(this, mArrayUri, carDetails.get(0).getId());
        vp_cars.setAdapter(imageAdapter);
    }

    private void initViews() {
        tv_carName = (TextView) findViewById(R.id.tv_carName);
        vp_cars = (ViewPager) findViewById(R.id.vp_cars);
        tv_carPrice = (TextView) findViewById(R.id.tv_carPrice);
        tv_carYear = (TextView) findViewById(R.id.tv_carYear);
        tv_description = (TextView) findViewById(R.id.tv_description);
        et_description = (EditText) findViewById(R.id.et_description);
        et_name = (EditText) findViewById(R.id.et_name);
        et_price = (EditText) findViewById(R.id.et_price);
        et_year = (EditText) findViewById(R.id.et_year);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_image = (Button) findViewById(R.id.btn_image);

    }

}
