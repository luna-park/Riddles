package org.lunapark.dev.riddles;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.lunapark.dev.ltf.LTFCategory;
import org.lunapark.dev.ltf.LTFInterface;
import org.lunapark.dev.ltf.LTFQuest;

import java.util.ArrayList;

/**
 * Created by znak on 15.12.2016.
 */

public class Question extends Fragment implements View.OnClickListener {

    private LTFInterface ltfInterface;
    private LTFCategory ltfCategory;
    private int categoryNumber;
    private String categoryName;
    private int step = 0, limit, rightAnswer, success;

    private TextView tvQuestion, tvTitle;

    private ProgressBar progressBar;
    private Handler progressHandler;
    private Runnable progressRunnable;

    private LinearLayout layoutAnswers;
    private ViewGroup.LayoutParams lpButton;
    private ArrayList<Button> buttons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        System.out.println("onCreateView");

        View rootView = inflater.inflate(R.layout.question, container, false);
        layoutAnswers = (LinearLayout) rootView.findViewById(R.id.layoutAnswers);

        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);

        tvQuestion = (TextView) rootView.findViewById(R.id.tvQuestion);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressQuest);
        progressHandler = new Handler();
        progressRunnable = new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(100 * step / limit);
            }
        };

        lpButton = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttons = new ArrayList<Button>();

        ltfInterface = (LTFInterface) getActivity();
        step = 0;
        success = 0;

        categoryNumber = ltfInterface.getCategoryNumber();
        ltfCategory = ltfInterface.getBase().getCategories().get(categoryNumber);
        categoryName = ltfCategory.getName();
        limit = ltfCategory.getLimit();
        setData();


        return rootView;
    }

    private void setProgress() {
        progressHandler.post(progressRunnable);
    }

    private void setData() {
        step++;
        tvTitle.setText(categoryName + " " + step + "/" + limit);

        setProgress();

        LTFQuest ltfQuest = ltfCategory.getQuest();
        tvQuestion.setText(ltfQuest.getQuestion());

        // Create answer buttons
        ArrayList<String> answers = ltfQuest.getAnswersShuffle();
        rightAnswer = ltfQuest.getRightAnswer();

        for (int i = 0; i < ltfQuest.getSize(); i++) {
            Button btnAnswer = new Button(getActivity().getApplicationContext());

            btnAnswer.setId(i);
            btnAnswer.setBackgroundResource(android.R.drawable.btn_default);


            btnAnswer.setTextColor(Color.BLACK);
            btnAnswer.setGravity(Gravity.LEFT);
            btnAnswer.setText(answers.get(i));
            btnAnswer.setOnClickListener(this);
            layoutAnswers.addView(btnAnswer, lpButton);
            buttons.add(btnAnswer);
        }

    }

    @Override
    public void onClick(View v) {
        // Check right answer
        if (v.getId() == rightAnswer) {
            success++;
            ltfInterface.reaction(true);
        } else {
            ltfInterface.reaction(false);
        }

        // Check steps
        if (step == limit) {
            ltfInterface.showResult(success);
        } else {
//            step++;
            // Clear buttons view
            layoutAnswers.removeAllViews();
            buttons.clear();
            setData();
        }

    }
}
