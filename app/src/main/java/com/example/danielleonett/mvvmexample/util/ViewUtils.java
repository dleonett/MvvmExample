package com.example.danielleonett.mvvmexample.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

public class ViewUtils {

    public static void requestFocus(Context context, View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void scrollToTop(ScrollView scrollView) {
        scrollView.scrollTo(0, 0);
    }
}