package org.lunapark.dev.riddles;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.lunapark.dev.ltf.LTFInterface;
import org.lunapark.dev.ltf.LTFResult;


/**
 * Created by znak on 18.12.2016.
 */

public class Result extends Fragment implements View.OnClickListener {

    private LTFInterface ltfInterface;
    private TextView tvResult, tvHeader;
    private ProgressBar pbResult;
    private LTFResult ltfResult;
    private TimeInterpolator DEFAULT_INTERPOLATER;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ltfInterface = (LTFInterface) getActivity();

        View rootView = inflater.inflate(R.layout.result, container, false);
        tvResult = (TextView) rootView.findViewById(R.id.tvResult);
        tvHeader = (TextView) rootView.findViewById(R.id.tvResultHeader);
        pbResult = (ProgressBar) rootView.findViewById(R.id.pbResult);
        Button btnOk = (Button) rootView.findViewById(R.id.btnResultOk);
        btnOk.setOnClickListener(this);

        ltfResult = ltfInterface.getResult();
        String categoryName = ltfResult.getCategoryName();
        int success = ltfResult.getSuccess();
        int limit = ltfResult.getLimit();

        int progress = Math.round(100 * success / limit);
        System.out.println(progress);

        DEFAULT_INTERPOLATER = new LinearInterpolator();


        tvHeader.setText(categoryName);
        tvResult.setText(success + "/" + limit + "\n" + progress + "%");
        setProgress(progress);
        return rootView;
    }

    private void setProgress(int progress) {

        ObjectAnimator animation = ObjectAnimator.ofInt(pbResult, "progress", progress);
        animation.setDuration(1000); // 0.5 second
        animation.setInterpolator(DEFAULT_INTERPOLATER);
        animation.start();
    }

    @Override
    public void onClick(View v) {
        ltfInterface.showCategories();
    }
}
