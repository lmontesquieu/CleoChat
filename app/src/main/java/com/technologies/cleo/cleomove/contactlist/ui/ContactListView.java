package com.technologies.cleo.cleomove.contactlist.ui;

import com.technologies.cleo.cleomove.entities.User;

/**
 * Created by Pepe on 10/20/16.
 */

public interface ContactListView {
    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);

}
