package com.technologies.cleo.cleomove.contactlist;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.technologies.cleo.cleomove.contactlist.events.ContactListEvent;
import com.technologies.cleo.cleomove.domain.FirebaseHelper;
import com.technologies.cleo.cleomove.entities.User;
import com.technologies.cleo.cleomove.lib.EventBus;
import com.technologies.cleo.cleomove.lib.GreenRobotEventBus;


/**
 * Created by Pepe on 10/24/16.
 */
public class ContactListRepositoryImpl implements ContactListRepository {

    private EventBus eventBus;
    private FirebaseHelper helper;
    private ChildEventListener contactEventListener;


    public ContactListRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.helper = helper.getInstance();
    }

    @Override
    public void signOff() {
        helper.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return helper.getAuthUserEmail();
    }

    @Override
    public void removeContact(String email) {
        String currentUserEmail = helper.getAuthUserEmail();
        helper.getOneContactReference(currentUserEmail, email).removeValue();
        helper.getOneContactReference(email, currentUserEmail).removeValue();
    }

    @Override
    public void destroyListener() {
        contactEventListener = null;
    }

    @Override
    public void subscribeToContactListEvents() {
        if (contactEventListener == null) {
            contactEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot, ContactListEvent.onContactAdded);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot, ContactListEvent.onContactChanged);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    handleContact(dataSnapshot, ContactListEvent.onContactRemoved);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
        }
        helper.getMyContactsReference().addChildEventListener(contactEventListener);
    }

    private void handleContact(DataSnapshot dataSnapshot, int type) {
        String email = dataSnapshot.getKey();
        email = email.replace("_", ".");
        boolean online = ((Boolean)dataSnapshot.getValue()).booleanValue();
        User user = new User();
        Log.d("handleContact: Email:",email);
        Log.d("handleContact: Online:",String.valueOf(online));
        user.setEmail(email);
        user.setOnline(online);
        post(type, user);
    }

    private void post(int type, User user) {
        ContactListEvent event = new ContactListEvent();
        event.setEventType(type);
        event.setUser(user);
        eventBus.post(event);
    }

    @Override
    public void unsubscribeToContactListEvents() {
        if (contactEventListener != null) {
            helper.getMyContactsReference().removeEventListener(contactEventListener);
        }

    }

    @Override
    public void changeConnectionStatus(boolean online) {
        helper.changeUserConnectionStatus(online);
    }
}
