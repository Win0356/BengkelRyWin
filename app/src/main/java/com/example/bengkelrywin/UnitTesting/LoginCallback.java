package com.example.bengkelrywin.UnitTesting;

import com.example.bengkelrywin.models.User;

public interface LoginCallback {
    void onSuccess(boolean value, User user);
    void onError();
}
