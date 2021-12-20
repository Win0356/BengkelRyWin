package com.example.bengkelrywin.UnitTesting;

public interface LoginView {
    String getEmail();
    void showEmailError(String message);

    String getPassword();
    void showPasswordError(String message);

    void startMainLogin();

    void showLoginError(String message);
    void showErrorResponse(String message);
}
