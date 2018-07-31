package ntt.thuy.practice.framgia.listcontact.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;


/**
 * Created by thuy on 14/04/2018.
 */
public class ContactManager {

    private Context mContext;

    public ContactManager(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> list = new ArrayList<>();
        @SuppressLint("Recycle")
        Cursor c = mContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        assert c != null;
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            String num;
            if (Integer.valueOf(hasNumber) == 1) {
                @SuppressLint("Recycle")
                Cursor numbers = mContext.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null,
                        null);
                assert numbers != null;
                if (numbers.moveToNext()) {
                    num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    list.add(new Contact(name, num));
                }
            }
            c.moveToNext();
        }
        return list;
    }
}
