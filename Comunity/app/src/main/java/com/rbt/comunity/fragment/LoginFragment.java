package com.rbt.comunity.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.rbt.comunity.Activities.ForgotPasswordActivity;
import com.rbt.comunity.Activities.MainActivity;
import com.rbt.comunity.R;

public class LoginFragment extends Fragment {

    private EditText etEmail;
    private EditText etPw;
    private Button btn_login;
    private TextView forgot;
    private static final String TAG = "LoginActivity";
    private FirebaseAuth authProfile;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.card_login, container, false);
        etEmail = root.findViewById(R.id.email_login);
        etPw = root.findViewById(R.id.password);
        btn_login = root.findViewById(R.id.btn_login);
        forgot = root.findViewById(R.id.tv_forgot);

        authProfile = FirebaseAuth.getInstance();

        etPw.addTextChangedListener(loginText);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = etEmail.getText().toString();
                String txtPw = etPw.getText().toString();

                loginUser(txtEmail, txtPw);

//                Intent intent = new Intent(getContext(), MainActivity.class);
//                startActivity(intent);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ForgotPasswordActivity.class));
            }
        });
        return root;
    }

    private void loginUser(String email, String pw) {
        authProfile.signInWithEmailAndPassword(email, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    // Get instance of the current User
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    // Check if email is verified before user can access their profile
                    if (firebaseUser.isEmailVerified()) {
                        Toast.makeText(getContext(), "You are logged in now", Toast.LENGTH_SHORT).show();

                        // Open Usr Profile
                        startActivity(new Intent(getContext(), MainActivity.class));
                    } else {
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut();
                        showAlertDialog();
                    }
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        etEmail.setError("User does not exists or is no longer valid. Please register again.");
                        etEmail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        etEmail.setError("Invalid credentials. Kindly, check and re-enter.");
                        etEmail.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void showAlertDialog() {
        // Setup allert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You can not login without email verification");

        // Open Email Apps if User clicks/tabs continue button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // To email app in new window and not within our app
                startActivity(intent);
            }
        });

        // Create Alert Dialog Box
        AlertDialog alertDialog = builder.create();

        // Show Alert Dialog
        alertDialog.show();
    }

    // Check if user is already logged in. In such case, straightaway take the User to the User's Profile
    @Override
    public void onStart() {
        super.onStart();
        if (authProfile.getCurrentUser() != null) {
            Toast.makeText(getContext(), "Already Logged In!", Toast.LENGTH_SHORT).show();
            // Start the MainActivity
            startActivity(new Intent(getContext(), MainActivity.class));
        }else{
            Toast.makeText(getContext(), "You can login now!", Toast.LENGTH_SHORT).show();

        }
    }

    private TextWatcher loginText = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String user = etEmail.getText().toString().trim();
            String pass = etPw.getText().toString().trim();

            btn_login.setEnabled(!user.isEmpty() && !pass.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
