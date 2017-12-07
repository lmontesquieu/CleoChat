package com.technologies.cleo.cleomove.contactlist.ui.adapters;

import com.technologies.cleo.cleomove.entities.User;

/**
 * Created by Pepe on 10/20/16.
 */

public interface OnItemClickListener {
    void onItemClick(User user);
    void onItemLongClick(User user);
}
