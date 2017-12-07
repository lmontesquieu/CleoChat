package com.technologies.cleo.cleomove.addcontact;

/**
 * Created by Pepe on 10/25/16.
 */
public class AddContactInteractorImpl implements AddContactInteractor {

    AddContactRepository repository;

    public AddContactInteractorImpl() {
        this.repository = new AddContactRepositoryImpl();
    }

    @Override
    public void execute(String email) {
        repository.addContact(email);
    }
}
