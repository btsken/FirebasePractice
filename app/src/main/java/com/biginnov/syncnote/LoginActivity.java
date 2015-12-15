package com.biginnov.syncnote;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.biginnov.syncnote.utils.LogUtils;
import com.biginnov.syncnote.utils.PreferenceUtils;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    public static final String USER_EMAIL = "user_email";
    public static final String USER_PWD = "user_pwd";
    private Firebase mFirebase;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private Firebase.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        showProgressDialog();

        Firebase.setAndroidContext(this);
        mFirebase = new Firebase("https://syncnote.firebaseio.com/");

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                loginOrRegister();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mAuthStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                LogUtils.d("onAuthStateChanged", authData);
                if (authData != null) {
                    enterHomePage();
                }
                dismissProgressDialog();
            }
        };
        /* Check if the user is authenticated with Firebase already. If this is the case we can set the authenticated
         * user and hide hide any login buttons */
        mFirebase.addAuthStateListener(mAuthStateListener);
    }

    Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
        @Override
        public void onAuthenticated(AuthData authData) {
            // Authentication just completed successfully :)
            Map<String, String> map = new HashMap<>();
            map.put("provider", authData.getProvider());
            if (authData.getProviderData().containsKey("displayName")) {
                map.put("displayName", authData.getProviderData().get("displayName").toString());
            }

            PreferenceUtils.setStringPrefrences(LoginActivity.this, USER_EMAIL,
                    mEmailView.getText().toString());
            PreferenceUtils.setStringPrefrences(LoginActivity.this, USER_PWD,
                    mPasswordView.getText().toString());
            mFirebase.child("users").child(authData.getUid()).setValue(map);
            enterHomePage();
            dismissProgressDialog();
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            // Authenticated failed with error firebaseError
            LogUtils.d("onAuthenticationError", firebaseError.getCode(), ", ", firebaseError.toString());
            PreferenceUtils.setStringPrefrences(LoginActivity.this, USER_EMAIL, "");
            PreferenceUtils.setStringPrefrences(LoginActivity.this, USER_PWD, "");

            if (firebaseError.getCode() == FirebaseError.USER_DOES_NOT_EXIST) {
                mFirebase.createUser(mEmailView.getText().toString(),
                        mPasswordView.getText().toString(),
                        new Firebase.ValueResultHandler<Map<String, Object>>() {
                            @Override
                            public void onSuccess(Map<String, Object> result) {
                                System.out.println("Successfully created user account with uid: " + result.get("uid"));
                                dismissProgressDialog();
                                enterHomePage();
                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                // there was an error
                                PreferenceUtils.setStringPrefrences(LoginActivity.this, USER_EMAIL, "");
                                PreferenceUtils.setStringPrefrences(LoginActivity.this, USER_PWD, "");
                                dismissProgressDialog();
                            }
                        });
            } else {
                Toast.makeText(LoginActivity.this, firebaseError.toString(), Toast.LENGTH_LONG).show();
                dismissProgressDialog();
            }
        }
    };

    private void loginOrRegister() {
        // Create a handler to handle the result of the authentication
        mFirebase.authWithPassword(mEmailView.getText().toString(),
                mPasswordView.getText().toString(), authResultHandler);
    }

    private void enterHomePage() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}

