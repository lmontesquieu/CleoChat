package com.technologies.cleo.cleochat.addcontact.events;

/**
 * Created by Pepe on 10/24/16.
 */
public class AddContactEvent {
    boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
