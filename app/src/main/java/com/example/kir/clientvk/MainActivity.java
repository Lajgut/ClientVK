package com.example.kir.clientvk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiUsers;
import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKApiGetDialogResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private String[] scope = new String[] {VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL};
    private ListView lv;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VKSdk.login(this, scope);
        lv = (ListView) findViewById(R.id.lv);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final VKRequest request = VKApi.messages().getDialogs(VKParameters.from(VKApiConst.COUNT,10));
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);

                        VKApiGetDialogResponse getDialogResponse = (VKApiGetDialogResponse) response.parsedModel;

                        VKList<VKApiDialog> list = getDialogResponse.items;

                        ArrayList<String> messages = new ArrayList<>();
                        ArrayList<String> usersList = new ArrayList<>();

                        for (VKApiDialog msg : list){
                            usersList.add(String.valueOf(msg.message.user_id));
                            messages.add(msg.message.body);
                        }

                        lv.setAdapter(new CustomAdapter(MainActivity.this,usersList,messages,list));

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS,"first_name","last_name"));
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);

                        VKList list = (VKList) response.parsedModel;

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,list);

                        lv.setAdapter(arrayAdapter);
                    }
                });
                Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_LONG).show();

            }
            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(),"LoginError",Toast.LENGTH_LONG).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
