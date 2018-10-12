package com.sep.UniTrips.model.ImportCalendar;

import android.app.Application;

import com.sep.UniTrips.R;
import com.sep.UniTrips.model.TestingStructs;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ImportCalendarTaskManagerTest {

    private ImportCalendarTaskManager mImportCalendarTaskManager;
    private static Application mContext;
    TestingStructs.AttemptGetCalendarLogicErrors mResult;

    @Before
    public void createImportCalendarTaskManager() {
        mImportCalendarTaskManager = new ImportCalendarTaskManager(mContext);
    }

    @Test
    public void attemptGetCalendarLogic_PasswordEmpty() {
        String studentID = "12345678";
        String password = "";
        String year = "2000";
        String calendarName = "exampleCalendarName";
        mResult = mImportCalendarTaskManager.attemptGetCalendarLogic(
                studentID, password, year, calendarName);

        assertTrue(mResult.cancel);
        assertThat(mResult.passwordError, is(mContext.getString(R.string.error_field_required)));
        assertThat(mResult.iDError, is(""));
        assertThat(mResult.calendarNameError, is(""));
    }

    @Test
    public void attemptGetCalendarLogic_PasswordInvalid() {
        String studentID = "12345678";
        String password = "12345";
        String year = "2000";
        String calendarName = "exampleCalendarName";
        mResult = mImportCalendarTaskManager.attemptGetCalendarLogic(
                studentID, password, year, calendarName);

        assertTrue(mResult.cancel);
        assertThat(mResult.passwordError, is(mContext.getString(R.string.error_invalid_password)));
        assertThat(mResult.iDError, is(""));
        assertThat(mResult.calendarNameError, is(""));
    }

    @Test
    public void attemptGetCalendarLogic_StudentIdEmpty() {
        String studentID = "";
        String password = "123456";
        String year = "2000";
        String calendarName = "exampleCalendarName";
        mResult = mImportCalendarTaskManager.attemptGetCalendarLogic(
                studentID, password, year, calendarName);

        assertTrue(mResult.cancel);
        assertThat(mResult.passwordError, is(""));
        assertThat(mResult.iDError, is(mContext.getString(R.string.error_field_required)));
        assertThat(mResult.calendarNameError, is(""));
    }

    @Test
    public void attemptGetCalendarLogic_StudentIdInvalid() {
        String studentID = "1234567"; // 7 chars
        String password = "12345";
        String year = "2000";
        String calendarName = "exampleCalendarName";
        mResult = mImportCalendarTaskManager.attemptGetCalendarLogic(
                studentID, password, year, calendarName);

        assertTrue(mResult.cancel);
        assertThat(mResult.passwordError, is(""));
        assertThat(mResult.iDError, is(mContext.getString(R.string.error_invalid_email)));
        assertThat(mResult.calendarNameError, is(""));
    }

    @Test
    public void attemptGetCalendarLogic_CalendarNameInvalid() {
        String studentID = "1234567"; // 7 chars
        String password = "12345";
        String year = "2000";
        String calendarName = "";
        mResult = mImportCalendarTaskManager.attemptGetCalendarLogic(
                studentID, password, year, calendarName);

        assertTrue(mResult.cancel);
        assertThat(mResult.passwordError, is(""));
        assertThat(mResult.iDError, is(""));
        assertThat(mResult.calendarNameError, is(mContext.getString(R.string.error_field_required)));
    }

    @Test
    public void attemptGetCalendarLogic_DetailsValid() {
        String studentID = "12345678";
        String password = "123456";
        String year = "2000";
        String calendarName = "exampleCalendarName";
        mResult = mImportCalendarTaskManager.attemptGetCalendarLogic(
                studentID, password, year, calendarName);

        assertFalse(mResult.cancel);
        assertThat(mResult.passwordError, is(""));
        assertThat(mResult.iDError, is(""));
        assertThat(mResult.calendarNameError, is(""));
    }


}