package com.cs595.uwm.chatbylocation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.cs595.uwm.chatbylocation.R;
import com.cs595.uwm.chatbylocation.service.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by Jason on 3/15/2017.
 */

public class ChatNameSelectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_name_select_layout);
    }

    public void registerChatName(View v) {
        EditText input = (EditText) findViewById(R.id.chatname);
        String name = input.getText().toString();

        //if(name.equals("") || !Database.getUsernameUnique(name)){
            //TODO: display error message

        //} else {

        Database.createUser();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            //username is updated
                            Intent intent = new Intent(ChatNameSelectionActivity.this, SelectActivity.class);
                            startActivity(intent);
                        }
                    }
                });


        //}


    }
}
