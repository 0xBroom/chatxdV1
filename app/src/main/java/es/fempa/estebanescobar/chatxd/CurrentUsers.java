package es.fempa.estebanescobar.chatxd;

public class CurrentUsers {

   private Author me;
   private Author other;

    private static CurrentUsers instance = null;

    private CurrentUsers(){
        me = new Author("1");
        other = new Author("2");
    }

    //Get Instance
    public static CurrentUsers getInstance(){
        if (instance == null){
            instance = new CurrentUsers();
        }
        return instance;
    }

    public Author getMe() {
        return me;
    }

    public void setMe(Author me) {
        this.me = me;
    }

    public Author getOther() {
        return other;
    }

    public void setOther(Author other) {
        this.other = other;
    }
}
