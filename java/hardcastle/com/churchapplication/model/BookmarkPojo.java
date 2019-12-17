package hardcastle.com.churchapplication.model;

public class BookmarkPojo {

    private String bookmarkTitle;
    private String bookmarkDescrip;

    public BookmarkPojo(String bookmarkTitle, String bookmarkDescrip) {
        this.bookmarkTitle = bookmarkTitle;
        this.bookmarkDescrip = bookmarkDescrip;
    }

    public String getBookmarkTitle() {
        return bookmarkTitle;
    }

    public void setBookmarkTitle(String bookmarkTitle) {
        this.bookmarkTitle = bookmarkTitle;
    }

    public String getBookmarkDescrip() {
        return bookmarkDescrip;
    }

    public void setBookmarkDescrip(String bookmarkDescrip) {
        this.bookmarkDescrip = bookmarkDescrip;
    }
}
