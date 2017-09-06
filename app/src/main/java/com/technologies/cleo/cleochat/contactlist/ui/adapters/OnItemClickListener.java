package com.technologies.cleo.cleochat.contactlist.ui.adapters;

import com.technologies.cleo.cleochat.entities.User;

/**
 * Created by Pepe on 10/20/16.
 */

public interface OnItemClickListener {
    void onItemClick(User user);
    void onItemLongClick(User user);
}
