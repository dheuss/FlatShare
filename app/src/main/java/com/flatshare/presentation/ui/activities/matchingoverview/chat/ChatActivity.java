package com.flatshare.presentation.ui.activities.matchingoverview.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.flatshare.R;
import com.flatshare.presentation.presenters.chat.ChatPresenter;
import com.flatshare.presentation.presenters.chat.impl.ChatPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.matching.MatchingActivity;
import com.flatshare.threading.MainThreadImpl;

public class ChatActivity extends AbstractActivity implements ChatPresenter.View {

    private ImageButton couchChatButton;

    private static final String TAG = "ChatActivity";

    private ChatPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new ChatPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        couchChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatActivity.this, MatchingActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_chat;
    }

    private void bindView(){couchChatButton = (ImageButton)findViewById(R.id.couchChatBtn);}

    @Override
    public void showError(String message) {

    }
}