package com.example.carsales.Activities;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.carsales.DbTables.DbCreation;
import com.example.carsales.Adapters.GalleryAdapter;
import com.example.carsales.R;

import java.util.ArrayList;

public class CarDetails extends AppCompatActivity {

    GalleryAdapter galleryAdapter;
    TextView tv_carName;
    GridView gv;
    LinearLayout ll_contact;
    String carId;
    String carName;
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        initViews();
        fetchIntent(getIntent());
        setData();

        ll_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(carName);

            }
        });
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
    }


    private void setData() {
        DbCreation obj = new DbCreation(CarDetails.this);
        ArrayList<String> carUrl = obj.getCarDetails(carId);
        tv_carName.setText("Car Name: " + carName);
        for (int i = 0; i < carUrl.size(); i++) {

            mArrayUri.add(Uri.parse(carUrl.get(i)));

        }
        galleryAdapter = new GalleryAdapter(getApplicationContext(), mArrayUri, true, carId);
        gv.setAdapter(galleryAdapter);
        gv.setVerticalSpacing(gv.getHorizontalSpacing());
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gv
                .getLayoutParams();
        mlp.setMargins(0, gv.getHorizontalSpacing(), 0, 0);
    }

    private void initViews() {
        tv_carName = (TextView) findViewById(R.id.tv_carName);
        gv = (GridView) findViewById(R.id.gv);
        ll_contact = (LinearLayout) findViewById(R.id.ll_contact);
    }
}
