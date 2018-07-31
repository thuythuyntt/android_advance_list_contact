package ntt.thuy.practice.framgia.listcontact.model;

/**
 * Created by thuy on 14/04/2018.
 */
public class Contact {
    private String mName;
    private String mPhone;

    Contact(String name, String phone) {
        this.mName = name;
        this.mPhone = phone;
    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }
}
