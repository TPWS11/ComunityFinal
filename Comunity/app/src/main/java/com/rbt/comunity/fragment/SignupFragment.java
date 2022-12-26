package com.rbt.comunity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;
import com.rbt.comunity.Activities.MainActivity;
import com.rbt.comunity.R;
import com.rbt.comunity.model.ReadWriteUserDetails;

import java.util.regex.Pattern;

public class SignupFragment extends Fragment {

    private EditText etUser, etEmail, etPw, etCpw;
    private Button btnSignUp;
    private static final String TAG = "SignupActivity";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.card_signup, container, false);

        etUser = root.findViewById(R.id.usernmae);
        etEmail = root.findViewById(R.id.email);
        etPw = root.findViewById(R.id.pw1);
        etCpw = root.findViewById(R.id.pw2);

        btnSignUp = root.findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUser = etUser.getText().toString();
                String txtEmail = etEmail.getText().toString();
                String txtPw = etPw.getText().toString();
                String txtCpw = etCpw.getText().toString();

                if (TextUtils.isEmpty(txtUser)) {
                    Toast.makeText(getContext(), "Please enter your username", Toast.LENGTH_SHORT).show();
                    etUser.setError("Username is required");
                    etUser.requestFocus();
                } else if (TextUtils.isEmpty(txtEmail)) {
                    Toast.makeText(getContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                    etEmail.setError("Email is required");
                    etEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
                    Toast.makeText(getContext(), "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    etEmail.setError("Valid email is required");
                    etEmail.requestFocus();
                } else if (TextUtils.isEmpty(txtPw)) {
                    Toast.makeText(getContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                    etPw.setError("Password is required");
                    etPw.requestFocus();
                } else if (txtPw.length() < 8) {
                    Toast.makeText(getContext(), "Password should be at least 8 digits", Toast.LENGTH_SHORT).show();
                    etPw.setError("Password too weak");
                    etPw.requestFocus();
                } else if (TextUtils.isEmpty(txtCpw)) {
                    Toast.makeText(getContext(), "Please enter your confirm password", Toast.LENGTH_SHORT).show();
                    etCpw.setError("Comfirm password is required");
                    etCpw.requestFocus();
                } else if (!txtCpw.equals(txtPw)) {
                    Toast.makeText(getContext(), "Please same same password", Toast.LENGTH_SHORT).show();
                    etCpw.setError("Password Confirmation is required");
                    etCpw.requestFocus();

                    etPw.clearComposingText();
                    etCpw.clearComposingText();
                } else {
                    signUp(txtUser, txtEmail, txtPw, txtCpw);
                }
            }
        });
        return root;
    }

    // credential firebase
    private void signUp(String txtUser, String txtEmail, String txtPw, String txtCpw) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        // Create User Profile
        auth.createUserWithEmailAndPassword(txtEmail, txtPw).addOnCompleteListener(getActivity(),
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = auth.getCurrentUser();

                            // Update Display Name of user
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(txtUser).build();
                            firebaseUser.updateProfile(profileChangeRequest);

                            // Enter User Data into the Firebase Realtime Database.
                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(txtUser, txtEmail);

                            // Extracting User refrence from database for "Sign-up User"
                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Sign-up Users");

                            referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        // Verification Email
                                        firebaseUser.sendEmailVerification();

                                        Toast.makeText(getContext(), "User Sign-up success. Please verify your email", Toast.LENGTH_SHORT).show();
                                        // Open User Profile after success sigup

                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(getContext(), "User Sign-up failed. Please try again", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                etPw.setError("Your password is too weak. Kindly use a mix of alphabets, " +
                                        "numbers and special characters");
                                etPw.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                etPw.setError("Your email is invalid or already in use. Kindly re-enter.");
                                etPw.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                etPw.setError("User is already registered with this email. User another email.");
                                etPw.requestFocus();
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
