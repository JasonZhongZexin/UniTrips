package com.sep.UniTrips.model.RestPassword;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ResetPasswordTaskManagerTest {

    private ResetPasswordTaskManager mResetPasswordTaskManager;

    @Before
    public void createResetPasswordManager() {
        mResetPasswordTaskManager = new ResetPasswordTaskManager();
    }

    @Test
    public void attemptResetPasswordLogic_EmailValid() {
        String validEmail = "Matthew@gmail.com";
        assertThat(mResetPasswordTaskManager.attemptRequestPasswordLogic(validEmail), is(false));
    }

    @Test
    public void attemptResetPasswordLogic_EmailEmpty() {
        String emptyEmail = "";
        assertThat(mResetPasswordTaskManager.attemptRequestPasswordLogic(emptyEmail), is(true));
    }

    @Test
    public void attemptResetPasswordLogic_EmailInvalid() {
        String invalidEmail = "Matthewgmail.com";
        assertThat(mResetPasswordTaskManager.attemptRequestPasswordLogic(invalidEmail), is(false));
    }

}