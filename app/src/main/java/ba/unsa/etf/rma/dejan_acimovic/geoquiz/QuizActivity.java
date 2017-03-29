package ba.unsa.etf.rma.dejan_acimovic.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button ;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class QuizActivity extends AppCompatActivity {

    public static final String TAG = "QuizActivity" ;
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;


    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private TextView myQuestionTextView;
    private ImageButton mBackButton;
    private Button mCheatButton;
    private boolean mIsCheater;

    private Question[] myQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0 ;

    private void updateQuestion()
    {
        int question = myQuestionBank[mCurrentIndex].getTextResId() ;
        myQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = myQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId =0  ;
        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate(Bundle) called") ;
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        setContentView(R.layout.activity_quiz);

        mTrueButton = (Button) findViewById(R.id.trueButton);
        mFalseButton= (Button) findViewById(R.id.falseButton);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        myQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        updateQuestion();
        mBackButton = (ImageButton)findViewById(R.id.previous_button) ;


        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex-1) ;
                if(mCurrentIndex < 0) mCurrentIndex = myQuestionBank.length-1;
                updateQuestion();
            }
        });


        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkAnswer(true);
            }
        });
        myQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                mCurrentIndex = (mCurrentIndex+1)%myQuestionBank.length ;
                updateQuestion();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mCurrentIndex = (mCurrentIndex+1)%myQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                boolean answerIsTrue  = myQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this , answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(TAG , "onStart() called");
    }

    @Override
    public  void  onPause()
    {
        super.onPause();
        Log.d(TAG , "onPause() called");
    }

    @Override
    public void  onResume()
    {
        super.onResume();
        Log.d(TAG , "onResume() called");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(TAG , "onStop() called");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG , "onDestroy() called");
    }
}
