package com.example.whatsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.R;
import com.example.whatsapp.model.MessageModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> MessageModels;
    Context context;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> MessageModels, Context context) {
        this.MessageModels = MessageModels;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position){
        if (MessageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }else {
            return RECEIVER_VIEW_TYPE;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new senderViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return new recieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        MessageModel messageModel = MessageModels.get(position);

        if (holder.getClass() == senderViewHolder.class){
            ((senderViewHolder)holder).senderMsg.setText(messageModel.getMessage());
        }else {
            ((recieverViewHolder)holder).recieverMsg.setText(messageModel.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return MessageModels.size();
    }




    public class recieverViewHolder extends RecyclerView.ViewHolder{

        TextView recieverMsg,recieverTime;

        public recieverViewHolder(@NonNull View itemView) {
            super(itemView);
            recieverMsg = itemView.findViewById(R.id.recievertxt);
            recieverTime = itemView.findViewById(R.id.recievertime);
        }

    }


    public class senderViewHolder extends RecyclerView.ViewHolder{

        TextView senderMsg,senderTime;

        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.sendermessage);
            senderTime = itemView.findViewById(R.id.sendertime);
        }

    }
}
