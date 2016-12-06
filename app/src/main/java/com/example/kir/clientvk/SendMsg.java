package com.example.kir.clientvk;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Kir on 06.12.2016.
 */

public class SendMsg extends Activity {

    ArrayList<String> incMsgList = new ArrayList<>();
    ArrayList<String> outMsgList = new ArrayList<>();
    int id = 0;

    EditText text;
    Button send;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        incMsgList = getIntent().getStringArrayListExtra("inc");
        outMsgList = getIntent().getStringArrayListExtra("out");

    }
}
