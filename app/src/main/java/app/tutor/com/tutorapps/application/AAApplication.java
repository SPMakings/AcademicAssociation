package app.tutor.com.tutorapps.application;

import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.LinkedList;

import app.tutor.com.tutorapps.pojo.QuestionAnsModel;
import app.tutor.com.tutorapps.pojo.UserDetails;

/**
 * Created by Saikat's Mac on 06/05/16.
 */

public class AAApplication extends MultiDexApplication {


    private SharedPreferences userData = null;
    UserDetails user = null;
    private RequestQueue mRequestQueue;
    private static AAApplication mInstance;
    public final String TAG = "AAApplication";
    private LinkedList<QuestionAnsModel> CURRENT_QUN_SET = null;

    private String SELECTED_TEST = "", SELECTED_TEST_SET = "";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        CURRENT_QUN_SET = new LinkedList<QuestionAnsModel>();
    }

    public LinkedList<QuestionAnsModel> getCURRENT_QUN_SET() {
        return CURRENT_QUN_SET;
    }

    public void setCURRENT_QUN_SET(LinkedList<QuestionAnsModel> CURRENT_QUN_SET) {
        this.CURRENT_QUN_SET = CURRENT_QUN_SET;
    }

    public UserDetails getUserData() {
        if (userData == null) {
            userData = getSharedPreferences("AAApplication", MODE_PRIVATE);
        }
        if (user == null) {
            user = new UserDetails();
        }
        user.setUserEmail(userData.getString("uEmail", ""));
        user.setUserName(userData.getString("uName", ""));
        user.setUserID(userData.getString("uID", ""));

        return user;
    }

    public void setUserData(final String uName, final String uEmail, final String uID) {
        if (userData == null) {
            userData = getSharedPreferences("AAApplication", MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = userData.edit();
        edit.putString("uEmail", uEmail);
        edit.putString("uName", uName);
        edit.putString("uID", uID);
        edit.commit();
    }

    public void clearUserData() {
        if (userData == null) {
            userData = getSharedPreferences("AAApplication", MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = userData.edit();
        edit.clear();
        edit.commit();
    }


    public static synchronized AAApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public String getSELECTED_TEST() {
        return SELECTED_TEST;
    }

    public void setSELECTED_TEST(String SELECTED_TEST) {
        this.SELECTED_TEST = SELECTED_TEST;
    }

    public String getSELECTED_TEST_SET() {
        return SELECTED_TEST_SET;
    }

    public void setSELECTED_TEST_SET(String SELECTED_TEST_SET) {
        this.SELECTED_TEST_SET = SELECTED_TEST_SET;
    }
}
