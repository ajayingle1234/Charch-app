package hardcastle.com.churchapplication.model;

import android.text.style.BackgroundColorSpan;

public class HighLightPojo {

    private int backgroundColor;
    private int start,end,flag,x,y;
    String title;

    public HighLightPojo(int backgroundColor, int start, int end, int flag, int x, int y, String title) {
        this.backgroundColor = backgroundColor;
        this.start = start;
        this.end = end;
        this.flag = flag;
        this.x = x;
        this.y = y;
        this.title = title;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
