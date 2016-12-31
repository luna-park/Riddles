package org.lunapark.dev.riddles;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.lunapark.dev.ltf.LTFInterface;


/**
 * Created by znak on 15.12.2016.
 */

public class Categories extends Fragment implements View.OnClickListener {

    private LTFInterface ltfInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ltfInterface = (LTFInterface) getActivity();

        View rootView = inflater.inflate(R.layout.categories, container, false);

        Button btnCat1 = (Button) rootView.findViewById(R.id.btnCat1);
        Button btnCat2 = (Button) rootView.findViewById(R.id.btnCat2);

        btnCat1.setText(ltfInterface.getBase().getCategories().get(0).getName());
        btnCat2.setText(ltfInterface.getBase().getCategories().get(1).getName());

        btnCat1.setOnClickListener(this);
        btnCat2.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int i = 0;
        switch (v.getId()) {
            case R.id.btnCat1:
                i = 0;
                break;
            case R.id.btnCat2:
                i = 1;
                break;

        }

        ltfInterface.onCategorySelect(i);
//        Toast.makeText(getActivity(), "Вы нажали на кнопку " + i, Toast.LENGTH_SHORT).show();
    }
}
