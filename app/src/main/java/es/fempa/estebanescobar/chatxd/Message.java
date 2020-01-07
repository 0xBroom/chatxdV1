package es.fempa.estebanescobar.chatxd;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class Message implements IMessage {
    private static int idNum = 0;
    private String id;
    private String text;
    private IUser user;
    private Date date;

    public Message(){
        idNum++;
        id = idNum+"";
        text = "";
        user = null;
        date = new Date(System.currentTimeMillis());
    }

    public Message(String text, IUser user){
        idNum++;
        id = idNum+"";
        this.text = text;
        this.user = user;
        date = new Date(System.currentTimeMillis());
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
    public IUser getUser() {
        return user;
    }

    @Override
    public Date getCreatedAt() {
        return date;
    }
}
