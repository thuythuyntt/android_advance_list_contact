package ntt.thuy.practice.framgia.listcontact.view;

import java.util.ArrayList;

import ntt.thuy.practice.framgia.listcontact.model.Contact;

/**
 * Created by thuy on 14/04/2018.
 */
public interface View {
    void getAllContactsSuccess(ArrayList<Contact> list);
    void getAllContactsError();
}
