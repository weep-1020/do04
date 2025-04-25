package cn.md.entity;

public class Clz {
    private String clzno;
    private String clzname;
    private int xid;

    public Clz() {
    }

    public Clz(String clzno, String clzname, int xid) {
        this.clzno = clzno;
        this.clzname = clzname;
        this.xid = xid;
    }

    public String getClzno() {
        return clzno;
    }

    public void setClzno(String clzno) {
        this.clzno = clzno;
    }

    public String getClzname() {
        return clzname;
    }

    public void setClzname(String clzname) {
        this.clzname = clzname;
    }

    public int getXid() {
        return xid;
    }

    public void setXid(int xid) {
        this.xid = xid;
    }

    @Override
    public String toString() {
        return "Clz{" +
                "clzno='" + clzno + '\'' +
                ", clzname='" + clzname + '\'' +
                ", xid=" + xid +
                '}';
    }
}
