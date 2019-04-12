package com.onlineshoppingmall.communication.Database;

public class MessageRecord {

    private int id;

    private int oid;

    private long date;

    private String content;

    private int kind;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "MessageRecord{" +
                "id=" + id +
                ", oid=" + oid +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", kind=" + kind +
                '}';
    }
}
