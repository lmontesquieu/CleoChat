package com.technologies.cleo.cleomove.chat.ui.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technologies.cleo.cleomove.R;
import com.technologies.cleo.cleomove.entities.ChatMessage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Pepe on 10/25/16.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context context;
    private List<ChatMessage> chatMessages;
    private final static String TAG = "ChatAdapter";

    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        Log.d(TAG,"onBindViewHolder");
        String msg = chatMessage.getMsg();
        holder.txtMessage.setText(msg);
        Log.d(TAG, msg);
        Log.d(TAG, String.valueOf(position));

        int color = fetchColor(R.attr.colorPrimary);
        int gravity = Gravity.LEFT;

        if (!chatMessage.isSentByMe()) {
            color = fetchColor(R.attr.colorAccent);
            gravity = Gravity.RIGHT;
        }

        holder.txtMessage.setBackgroundColor(color);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.txtMessage.getLayoutParams();
        params.gravity = gravity;
        holder.txtMessage.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public void add(ChatMessage msg) {
        Log.d(TAG,"add");
        if (!chatMessages.contains(msg)) {
            Log.d(TAG,msg.getMsg());
            chatMessages.add(msg);
            notifyDataSetChanged();
        }
    }

    public int fetchColor(int color) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data,
                new int[] { color });
        int returnColor = a.getColor(0, 0);
        a.recycle();
        return returnColor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtMessage)
        TextView txtMessage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
