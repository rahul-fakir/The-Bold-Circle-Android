package com.rahul.fakir.theboldcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        final TextView tvCreateProfile = (TextView) findViewById(R.id.tvCreateProfile);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final TextView tvResetPassword = (TextView) findViewById(R.id.tvForgotPassword);
        final Switch swtchRememberUser = (Switch) findViewById(R.id.swtchPersistSession);
        Firebase ref = new Firebase("https://the-bold-circle.firebaseio.com");


        AuthData authData = ref.getAuth();
        if (authData != null) {
            Intent intentToMain = new Intent(LoginActivity.this, MainActivity.class);
            intentToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentToMain);
            finish();
        }

        tvCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToCreateProfile = new Intent(LoginActivity.this, CreateProfileActivity.class);
                intentToCreateProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentToCreateProfile);
            }
        });

        tvResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToCreateProfile = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                intentToCreateProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentToCreateProfile);
            }
        });

        assert btnLogin != null;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidationResult result = validateForm(etEmail.getText().toString(), 1);

                if (result.getStatus()){
                    result = validateForm(etPassword.getText().toString(), 3);
                    if (result.getStatus()) {
                        final Firebase ref = new Firebase("https://the-bold-circle.firebaseio.com");
                        // Create a handler to handle the result of the authentication
                        Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                // Authenticated successfully with payload authData




                                Intent intentToMain = new Intent(LoginActivity.this, MainActivity.class);
                                intentToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                if (!swtchRememberUser.isChecked()) {
                                    ref.unauth();
                                }
                                startActivity(intentToMain);
                                finish();
                            }

                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                // Authenticated failed with error firebaseError
                                Toast.makeText(getApplicationContext(), firebaseError.toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        };

                        ref.authWithPassword(etEmail.getText().toString(), etPassword.getText().toString(), authResultHandler);

                    } else{
                        Toast.makeText(getApplicationContext(), result.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), result.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public ValidationResult validateForm(String input, int code){
        DataValidation validationCheck = new DataValidation(input, code);
        ValidationResult result = new ValidationResult(validationCheck.getValidationStatus(), validationCheck.getErrorMessage());
        return result;
    }
}
