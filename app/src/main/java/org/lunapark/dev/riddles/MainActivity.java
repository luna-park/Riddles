package org.lunapark.dev.riddles;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.view.View;

import org.lunapark.dev.ltf.LTFBase;
import org.lunapark.dev.ltf.LTFInterface;
import org.lunapark.dev.ltf.LTFResult;


public class MainActivity extends Activity implements LTFInterface {

    private Categories fragmentCategories;
    private Question fragmentQuestion;
    private Result fragmentResult;
    private FragmentManager fragmentManager;
    private LTFBase ltfBase;
    private LTFResult ltfResult;
    private int categoryNumber;

    private View mainLayout;
    private ArgbEvaluator evaluator;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            Signature[] signatures = info.signatures;
            if (signatures != null) System.out.println(signatures[0].toString());
        }

        // Create data
        initData();
        prepareAnimation();
        // Create fragments
        fragmentCategories = new Categories();
        fragmentQuestion = new Question();
        fragmentResult = new Result();

        fragmentManager = getFragmentManager();
        mainLayout = findViewById(R.id.activity_main);

        showCategories();

    }


    private void initData() {
        ltfResult = new LTFResult();
        String[] questions1 = getResources().getStringArray(R.array.questions1);
        String[] questions2 = getResources().getStringArray(R.array.questions2);
        ltfBase = new LTFBase();
        ltfBase.createCategory(questions1, questions1.length - 1);
        ltfBase.createCategory(questions2, questions2.length - 1);
    }

    private void changeFragment(Fragment fragment, boolean add2stack) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.activity_main, fragment, "fragment");
        if (add2stack) ft.addToBackStack(null);
        ft.setCustomAnimations(FragmentTransaction.TRANSIT_FRAGMENT_FADE, FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }


    @Override
    public void onCategorySelect(int buttonIndex) {
        categoryNumber = buttonIndex;
        changeFragment(fragmentQuestion, true);
    }

    @Override
    public void showCategories() {
        changeFragment(fragmentCategories, false);
    }

    @Override
    public void showResult(int intResult) {
        ltfResult.setCategoryName(ltfBase.getCategories().get(categoryNumber).getName());
        ltfResult.setSuccess(intResult);
        ltfResult.setLimit(ltfBase.getCategories().get(categoryNumber).getLimit());
        changeFragment(fragmentResult, false);
    }

    private void prepareAnimation() {
        evaluator = new ArgbEvaluator();
    }

    @Override
    public void reaction(boolean answer) {

//        int colorFrom = getResources().getColor(android.support.v7.appcompat.R.color.abc_btn_colored_text_material);
//
//        if (answer)
//            colorFrom = getResources().getColor(android.support.v7.appcompat.R.color.accent_material_light);

        int colorFrom = getResources().getColor(R.color.colorAccent);
        if (answer) colorFrom = getResources().getColor(R.color.colorPrimary);
//        int colorTo = getResources().getColor(android.support.v7.appcompat.R.color.background_material_light);
        int colorTo = getResources().getColor(R.color.colorBackground);


        ValueAnimator colorAnimation = ValueAnimator.ofObject(evaluator, colorFrom, colorTo);
        colorAnimation.setDuration(500); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                mainLayout.setBackgroundColor((Integer) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    @Override
    public LTFBase getBase() {
        return ltfBase;
    }

    @Override
    public LTFResult getResult() {
        return ltfResult;
    }

    @Override
    public int getCategoryNumber() {
        return categoryNumber;
    }

}
