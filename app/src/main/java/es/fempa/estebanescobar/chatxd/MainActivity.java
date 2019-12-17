package es.fempa.estebanescobar.chatxd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static int max_userid; //Para tener controlado el número de ids creados.
    public static int max_messid;
    MessagesList messagesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MessagesListAdapter<Message> adapter = new MessagesListAdapter<>("1", null);
        messagesList = (MessagesList)findViewById(R.id.messagesList);
        //messagesList = getlist;
        //messagesList = ;
        messagesList.setAdapter(adapter);
    }

    public class Author implements IUser {

        /*...*/

        private String id;
        private String name;
        private String avatar;

        public Author(){
            super();
            max_userid++;
            String newid = max_userid+"";
            id= newid;
            name = "Default";
            avatar = null;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getAvatar() {
            return avatar;
        }
    }

    public class Message implements IMessage {

        /*...*/

        private String id;
        public String text;
        public Author author;
        public Date createdAt;

        public Message(){
            max_messid++;
            id = max_messid+"";
            text = "Hola";
            Author author = new Author();
            createdAt = new Date();
        }


        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getText() {
            return text;
        }

        @Override
        public Author getUser() {
            return author;
        }

        @Override
        public Date getCreatedAt() {
            return createdAt;
        }
    }
}
