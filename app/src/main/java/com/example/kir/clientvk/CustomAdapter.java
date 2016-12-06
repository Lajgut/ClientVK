package com.example.kir.clientvk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{
    private ArrayList<String> users, messages;
    private Context context;
    private VKList<VKApiDialog> list;



    public CustomAdapter(Context context, ArrayList<String> users, ArrayList<String> messages, VKList<VKApiDialog> list){
        this.context = context;
        this.users = users;
        this.messages = messages;
        this.list = list;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        SetData setData = new SetData();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.style_list_view,null);

        setData.userName = (TextView) view1.findViewById(R.id.user);
        setData.msg = (TextView) view1.findViewById(R.id.msg);

        setData.userName.setText(users.get(position));
        setData.msg.setText(messages.get(position));

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKRequest request = new VKRequest("messages.send", VKParameters.from(VKApiConst.USER_ID, list.get(position).message.user_id, VKApiConst.MESSAGE, "Test msg"));

                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        System.out.println("сообщение отправлено");
                    }
                });
            }
        });

        return view1;
    }

    public class SetData{
        TextView userName, msg;
    }
}
