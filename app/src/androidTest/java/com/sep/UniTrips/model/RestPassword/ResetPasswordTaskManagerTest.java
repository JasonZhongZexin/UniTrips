package com.sep.UniTrips.model.RestPassword;

import android.content.Context;
import android.test.mock.MockContext;

import com.sep.UniTrips.presenter.ResetPasswordPresenter;
import com.sep.UniTrips.view.ResetPasswordActivity;

import org.junit.Before;
import org.junit.Test;

public class ResetPasswordTaskManagerTest {

    private ResetPasswordPresenter mockPresenter;
    private Context mockContext;
    private ResetPasswordActivity mockResetPasswordActivity;

    private ResetPasswordTaskManager mockTaskManager;

    @Before
    public void initMocks() {
        mockContext = new MockContext();
        mockResetPasswordActivity = new ResetPasswordActivity();
        mockPresenter = createResetPasswordPresenter(mockContext, mockResetPasswordActivity);
        mockTaskManager = new ResetPasswordTaskManager(mockPresenter, mockContext);
    }

    private ResetPasswordPresenter createResetPasswordPresenter(
            Context mockContext, ResetPasswordActivity mockResetPasswordActivity) {

        // add in whens & dependencies

        return new ResetPasswordPresenter(mockContext, mockResetPasswordActivity);
    }

    @Test
    public void sendResetPasswordEmail() {
    }
}