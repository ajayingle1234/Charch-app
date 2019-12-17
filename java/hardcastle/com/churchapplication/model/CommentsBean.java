package hardcastle.com.churchapplication.model;

public class CommentsBean {

    String userName,comment,profileImg;

    public CommentsBean(String userName, String comment, String profileImg) {
        this.userName = userName;
        this.comment = comment;
        this.profileImg = profileImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
