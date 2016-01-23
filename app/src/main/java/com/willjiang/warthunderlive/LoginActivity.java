package com.willjiang.warthunderlive;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.willjiang.warthunderlive.Network.API;
import com.willjiang.warthunderlive.Network.RequestMaker;

/**
 * Created by Will on 1/21/16.
 */
public class LoginActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setupTextFields();
        setupLoginButton(this);
    }

    public void setUserID(String userID) {
        SharedPreferences prefs = this.getSharedPreferences(
                this.getPackageName(), Context.MODE_PRIVATE);
        prefs.edit().putString(API.userIDKey, userID).apply();
        API.userID = userID;
    }

    public void clearUserID() {
        SharedPreferences prefs = this.getSharedPreferences(
                this.getPackageName(), Context.MODE_PRIVATE);
        prefs.edit().remove(API.userIDKey).apply();
        API.userID = "0";
    }

    @Deprecated
    public void restoreUserID() {
        ((MainActivity) getParent()).restoreUserID();
    }

    private void setupTextFields() {
        final EditText username = (EditText) findViewById(R.id.login_username);
        final EditText password = (EditText) findViewById(R.id.login_password);
        setupKeyboard(username);

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String t = username.getText().toString().trim();
                    if (t.equals("")) {
                        username.setError("Enter username");
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(t).matches()) {
                        username.setError("Invalid Email address");
                    }
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String t = password.getText().toString().trim();
                    if (t.equals("")) {
                        password.setError("Enter password");
                    }
                }
            }
        });
    }

    private void setupLoginButton(final Context requestContext) {
        final Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!API.userID.equals("0")) {
                    Log.v("log", "logout");
                    logout();
                    return;
                }
                EditText username = (EditText) findViewById(R.id.login_username);
                EditText password = (EditText) findViewById(R.id.login_password);
                username.clearFocus();
                username.requestFocus();
                password.clearFocus();
                password.requestFocus();
                loginButton.requestFocus();

                if (username.getError() == null && password.getError() == null) {
                    hideKeyboard();
                    final View rootView = findViewById(R.id.login);
                    Bundle args = new Bundle();
                    args.putString("type", "login");
                    args.putString("username", username.getText().toString());
                    args.putString("password", password.getText().toString());
                    new RequestMaker(requestContext, rootView, args).execute(args);
                }
            }
        });
    }

    private void setupKeyboard(final View view) {
        // script taken from:
        // http://stackoverflow.com/questions/4745988/
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);
                int screenHeight = view.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    ScrollView scrollView = (ScrollView) findViewById(R.id.login_scroll);
                    scrollView.smoothScrollBy(0, 500);
                } else {
                    // keyboard is closed
                }
            }
        });
    }

    private void hideKeyboard() {
        // script taken from:
        // http://stackoverflow.com/questions/1109022/
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void logout() {
        clearUserID();
        ((WTLApplication) getApplicationContext()).clearCookie();
    }

    public void successAndBack() {
        Toast.makeText(this, "Login successful!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void fail() {
        Toast.makeText(this, "Fail to login!", Toast.LENGTH_LONG).show();
        ((WTLApplication) getApplicationContext()).clearCookie();
    }
}
