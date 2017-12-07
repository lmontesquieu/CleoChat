package com.technologies.cleo.cleomove.contactlist;

import com.technologies.cleo.cleomove.contactlist.events.ContactListEvent;
import com.technologies.cleo.cleomove.contactlist.ui.ContactListView;
import com.technologies.cleo.cleomove.entities.User;
import com.technologies.cleo.cleomove.lib.EventBus;
import com.technologies.cleo.cleomove.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Pepe on 10/24/16.
 */

public class ContactListPresenterImpl implements ContactListPresenter {

    private EventBus eventBus;
    private ContactListView view;
    private ContactListInteractor listInteractor;
    private ContactListSessionInteractor sessionInteractor;

    public ContactListPresenterImpl(ContactListView view) {
        this.view = view;
        eventBus = GreenRobotEventBus.getInstance();
        this.listInteractor = new ContactListInteractorImpl();
        this.sessionInteractor = new ContactListSessionInteractorImpl();
    }

    @Override
    public void onPause() {
        sessionInteractor.changeConnectionStatus(User.OFFLINE);
        listInteractor.unsuscribe();

    }

    @Override
    public void onResume() {
        sessionInteractor.changeConnectionStatus(User.ONLINE);
        listInteractor.subscribe();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        listInteractor.destroyListener();
        view = null;
    }

    @Override
    public void signOff() {
        sessionInteractor.changeConnectionStatus(User.OFFLINE);
        listInteractor.unsuscribe();
        listInteractor.destroyListener();
        sessionInteractor.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return sessionInteractor.getCurrentUserEmail();
    }

    @Override
    public void removeContact(String email) {
        listInteractor.removeContact(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ContactListEvent event) {
        User user = event.getUser();
        switch (event.getEventType()) {
            case ContactListEvent.onContactAdded:
                onContactAdded(user);
                break;
            case ContactListEvent.onContactChanged:
                onContactChanged(user);
                break;
            case ContactListEvent.onContactRemoved:
                onContactRemoved(user);
                break;
        }

    }

    private void onContactAdded(User user) {
        if (view != null) {
            view.onContactAdded(user);
        }
    }

    private void onContactChanged(User user) {
        if (view != null) {
            view.onContactChanged(user);
        }
    }

    private void onContactRemoved(User user) {
        if (view != null) {
            view.onContactRemoved(user);
        }
    }

}
