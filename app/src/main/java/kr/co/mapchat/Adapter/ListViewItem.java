package kr.co.mapchat.Adapter;

public class ListViewItem {
    private String idStr;
    private String titleStr ;
    private String contentStr ;

    public void setId(String id) {
        idStr = id ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setContent(String content) {
        contentStr = content ;
    }

    public String getId() {
        return this.idStr ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getContent() {
        return this.contentStr ;
    }
}