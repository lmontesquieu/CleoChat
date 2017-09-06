package com.technologies.cleo.cleochat.domain;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.technologies.cleo.cleochat.entities.User;

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
        //Log.e(TAG, "getInstance");
        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper() {
        //Log.e(TAG,"FirebaseHelper");
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
        //Log.e(TAG, "getDataReference");
        return mDatabase;
    }

    public FirebaseAuth getMyAuth() {
        //Log.e(TAG, "getMyAuth");
        return mAuth;
    }

    public FirebaseAuth.AuthStateListener getMyAuthListener() {
        //Log.e(TAG, "getMyAuthListener");
        return mAuthListener;
    }

    public String getAuthUserEmail() {
        //Log.e(TAG, "getAuthUserEmail");
        String email = null;
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        if (authUser != null) {
            email = authUser.getEmail();
        }
        //Log.e(TAG, email);
        return email;
    }

    public DatabaseReference getUserReference(String email) {
        //Log.e(TAG, "getUserReference");
        DatabaseReference userReference = null;
        if (email != null) {
            String emailKey = email.replace(".","_");
            userReference = mDatabase.getRoot().child(USERS_PATH).child(emailKey);
        }
        return userReference;
    }

    public DatabaseReference getMyUserReference() {
        //Log.e(TAG, "getMyUserReference");
        return getUserReference(getAuthUserEmail());
    }

    public DatabaseReference getContactsReference(String email) {
        //Log.e(TAG, "getContactsReference");
        return getUserReference(email).child(CONTACTS_PATH);
    }

    public DatabaseReference getMyContactsReference() {
        //Log.e(TAG, "getMyContactsReference");
        return getContactsReference(getAuthUserEmail());
    }

    public DatabaseReference getOneContactReference(String mainEmail, String childEmail) {
        //Log.e(TAG, "getOneContactReference");
        String childKey = childEmail.replace(".","_");
        return getUserReference(mainEmail).child(CONTACTS_PATH).child(childKey);
    }

    public DatabaseReference getChatsReference(String receiver) {
        //Log.e(TAG, "getChatsReference");
        String keySender = getAuthUserEmail().replace(".","_");
        String keyReceiver = receiver.replace(".","_");

        String keyChat = keySender + SEPARATOR + keyReceiver;
        if (keySender.compareTo(keyReceiver) > 0) {
            keyChat = keyReceiver + SEPARATOR + keySender;
        }
        return mDatabase.getRoot().child(CHATS_PATH).child(keyChat);
    }

    public void changeUserConnectionStatus(boolean online) {
        //Log.e(TAG, "changeUserConnectionStatus");
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
        //Log.e(TAG, "signOff");
        notifyContactsOfConnectionChange(User.OFFLINE, true);
    }

    private void notifyContactsOfConnectionChange(final boolean online, final boolean signOff) {
        //Log.e(TAG, "notifyContactsOfConnectionChange");
        final String myEmail = getAuthUserEmail();
        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String email = child.getKey();
                    DatabaseReference reference = getOneContactReference(email, myEmail);
                    reference.setValue(online);
                    Log.e("onDataChange",email);
                    Log.e("status:",String.valueOf(online));
                }
                if (signOff) {
                    //Log.e(TAG, "signOut");
                    //FirebaseAuth.getInstance().signOut();
                    mAuth.signOut();
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e(TAG, "firebaseError");
            }
        });
    }

}
