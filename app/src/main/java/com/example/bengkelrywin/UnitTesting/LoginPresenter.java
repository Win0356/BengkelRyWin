package com.example.bengkelrywin.UnitTesting;

import com.example.bengkelrywin.models.Login;
import com.example.bengkelrywin.models.User;

public class LoginPresenter {
    private LoginView view;
    private LoginService service;
    private LoginCallback callback;
    private Login login;

    public LoginPresenter(LoginView view, LoginService service) {
        this.view = view;
        this.service = service;
    }

    public void onLoginClicked() {
        String regexEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String regexPassword = "^[A-Za-z0-9]+$";

        if (view.getEmail().isEmpty()) {
            view.showEmailError("Email tidak boleh kosong");
            return;
        } else if (!view.getEmail().matches(regexEmail)) {
            view.showEmailError("Format email salah");
            return;
        } if (view.getPassword().isEmpty()) {
            view.showPasswordError("Password tidak boleh kosong");
            return;
        } else if (!view.getPassword().matches(regexPassword)) {
            view.showPasswordError("Format password salah");
            return;
        } else {
            service.login(view, login, new LoginCallback() {
                @Override
                public void onSuccess(boolean value, User user)
                {
                    view.startMainLogin();
                }

                @Override
                public void onError() {

                }
            });
            return;
        }
    }
}
