package com.example.bengkelrywin.UnitTesting;

import android.content.Context;
import android.content.Intent;

import com.example.bengkelrywin.LoginActivity;

public class ActivityUtil {
    private Context context;

    public ActivityUtil(Context context) {
        this.context = context;
    }

    public void startMainLogin() {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
