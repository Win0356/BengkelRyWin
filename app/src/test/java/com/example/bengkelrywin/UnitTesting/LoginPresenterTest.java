package com.example.bengkelrywin.UnitTesting;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
    @Mock
    private LoginView view;
    @Mock
    private LoginService service;
    private LoginPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view, service);
    }

    @Test
    public void shouldShowErrorMessageWhenEmailIsEmpty() throws Exception {
        System.out.println("\n\n" + "Testing Pertama : Inputan Email Kosong");

        when(view.getEmail()).thenReturn("");
        System.out.println("Email : " + view.getEmail());

        presenter.onLoginClicked();
        verify(view).showEmailError("Email tidak boleh kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenEmailFormatInvalid() throws Exception {
        System.out.println("\n\n" + "Testing Kedua : Format Inputan Email Salah");

        when(view.getEmail()).thenReturn("testgmailcom");
        System.out.println("Email : " + view.getEmail());

        presenter.onLoginClicked();
        verify(view).showEmailError("Format email salah");
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        System.out.println("\n\n" + "Testing Ketiga : Inputan Password Kosong");

        when(view.getEmail()).thenReturn("test@gmail.com");
        System.out.println("Email : " + view.getEmail());

        when(view.getPassword()).thenReturn("");
        System.out.println("Password : " + view.getPassword());

        presenter.onLoginClicked();
        verify(view).showPasswordError("Password tidak boleh kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordFormatInvalid() throws Exception {
        System.out.println("\n\n" + "Testing Keempat : Format Inputan Password Salah");

        when(view.getEmail()).thenReturn("test@gmail.com");
        System.out.println("Email : " + view.getEmail());

        when(view.getPassword()).thenReturn("Test_Password");
        System.out.println("Password : " + view.getPassword());

        presenter.onLoginClicked();
        verify(view).showPasswordError("Format password salah");
    }
}