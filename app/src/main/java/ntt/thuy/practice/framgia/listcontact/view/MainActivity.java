package ntt.thuy.practice.framgia.listcontact.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import ntt.thuy.practice.framgia.listcontact.R;
import ntt.thuy.practice.framgia.listcontact.model.Contact;
import ntt.thuy.practice.framgia.listcontact.presenter.Presenter;
import ntt.thuy.practice.framgia.listcontact.presenter.PresenterImplement;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_CONTACTS;

public class MainActivity extends AppCompatActivity implements View {

    private static final int REQUEST_CODE_READ_CONTACT_PERMISSION = 100;
    private static final int REQUEST_CODE_CALL_PHONE_PERMISSION = 101;
    private MyAdapter mAdapter;
    private Presenter mPresenter;
    private Contact mSelectedContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerContacts = findViewById(R.id.recycle_view_contacts);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerContacts.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(new MyAdapter.OnItemClick() {
            @Override
            public void callPhone(Contact contact) {
                mSelectedContact = contact;
                makeAPhoneCall();
            }

            @Override
            public void sendMessage(Contact contact) {
                //todo something
            }

            @Override
            public void callVideo(Contact contact) {
                //todo something
            }

            @Override
            public void showDetailInformation(Contact contact) {
                //todo something
            }
        });

        mRecyclerContacts.setAdapter(mAdapter);

        mPresenter = new PresenterImplement(this);

        requestPermission();

    }

    private void requestPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
                mPresenter.getAllContacts();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        READ_CONTACTS
                }, REQUEST_CODE_READ_CONTACT_PERMISSION);
            }
        } else mPresenter.getAllContacts();
    }

    private void makeAPhoneCall() {
        String uri = "tel:" + mSelectedContact.getPhone();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    CALL_PHONE
            }, REQUEST_CODE_CALL_PHONE_PERMISSION);
            return;
        }
        startActivity(intent);
    }

    @Override
    public void getAllContactsSuccess(ArrayList<Contact> list) {
        mAdapter.setList(list);
    }

    @Override
    public void getAllContactsError() {
        Toast.makeText(this, R.string.message_get_contacts_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_READ_CONTACT_PERMISSION){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mPresenter.getAllContacts();
            } else {
                Toast.makeText(this, R.string.permission_notification, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
