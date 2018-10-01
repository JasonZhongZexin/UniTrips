package com.sep.UniTrips.model.SignInModel;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LoginTaskManagerTest {

    private LoginTaskManager mLoginTaskManager;

    @Before
    public void createLoginTaskManager() {
        mLoginTaskManager = new LoginTaskManager();
    }

    @Test
    public void attemptLoginLogic_EmailValid() {
        String validEmail = "Matthew@gmail.com";
        String validPassword = "123456";
        assertThat(mLoginTaskManager.attemptLoginLogic(validEmail, validPassword), is(true));
    }

    @Test
    public void attemptLoginLogic_EmailEmpty() {
        String emptyEmail = "";
        String validPassword = "123456";
        assertThat(mLoginTaskManager.attemptLoginLogic(emptyEmail, validPassword), is(false));
    }

    @Test
    public void attemptLoginLogic_EmailInvalid() {
        String invalidEmail = "Matthewgmail.com";
        String validPassword = "123456";
        assertThat(mLoginTaskManager.attemptLoginLogic(invalidEmail, validPassword), is(false));

    }

    @Test
    public void attemptLoginLogic_PasswordValid() {
        String validEmail = "Matthew@gmail.com";
        String validPassword = "123456";
        assertThat(mLoginTaskManager.attemptLoginLogic(validEmail, validPassword), is(true));
    }

    @Test
    public void attemptLoginLogic_PasswordEmpty() {
        String emptyEmail = "Matthew@gmail.com";
        String validPassword = "";
        assertThat(mLoginTaskManager.attemptLoginLogic(emptyEmail, validPassword), is(false));
    }

    @Test
    public void attemptLoginLogic_PasswordInvalid() {
        String validEmail = "Matthew@gmail.com";
        String validPassword = "1234";
        assertThat(mLoginTaskManager.attemptLoginLogic(validEmail, validPassword), is(false));
    }
}