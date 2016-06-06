package app.tutor.com.tutorapps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import app.tutor.com.tutorapps.adapters.QuestionAnsAdapter;
import app.tutor.com.tutorapps.application.AAApplication;
import app.tutor.com.tutorapps.constants.AAConstants;
import app.tutor.com.tutorapps.customview.RobotoMediam;
import app.tutor.com.tutorapps.helper.Logger;
import app.tutor.com.tutorapps.pojo.QuestionAnsModel;

public class ExamManager extends AppCompatActivity {


    View pBAR;
    final String TAG = "ExamManager";
    LinkedList<QuestionAnsModel> mainModel = null;
    RecyclerView questionSet = null;
    boolean isPaused = false;
    public static String ACTION_MOCK = "ACTION_MOCK";
    RobotoMediam timerText = null;

    //===========timer management

    TimerTask timerTask = null;
    Timer timer = null;
    int CURRENT_TIME_SCE = 0;
    QuestionAnsAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        timerText = (RobotoMediam) findViewById(R.id.timer);

        pBAR = findViewById(R.id.loaderView);
        questionSet = (RecyclerView) findViewById(R.id.question_ans_listing);
        questionSet.setHasFixedSize(false);
        questionSet.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if (getIntent().getAction().equalsIgnoreCase(ACTION_MOCK)) {
            getQuestionSetMocktest();
            CURRENT_TIME_SCE = 7200;
        } else {
            getQuestionSetClasstest();
            CURRENT_TIME_SCE = 600;
        }

    }


    public void getQuestionSetClasstest() {
        pBAR.setVisibility(View.VISIBLE);
        final String URL = AAConstants.DOMAIN_URL_OTHER_NEW + "get_set_wise_questions?subject_id=" + getIntent().getStringExtra("SUBJECT_ID") + "&mode=C&set_number=" + getIntent().getStringExtra("SET_ID");
        Logger.showMessage(TAG, URL);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pBAR.setVisibility(View.GONE);
                        Logger.showMessage(TAG, response.toString());
                        try {
                            (new MakingReadyQuestion(response.getJSONArray("all_questions"))).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.showMessage(TAG, "Error: " + error.getMessage());
                pBAR.setVisibility(View.GONE);
            }
        });
        AAApplication.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void getQuestionSetMocktest() {
        pBAR.setVisibility(View.VISIBLE);
        final String URL = AAConstants.DOMAIN_URL_OTHER_NEW + "get_set_wise_questions?subject_id=&mode=M&set_number=" + getIntent().getStringExtra("SET_ID");
        Logger.showMessage(TAG, URL);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pBAR.setVisibility(View.GONE);
                        Logger.showMessage(TAG, response.toString());
                        try {
                            (new MakingReadyQuestion(response.getJSONArray("all_questions"))).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.showMessage(TAG, "Error: " + error.getMessage());
                pBAR.setVisibility(View.GONE);
            }
        });
        AAApplication.getInstance().addToRequestQueue(jsonObjReq);
    }


    public class MakingReadyQuestion extends AsyncTask<Void, Void, Void> {

        JSONArray mainArray = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            adapter = new QuestionAnsAdapter(ExamManager.this, mainModel);
            questionSet.setAdapter(adapter);
            initializeTimerTask();

        }

        @Override
        protected Void doInBackground(Void... params) {

            for (int i = 0; i < mainArray.length(); i++) {
                try {
                    JSONObject innerObj = mainArray.getJSONObject(i);
                    QuestionAnsModel temp_ = new QuestionAnsModel();

                    temp_.setCorrectAns(innerObj.getInt("correct_answer"));

                    temp_.setQuestion(innerObj.getString("question"));

                    temp_.setOption1(innerObj.getString("option1"));
                    temp_.setOption2(innerObj.getString("option2"));
                    temp_.setOption3(innerObj.getString("option3"));
                    temp_.setOption4(innerObj.getString("option4"));

                    temp_.setParagraph(innerObj.getString("paragraph"));
                    temp_.setParagraphID(innerObj.getString("paragraph_id"));

                    if (mainModel.size() > 0) {
                        Logger.showMessage(TAG, "mainModel " + mainModel.size());

                        if (!mainModel.get(mainModel.size() - 1).getParagraphID().equalsIgnoreCase(temp_.getParagraphID()) && !temp_.getParagraph().equals("")) {
                            temp_.setViewType(1);//=====with paragraph

                            Logger.showMessage(TAG, "mainModel " + 1);

                        } else {
                            Logger.showMessage(TAG, "mainModel " + 2);

                            temp_.setViewType(2);
                        }
                    } else {

                        Logger.showMessage(TAG, "mainModel " + mainModel.size());

                        if (!temp_.getParagraph().equals("")) {
                            Logger.showMessage(TAG, "mainModel " + 1);
                            temp_.setViewType(1);//=====with paragraph
                        } else {
                            Logger.showMessage(TAG, "mainModel " + 2);
                            temp_.setViewType(2);
                        }
                    }

                    mainModel.add(temp_);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        public MakingReadyQuestion(final JSONArray mainArray) {
            super();
            this.mainArray = mainArray;
            mainModel = new LinkedList<QuestionAnsModel>();
        }
    }


    public void initializeTimerTask() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                //Logger.showMessage(TAG, "TimerTask " + CURRENT_TIME_SCE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timerText.setText(getTimerHeader(CURRENT_TIME_SCE));
                    }
                });
                if (CURRENT_TIME_SCE <= 0) {

                } else {
                    CURRENT_TIME_SCE--;
                }
            }
        };

        timer.schedule(timerTask, 1, 1000);

    }

    public String getTimerHeader(int timer_) {
        String temp_;
        int tempI = timer_ / 60;
        if (tempI < 10) {
            temp_ = "0" + tempI;
        } else {
            temp_ = "" + tempI;
        }
        tempI = timer_ % 60;
        if (tempI < 10) {
            temp_ = temp_ + ":" + "0" + tempI;
        } else {
            temp_ = temp_ + ":" + "" + tempI;
        }
        return temp_;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(ExamManager.this);
        builder.setMessage("Do you want to submit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (adapter != null) {
                    AAApplication.getInstance().setCURRENT_QUN_SET(adapter.getSubName());
                }
                Intent i = new Intent(ExamManager.this, ExamResult.class);
                if (getIntent().getAction().equalsIgnoreCase(ACTION_MOCK)) {
                    i.setAction(ExamResult.ACTION_MOCK);
                } else {
                    i.setAction("class");
                }
                i.putExtra("SET_ID", getIntent().getStringExtra("SET_ID"));
                startActivity(i);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
        Logger.showMessage(TAG, "onPause");
        timerTask.cancel();
        timer.cancel();
        timer.purge();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.showMessage(TAG, "onResume");
        if (isPaused) {
            isPaused = false;
            initializeTimerTask();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ExamManager.this);
            builder.setMessage("Do you want to submit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    if (adapter != null) {
                        AAApplication.getInstance().setCURRENT_QUN_SET(adapter.getSubName());
                    }
                    Intent i = new Intent(ExamManager.this, ExamResult.class);
                    if (getIntent().getAction().equalsIgnoreCase(ACTION_MOCK)) {
                        i.setAction(ExamResult.ACTION_MOCK);
                    } else {
                        i.setAction("class");
                    }
                    i.putExtra("SET_ID", getIntent().getStringExtra("SET_ID"));
                    startActivity(i);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.setCancelable(false);
            builder.show();

        }

        return super.onOptionsItemSelected(item);
    }


}
