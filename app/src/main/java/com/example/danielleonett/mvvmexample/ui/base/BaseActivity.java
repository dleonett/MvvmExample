package com.example.danielleonett.mvvmexample.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by daniel.leonett on 29/01/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        initVars();
        initViews();
    }

    protected abstract int getContentViewId();

    protected abstract void initVars();

    protected void initViews() {
        bindViews();
        useViews();
    }

    protected abstract void bindViews();

    protected abstract void useViews();

    public Context getAppContext() {
        return getApplicationContext();
    }

    public void showToast(int stringResId, Object... objects) {
        showToast(getString(stringResId, objects));
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(int stringResId, Object... objects) {
        showLongToast(getString(stringResId, objects));
    }

    public void showLongToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    protected void setOkResult() {
        setResult(RESULT_OK);
    }

    protected void setOkResultWithData(Bundle args) {
        Intent intent = new Intent();
        intent.putExtras(args);

        setResult(RESULT_OK, intent);
    }

    protected void sendOkResult() {
        setOkResult();
        finish();
    }

    protected void sendOkResultWithData(Bundle args) {
        setOkResultWithData(args);
        finish();
    }


    protected Object getArgument(String key) {
        if (getIntent().getExtras() != null) {
            return getIntent().getExtras().get(key);
        }

        return null;
    }

    protected Object getArgument(Intent intent, String key) {
        if (intent.getExtras() != null) {
            return intent.getExtras().get(key);
        }

        return null;
    }
}
