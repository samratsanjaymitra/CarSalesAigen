package com.example.carsales.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.carsales.DbTables.DbCreation;
import com.example.carsales.POJO.LoginPOJO;
import com.example.carsales.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText et_Name, et_Password;
    ProgressBar pb_progress;
    Button btn_Login;
    ArrayList<LoginPOJO> loginDetails;
    String userName = "";
    String password = "";
    private int REQUEST_CODE = 0x12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        userLogin();

    }

     private void userLogin() {
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean loginFlag = false;
                DbCreation obj = new DbCreation(LoginActivity.this);
                obj.tableCarDetailsCreation();
                loginDetails = obj.getLoginDetails();
                getLoginValues();
                for (int i = 0; i < loginDetails.size(); i++) {
                    if (userName.equalsIgnoreCase(loginDetails.get(i).getEmail()) && password.equalsIgnoreCase(loginDetails.get(i).getPassword())) {
                        loginFlag = true;
                    }

                }
                if (loginFlag == true) {
                    obj.addCarDetails(101, "Car1", "R.drawable.car1","200000","2005","This is special car.");
                    obj.addCarDetails(102, "Car2", "R.drawable.car2","100000","2009","Dream Car");
                    obj.addCarDetails(102, "Car2", "R.drawable.car1"," ","","");
                    SharedPreferences sharedPreferences = getSharedPreferences("loginState", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("state", "2");
                    editor.commit();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeNewActivity.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(LoginActivity.this, "Credentials are incorrect", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void getLoginValues() {
        userName = et_Name.getText().toString();
        password = et_Password.getText().toString();
    }

    private void initViews() {
        et_Name = (EditText) findViewById(R.id.et_UserName);
        et_Password = (EditText) findViewById(R.id.et_UserPassword);
        pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
        btn_Login = (Button) findViewById(R.id.btn_Login);

    }
}
