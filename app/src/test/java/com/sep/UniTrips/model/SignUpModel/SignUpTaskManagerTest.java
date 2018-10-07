package com.sep.UniTrips.model.SignUpModel;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SignUpTaskManagerTest {

    private SignUpTaskManager mSignUpTaskManager;

    @Before
    public void createResetPasswordManager() {
        mSignUpTaskManager = new SignUpTaskManager();
    }


    @Test
    public void attemptCreateAccount() {

    }

    @Test
    public void attemptCreateAccountLogic_EmailValid() {
        String validEmail = "Matthew@gmail.com";
        String validPassword = "123456";
        String validConfirmPassword = validPassword;

        assertThat(mSignUpTaskManager.attemptCreateAccountLogic(validEmail, validPassword, validConfirmPassword), is(true));
    }

    @Test
    public void attemptCreateAccountLogic_EmailEmpty() {
        String emptyEmail = "";
        String validPassword = "123456";
        String validConfirmPassword = validPassword;

        assertThat(mSignUpTaskManager.attemptCreateAccountLogic(emptyEmail, validPassword, validConfirmPassword), is(false));
    }

    @Test
    public void attemptCreateAccountLogic_EmailInvalid() {
        String invalidEmail = "Matthewgmail.com";
        String validPassword = "123456";
        String validConfirmPassword = validPassword;

        assertThat(mSignUpTaskManager.attemptCreateAccountLogic(invalidEmail, validPassword, validConfirmPassword), is(false));
    }

    @Test
    public void attemptCreateAccountLogic_PasswordsSame() {
        String invalidEmail = "Matthew@gmail.com";
        String validPassword = "123456";
        String validConfirmPassword = validPassword;

        assertThat(mSignUpTaskManager.attemptCreateAccountLogic(invalidEmail, validPassword, validConfirmPassword), is(true));
    }

    @Test
    public void attemptCreateAccountLogic_PasswordsDifferent() {
        String invalidEmail = "Matthew@gmail.com";
        String validPassword = "123456";
        String invalidConfirmPassword = "654321";

        assertThat(mSignUpTaskManager.attemptCreateAccountLogic(invalidEmail, validPassword, invalidConfirmPassword), is(false));
    }

}