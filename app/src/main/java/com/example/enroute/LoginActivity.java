package com.example.enroute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.enroute.Constants.Role;
import com.example.enroute.CustomerActivities.CustomerHomeActivity;
import com.example.enroute.ManagementActivities.ShopManagerHomeActivity;
import com.example.enroute.Models.User;
import com.example.enroute.RequestModels.LoginRequest;
import com.example.enroute.RequestModels.LoginResponse;
import com.example.enroute.Services.AuthService;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.login_btn);
        emailInput = (EditText) findViewById(R.id.login_email_input);
        passwordInput = (EditText) findViewById(R.id.login_password_input);

        //emailInput.setText("organization@gmail.com");
        //passwordInput.setText("organization123!");
        emailInput.setText("customer@ukr.net");
        passwordInput.setText("customer123!");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        LoginResponse response = AuthService.getInstance(this).login(new LoginRequest(email, password));

        if (response != null) {
            User user = AuthService.getInstance(this).getUser();
            Intent intent;
            String role = user.getRoles();
            if (role.equals(String.valueOf(Role.customer)))
            {
                intent = new Intent(LoginActivity.this, CustomerHomeActivity.class);
            }
            else {
                intent = new Intent(LoginActivity.this, ShopManagerHomeActivity.class);
            }

            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.universal_fail), Toast.LENGTH_SHORT).show();
        }
    }
}