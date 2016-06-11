package app.tutor.com.tutorapps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import app.tutor.com.tutorapps.adapters.CorrectAnswerAdapter;
import app.tutor.com.tutorapps.adapters.QuestionAnsAdapter;
import app.tutor.com.tutorapps.application.AAApplication;

public class ShowingCorrectAnswerActivity extends AppCompatActivity {


    //===========Main array  AAApplication.getInstance().getCURRENT_QUN_SET()

    private RecyclerView mainView = null;
    private CorrectAnswerAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_correct_answer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Correct Answers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mainView = (RecyclerView) findViewById(R.id.question_ans_set);
        mainView.setHasFixedSize(false);
        mainView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapter = new CorrectAnswerAdapter(ShowingCorrectAnswerActivity.this, AAApplication.getInstance().getCURRENT_QUN_SET());
        mainView.setAdapter(adapter);

    }

}
