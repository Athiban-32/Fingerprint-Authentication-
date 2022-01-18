package com.example.fingerprintauthentication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BiometricPrompt.PromptInfo promptInfo=new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Verify it Please!!")
                        .setDescription("User Authentication is required")
                        .setNegativeButtonText("Cancel")
                        .build();
                getPrompt().authenticate(promptInfo);
            }
        });
    }
    private BiometricPrompt getPrompt(){
        Executor executor= ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback=new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                notifyuser(errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                notifyuser("Authentication Succeeded!!");
                Intent intent=new Intent(MainActivity.this,SecretActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                notifyuser("Authentication Failed!!");
            }
        };
        BiometricPrompt biometricPrompt=new BiometricPrompt(this,executor,callback);
        return biometricPrompt;

    }
    private void notifyuser(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}