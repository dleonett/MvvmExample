package com.example.danielleonett.mvvmexample.data.remote.response;

/**
 * Created by daniel.leonett on 1/02/2018.
 */

public class ErrorResponse {

    private ErrorWrapper error;

    public ErrorWrapper getError() {
        return error;
    }

    private class ErrorWrapper {

        private int status;
        private String message;

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

    }
}
