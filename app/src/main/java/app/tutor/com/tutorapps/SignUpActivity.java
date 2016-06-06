package app.tutor.com.tutorapps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import app.tutor.com.tutorapps.application.AAApplication;
import app.tutor.com.tutorapps.constants.AAConstants;
import app.tutor.com.tutorapps.helper.Logger;

public class SignUpActivity extends AppCompatActivity {


    EditText userEmail, userPass, userConfPass, userPhone, userRegNo, userName;
    ProgressBar pBar;
    View signUp;
    private final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pBar = (ProgressBar) findViewById(R.id.sign_prog);

        userName = (EditText) findViewById(R.id.input_username);
        userEmail = (EditText) findViewById(R.id.input_useremail);
        userPass = (EditText) findViewById(R.id.input_userpass);
        userConfPass = (EditText) findViewById(R.id.input_user_cnf_pass);
        userPhone = (EditText) findViewById(R.id.input_user_phone);
        userRegNo = (EditText) findViewById(R.id.input_user_reg);

        signUp = findViewById(R.id.sign_up);

        signUp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString().trim()).matches()) {
                    if (userPass.getText().toString().trim().equals("")) {
                        userPass.setError("Enter Password");
                    } else {
                        if (userName.getText().toString().trim().equals("")) {
                            userConfPass.setError("Enter Your Name");
                        } else {
                            if (userConfPass.getText().toString().trim().equals("")) {
                                userConfPass.setError("Enter Confirm Password");
                            } else {
                                if (userConfPass.getText().toString().trim().equals(userPass.getText().toString().trim())) {
                                    if (userPhone.getText().toString().trim().equals("")) {
                                        userPhone.setError("Enter Phone Number");
                                    } else {
//                                        if (userRegNo.getText().toString().trim().equals("")) {
//                                            userRegNo.setError("Enter Registration Number");
//                                        } else {
//                                            //=======Process SignUp
//                                            makeMeSignUp();
//                                        }

                                        makeMeSignUpGet();

                                    }
                                } else {
                                    userConfPass.setError("Password is not matching with confirm password.");
                                }
                            }
                        }
                    }
                } else {
                    userEmail.setError("Invalid Email Id");
                }
            }
        });
    }


    //=====================================


    //http://academicassociation.co.in/app_data/app_control/user_registration?name=amrita&email=services.to.amrita@gmail.com&password=123456&phone=123456789&address=kolkata&registration_no=1234


//    public void makeMeSignUp() {
//        pBar.setVisibility(View.VISIBLE);
//        signUp.setEnabled(false);
//        final String URL = AAConstants.DOMAIN_URL + "register";
//        StringRequest sr = new StringRequest(Request.Method.POST, URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response2) {
//                        Log.i(TAG, response2);
//                        try {
//                            JSONObject jObject = new JSONObject(response2);
//                            if (jObject.getBoolean("error") == true) {
//                                pBar.setVisibility(View.GONE);
//                                signUp.setEnabled(true);
//                                displayMessage(jObject.getString("message"));
//                            } else {
//                                //AAApplication.getInstance().setUserData(jObject.getString("name").trim(), jObject.getString("email"), "" + jObject.getInt("user_id"));
////                                Intent i = new Intent(SignUpActivity.this, LandingActivity.class);
////                                startActivity(i);
//                                Toast.makeText(getApplicationContext(), "Account Created Successfully. Login now.", Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                        } catch (Exception e) {
//                            pBar.setVisibility(View.GONE);
//                            signUp.setEnabled(true);
//                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(), "Sign in Failed...", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("Output : ", "Error: " + error.getMessage());
//                String json = null;
//                NetworkResponse response = error.networkResponse;
//                if (response != null && response.data != null) {
//                    switch (response.statusCode) {
//                        case 400:
//                            json = new String(response.data);
////                            json = trimMessage(json, "message");
//                            if (json != null) displayMessage(json);
//                            break;
//                    }
//                }
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("email", "" + userEmail.getText().toString().trim());
//                params.put("password", "" + userPass.getText().toString().trim());
//                params.put("name", "" + userName.getText().toString().trim());
//                params.put("phone", "" + userPhone.getText().toString().trim());
//                params.put("registration_no", "" + userRegNo.getText().toString().trim());
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//        AAApplication.getInstance().addToRequestQueue(sr);
//    }


    public void displayMessage(String toastString) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUpActivity.this);
        alertDialogBuilder.setTitle("Error");
        alertDialogBuilder
                .setMessage(Html.fromHtml(toastString))
                .setCancelable(false)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void makeMeSignUpGet() {
        String URL = "";
        pBar.setVisibility(View.VISIBLE);
        try {
            URL = AAConstants.DOMAIN_URL_OTHER_NEW + "user_registration?name=" + URLEncoder.encode(userName.getText().toString().trim(), "UTF-8") +
                    "&email=" + URLEncoder.encode(userEmail.getText().toString().trim(), "UTF-8") +
                    "&password=" + URLEncoder.encode(userPass.getText().toString().trim(), "UTF-8") +
                    "&phone=" + URLEncoder.encode(userPhone.getText().toString().trim(), "UTF-8") +
                    "&address=kolkata&registration_no=" + URLEncoder.encode(userRegNo.getText().toString().trim(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Logger.showMessage(TAG, AAConstants.DOMAIN_URL_OTHER + "notice.php");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pBar.setVisibility(View.GONE);
                        Logger.showMessage(TAG, response.toString());
                        try {
                            if (response.getString("status").equalsIgnoreCase("SUCCESS")) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUpActivity.this);
                                alertDialogBuilder.setTitle("Academic Association");
                                alertDialogBuilder
                                        .setMessage("Account Created Successfully, An activation mail has been send to you soon. Activate your account to login into app.")
                                        .setCancelable(false)
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                onBackPressed();
                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            } else {
                                displayMessage("Failed to create your account. Cause : " + response.getString("message"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.showMessage(TAG, "Error: " + error.getMessage());
                pBar.setVisibility(View.GONE);
                VolleyLog.d("Output : ", "Error: " + error.getMessage());
                String json = null;
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 400:
                            json = new String(response.data);
                            if (json != null) displayMessage(json);
                            break;
                    }
                }
            }
        });
        AAApplication.getInstance().addToRequestQueue(jsonObjReq);
    }

}
