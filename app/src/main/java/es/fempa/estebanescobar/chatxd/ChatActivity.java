/**
 * Activity used to handler chat's user interactions and the comunication with socket's threads.
 */

package es.fempa.estebanescobar.chatxd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;


public class ChatActivity extends AppCompatActivity {
    MessagesList messagesList;
    MessageInput inputView;
    MessagesListAdapter<Message> adapter;
    Hilos h;
    private Toolbar mTopToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        InitializeChatComunications();
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                adapter.addToStart(new Message(input.toString(), CurrentUsers.getInstance().getMe()), true);
                h.messageSender(input.toString());
                return true;
            }
        });
        InitializeToolBar();
    }

    public void InitializeChatComunications(){
        /**
         * Create thread instances for communication.
         * Name's special code for communicate it to the other device is _-_-_-_-_(Name)
         */
        h = new Hilos(this);
        h.messageListener();
        adapter =  new MessagesListAdapter<>("1", null); //Sets the adapter
        inputView = findViewById(R.id.input);
        messagesList = findViewById(R.id.messagesList);
        h.messageSender("_-_-_-_-_;"+CurrentUsers.getInstance().getMe().getName());
        messagesList.setAdapter(adapter);
    }

    public void InitializeToolBar(){
        /**
         * Initialize all tools and buttons from appbar.
         */
        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.userpic);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * Handle action bar item's clicks.
         */
        Intent intent;
        boolean ButtonClicked = false;
        switch (item.getItemId())
        {
            case R.id.action_info: //Info button clicked.
                intent = new Intent(ChatActivity.this, AboutActivity.class);
                startActivity(intent);
                ButtonClicked = true;
                break;
            case android.R.id.home: //Home back button clicked.
                finish();
                ButtonClicked = true;
                break;
            default:
                break;
        }

        return ButtonClicked || super.onOptionsItemSelected(item);
    }

    public void printMessage(String m){
        /**
         * Print incoming message on list.
         */
        final String message = m;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.addToStart(new Message(message, CurrentUsers.getInstance().getOther()), true);
            }
        });
    }




}
