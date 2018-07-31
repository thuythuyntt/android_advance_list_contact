package ntt.thuy.practice.framgia.listcontact.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ntt.thuy.practice.framgia.listcontact.R;
import ntt.thuy.practice.framgia.listcontact.model.Contact;

/**
 * Created by thuy on 14/04/2018.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int CONTACT_TYPE = 1;
    public static final int SELECT_CONTACT_TYPE = 2;

    private ArrayList<Contact> contacts;
    private OnItemClick mOnItemClick;

    private int index = -1;

    MyAdapter(OnItemClick mOnItemClick) {
        this.mOnItemClick = mOnItemClick;
    }

    public void setList(ArrayList<Contact> list) {
        this.contacts = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == CONTACT_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
            return new MyContactHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_contact, parent, false);
            return new MySelectContactHoler(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != index) {
            ((MyContactHolder) holder).bindData(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == index) return SELECT_CONTACT_TYPE;
        return CONTACT_TYPE;
    }

    @Override
    public int getItemCount() {
        if (index == -1) {
            return contacts == null ? 0 : contacts.size();
        }
        return contacts == null ? 0 : contacts.size() + 1;
    }

    class MyContactHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPhone, textLetter;

        MyContactHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.tv_name);
            textViewPhone = itemView.findViewById(R.id.tv_phone);
            textLetter = itemView.findViewById(R.id.text_representative_letter);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    index = getAdapterPosition() + 1;
                    notifyDataSetChanged();
                }
            });
        }

        void bindData(int position) {
            textViewName.setText(contacts.get(position).getName());
            textViewPhone.setText(contacts.get(position).getPhone());
            textLetter.setText(String.format("%s", contacts.get(position).getName().charAt(0)));
        }
    }

    class MySelectContactHoler extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imagePhone, imageMessage, imageVideo, imageInformation;

        MySelectContactHoler(View itemView) {
            super(itemView);

            imagePhone = itemView.findViewById(R.id.image_phone_call);
            imagePhone.setOnClickListener(this);

            imageMessage = itemView.findViewById(R.id.image_message);
            imageMessage.setOnClickListener(this);

            imageVideo = itemView.findViewById(R.id.image_video_call);
            imageVideo.setOnClickListener(this);

            imageInformation = itemView.findViewById(R.id.image_information);
            imageInformation.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_phone_call:
                    mOnItemClick.callPhone(contacts.get(index-1));
                    break;
                case R.id.image_message:
                    mOnItemClick.sendMessage(contacts.get(index-1));
                    break;
                case R.id.image_video_call:
                    mOnItemClick.callVideo(contacts.get(index-1));
                    break;
                case R.id.image_information:
                    mOnItemClick.showDetailInformation(contacts.get(index-1));
                    break;
                default:
                    break;
            }
        }
    }

    interface OnItemClick {
        void callPhone(Contact contact);

        void sendMessage(Contact contact);

        void callVideo(Contact contact);

        void showDetailInformation(Contact contact);
    }

}
