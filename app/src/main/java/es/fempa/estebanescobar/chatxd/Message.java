package es.fempa.estebanescobar.chatxd;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class Message implements IMessage {
    private static int idNum;
    private String id;
    private String text;
    private IUser user;

    public Message(){
        idNum++;
        id = idNum+"";
        text = "";
        user = null;
    }

    public Message(String text, IUser user){
        idNum++;
        id = idNum+"";
        this.text = text;
        this.user = user;
    }


    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public IUser getUser() {
        return null;
    }

    @Override
    public Date getCreatedAt() {
        return null;
    }
}
