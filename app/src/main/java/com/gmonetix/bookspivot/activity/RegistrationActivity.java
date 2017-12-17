package com.gmonetix.bookspivot.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gmonetix.bookspivot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity  {
    Button register;
    String str_Password, str_Username, str_Email;
    AppCompatEditText edt_Password, edt_Email, edt_Username;
    TextView existing_user;
    private FirebaseAuth auth;

    Button showb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
        auth = FirebaseAuth.getInstance();
        register = (Button) findViewById(R.id.btn_register);
        edt_Email = (AppCompatEditText) findViewById(R.id.edt_email);
        edt_Password = (AppCompatEditText) findViewById(R.id.edt_Rpassword);
        edt_Username = (AppCompatEditText) findViewById(R.id.edt_username);
        existing_user = (TextView) findViewById(R.id.existing);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_Password = edt_Password.getText().toString();
                str_Email = edt_Email.getText().toString();
                str_Username=edt_Username.getText().toString();
                if (str_Email.length() == 0 & str_Password.length() == 0
                        & str_Username.length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "All fields are mandatory", Toast.LENGTH_LONG)
                            .show();
                } else if (str_Password.length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Empty Field!", Toast.LENGTH_LONG).show();
                } else if (str_Username.length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Empty Field!", Toast.LENGTH_LONG).show();
                } else if (str_Email.length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Empty Field!", Toast.LENGTH_LONG).show();
                } else {
                    SplashActivity.editor.putString("password", str_Password);
                    SplashActivity.editor.putString("username", str_Username);
                    SplashActivity.editor.commit();

                    auth.createUserWithEmailAndPassword(str_Email, str_Password)
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {

                                        try {
                                            throw task.getException();
                                        } catch (FirebaseNetworkException e) {
                                            Toast.makeText(RegistrationActivity.this,
                                                    "Network problem!",Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            Toast.makeText(RegistrationActivity.this,
                                                    "Some error occurred!"+String.valueOf(task.getException()),Toast.LENGTH_SHORT).show();
                                        }
                                        Toast.makeText(RegistrationActivity.this,
                                                "Some error occurred!"+String.valueOf(task.getException()),Toast.LENGTH_SHORT).show();


                                        if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                            Toast.makeText(RegistrationActivity.this,
                                                    "Email Id already exists", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(RegistrationActivity.this,
                                                "Check your mailbox for confirmation", Toast.LENGTH_LONG).show();
                                        sendVerificationEmail();
                                    }
                                }
                            });
                }
            }
        });
        existing_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(go);

            }
        });

    }

    private void sendVerificationEmail(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent sendtoLogin = new Intent(getApplicationContext(),
                            LoginActivity.class);
                    startActivity(sendtoLogin);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(RegistrationActivity.this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

}

