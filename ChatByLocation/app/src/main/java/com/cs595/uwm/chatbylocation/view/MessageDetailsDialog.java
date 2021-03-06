package com.cs595.uwm.chatbylocation.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cs595.uwm.chatbylocation.R;
import com.cs595.uwm.chatbylocation.controllers.MuteController;
import com.cs595.uwm.chatbylocation.objModel.UserIcon;
import com.cs595.uwm.chatbylocation.service.Database;

/**
 * Created by Nathan on 3/29/17.
 */

public class MessageDetailsDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get layout and set to dialog
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View dialogView = inflater.inflate(R.layout.message_details_layout, null);
        builder.setView(dialogView);

        final String userName = getArguments().getString(ChatActivity.NAME_ARGUMENT);
        final int userIcon = getArguments().getInt(ChatActivity.ICON_ARGUMENT);

        final TextView userNameView = (TextView) dialogView.findViewById(R.id.userName);
        final ImageView userImageView = (ImageView) dialogView.findViewById(R.id.userImage);
        final Button banButton = (Button) dialogView.findViewById(R.id.banUser);
        final ToggleButton muteButton = (ToggleButton) dialogView.findViewById(R.id.blockUser);

        userNameView.setText(userName);

        String userId = Database.getUserId(userName);

        if (userIcon == 0) {
            Bitmap image = Database.getUserImage(userId);
            if (image != null) {
                userImageView.setImageBitmap(image);
            } else {
                userImageView.setImageResource(UserIcon.NONE_RESOURCE);
            }
        } else {
            userImageView.setImageResource(userIcon);
            userImageView.setPadding(35,35,35,35);
        }

        // Make sure mute button reflects user's muted status
        MuteController.adjustMuteButton(muteButton, userName);

        // Make 'Ban' button visible when user is admin of current room
        if (Database.isCurrentUserAdminOfRoom()) {
            banButton.setVisibility(Button.VISIBLE);
        } else {
            banButton.setVisibility(View.GONE);
        }

        // Close message details dialog on ban click
        banButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if (activity instanceof ChatActivity) {
                    ((ChatActivity) activity).banUserClick(v);
                }
                getDialog().cancel();
            }
        });

        return builder.create();
    }
}
