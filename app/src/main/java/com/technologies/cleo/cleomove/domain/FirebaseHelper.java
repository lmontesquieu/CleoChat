package com.technologies.cleo.cleomove.domain;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.technologies.cleo.cleomove.entities.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pepe on 10/12/2016.
 */

public class FirebaseHelper {

    private static final String TAG = "FirebaseHelper";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private final static String SEPARATOR = "___";
    private final static String CHATS_PATH = "chats";
    private final static String USERS_PATH = "users";
    private final static String CONTACTS_PATH = "contacts";

    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance() {
        //Log.d(TAG, "getInstance");
        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper() {
        //Log.d(TAG,"FirebaseHelper");
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.mAuth = FirebaseAuth.getInstance();

        this.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    public DatabaseReference getDataReference() {
        //Log.d(TAG, "getDataReference");
        return mDatabase;
    }

    public FirebaseAuth getMyAuth() {
        //Log.d(TAG, "getMyAuth");
        return mAuth;
    }

    public FirebaseAuth.AuthStateListener getMyAuthListener() {
        //Log.d(TAG, "getMyAuthListener");
        return mAuthListener;
    }

    public String getAuthUserEmail() {
        //Log.d(TAG, "getAuthUserEmail");
        String email = null;
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        if (authUser != null) {
            email = authUser.getEmail();
        }
        //Log.d(TAG, email);
        return email;
    }

    public DatabaseReference getUserReference(String email) {
        //Log.d(TAG, "getUserReference");
        DatabaseReference userReference = null;
        if (email != null) {
            String emailKey = email.replace(".","_");
            userReference = mDatabase.getRoot().child(USERS_PATH).child(emailKey);
        }
        return userReference;
    }

    public DatabaseReference getMyUserReference() {
        //Log.d(TAG, "getMyUserReference");
        return getUserReference(getAuthUserEmail());
    }

    public DatabaseReference getContactsReference(String email) {
        //Log.d(TAG, "getContactsReference");
        return getUserReference(email).child(CONTACTS_PATH);
    }

    public DatabaseReference getMyContactsReference() {
        //Log.d(TAG, "getMyContactsReference");
        return getContactsReference(getAuthUserEmail());
    }

    public DatabaseReference getOneContactReference(String mainEmail, String childEmail) {
        //Log.d(TAG, "getOneContactReference");
        String childKey = childEmail.replace(".","_");
        return getUserReference(mainEmail).child(CONTACTS_PATH).child(childKey);
    }

    public DatabaseReference getChatsReference(String receiver) {
        //Log.d(TAG, "getChatsReference");
        String keySender = getAuthUserEmail().replace(".","_");
        String keyReceiver = receiver.replace(".","_");

        String keyChat = keySender + SEPARATOR + keyReceiver;
        if (keySender.compareTo(keyReceiver) > 0) {
            keyChat = keyReceiver + SEPARATOR + keySender;
        }
        return mDatabase.getRoot().child(CHATS_PATH).child(keyChat);
    }

    public void changeUserConnectionStatus(boolean online) {
        //Log.d(TAG, "changeUserConnectionStatus");
        if (getMyUserReference() != null) {
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("online", online);
            getMyUserReference().updateChildren(updates);
            notifyContactsOfConnectionChange(online);
        }
    }

    public void notifyContactsOfConnectionChange(boolean online) {
        notifyContactsOfConnectionChange(online, false);
    }

    public void signOff() {
        //Log.d(TAG, "signOff");
        notifyContactsOfConnectionChange(User.OFFLINE, true);
    }

    private void notifyContactsOfConnectionChange(final boolean online, final boolean signOff) {
        //Log.d(TAG, "notifyContactsOfConnectionChange");
        final String myEmail = getAuthUserEmail();
        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String email = child.getKey();
                    DatabaseReference reference = getOneContactReference(email, myEmail);
                    reference.setValue(online);
                    Log.d("onDataChange",email);
                    Log.d("status:",String.valueOf(online));
                }
                if (signOff) {
                    //Log.d(TAG, "signOut");
                    //FirebaseAuth.getInstance().signOut();
                    mAuth.signOut();
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.d(TAG, "firebaseError");
            }
        });
    }

}
