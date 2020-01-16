/**
 * This class handle Author information
 *
 * @id The Author's id used to make the relation with message object.
 * @name The Auhthor's name.
 * @avatar Author's Profie image.
 */

package es.fempa.estebanescobar.chatxd;

import com.stfalcon.chatkit.commons.models.IUser;

public class Author implements IUser {
   private String id;
   private String name;
   private String avatar;

    public Author(String id){
        this.id = id;
        name = "";
        avatar = "";
    }

    public Author()
    {
        id = "";
        name = "";
        avatar = "";
    }

    public Author(String id, String name)
    {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    public Author(String id, String name, String avatar)
    {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
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

    public void setName(String name) {
        this.name = name;
    }
}
