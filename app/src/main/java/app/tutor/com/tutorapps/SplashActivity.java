package app.tutor.com.tutorapps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.tutor.com.tutorapps.application.AAApplication;
import app.tutor.com.tutorapps.constants.AAConstants;

public class SplashActivity extends AppCompatActivity {

    View signIN = null;
    EditText userEmail, userPassword;
    final String TAG = "SplashActivity";
    ProgressBar pBar = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        signIN = findViewById(R.id.sign_id);
        userEmail = (EditText) findViewById(R.id.input_username);
        userPassword = (EditText) findViewById(R.id.input_password);
        pBar = (ProgressBar) findViewById(R.id.progressBar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AAApplication.getInstance().getUserData().getUserID().equals("")) {
                    findViewById(R.id.login_panel).setVisibility(View.VISIBLE);
                    findViewById(R.id.sp_logo).setVisibility(View.GONE);
                } else {
                    Intent i = new Intent(SplashActivity.this, LandingActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 3000);

        signIN.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString().trim()).matches()) {
                    if (userPassword.getText().toString().trim().equalsIgnoreCase("")) {
                        userPassword.setError("Enter Password");
                    } else {
                        makeMeSignIn(userEmail.getText().toString().trim(), userPassword.getText().toString().trim());
                    }
                } else {
                    userEmail.setError("Invalid Email ID");
                }
            }
        });


        findViewById(R.id.creat_new).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(SplashActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void makeMeSignIn(final String email, final String password) {
        pBar.setVisibility(View.VISIBLE);
        signIN.setEnabled(false);
        final String URL = AAConstants.DOMAIN_URL + "login";
        StringRequest sr = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response2) {
                        Log.i(TAG, response2);
                        try {
                            JSONObject jObject = new JSONObject(response2);
                            if (jObject.getBoolean("error") == true) {
                                if (jObject.getString("message").equalsIgnoreCase("Login failed. Inactive account.")) {
                                    pBar.setVisibility(View.GONE);
                                    signIN.setEnabled(true);
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashActivity.this);
                                    alertDialogBuilder.setTitle("Academic Association");
                                    alertDialogBuilder
                                            .setMessage("An activation mail is already mailed to your account. Activate your account to login")
                                            .setCancelable(false)
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                } else {
                                    displayMessage(jObject.getString("message"));
                                }

                            } else {
                                AAApplication.getInstance().setUserData(jObject.getString("name").trim(), jObject.getString("email"), "" + jObject.getInt("user_id"));
                                Intent i = new Intent(SplashActivity.this, LandingActivity.class);
                                startActivity(i);
                                finish();
                            }
                        } catch (Exception e) {
                            pBar.setVisibility(View.GONE);
                            signIN.setEnabled(true);
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Sign in Failed...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Output : ", "Error: " + error.getMessage());
                String json = null;
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 400:
                            json = new String(response.data);
//                            json = trimMessage(json, "message");
                            if (json != null) displayMessage(json);
                            break;
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", "" + email);
                params.put("password", "" + password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        AAApplication.getInstance().addToRequestQueue(sr);
    }


    public void displayMessage(String toastString) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashActivity.this);
        alertDialogBuilder.setTitle("Error");
        alertDialogBuilder
                .setMessage(Html.fromHtml(toastString))
                .setCancelable(false)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
//                .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    }
//                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Splash Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://app.tutor.com.tutorappsnew/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Splash Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://app.tutor.com.tutorappsnew/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
