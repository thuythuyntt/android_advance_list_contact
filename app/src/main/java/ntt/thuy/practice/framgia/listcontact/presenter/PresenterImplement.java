package ntt.thuy.practice.framgia.listcontact.presenter;

import java.util.ArrayList;

import ntt.thuy.practice.framgia.listcontact.view.MainActivity;
import ntt.thuy.practice.framgia.listcontact.model.Contact;
import ntt.thuy.practice.framgia.listcontact.model.ContactManager;

/**
 * Created by thuy on 14/04/2018.
 */
public class PresenterImplement implements Presenter {
    private MainActivity mView;
    private ContactManager mModel;

    public PresenterImplement(MainActivity view) {
        this.mView = view;
        mModel = new ContactManager(view.getApplicationContext());
    }

    @Override
    public void getAllContacts() {
        ArrayList<Contact> list = mModel.getAllContacts();
        if (list != null && list.size() > 0) {
            mView.getAllContactsSuccess(list);
        } else mView.getAllContactsError();
    }
}
