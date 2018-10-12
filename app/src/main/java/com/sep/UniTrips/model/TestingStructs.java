package com.sep.UniTrips.model;

public class TestingStructs {

    public static class AttemptGetCalendarLogicErrors {
        public String passwordError;
        public String iDError;
        public String calendarNameError;
        public boolean cancel;

        public AttemptGetCalendarLogicErrors() {
            this.passwordError = "";
            this.iDError = "";
            this.calendarNameError = "";
            cancel = false;
        }
    }

}
