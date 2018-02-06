package com.example.danielleonett.mvvmexample.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by daniel.leonett on 29/01/2018.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentViewId(), container, false);
    }

    protected abstract int getContentViewId();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    protected abstract void initVars();

    protected void initViews(View view) {
        bindViews(view);
        useViews();
    }

    protected abstract void bindViews(View view);

    protected abstract void useViews();

    protected Object getArgument(String key) {
        if (getArguments() != null) {
            return getArguments().get(key);
        }

        return null;
    }

}
