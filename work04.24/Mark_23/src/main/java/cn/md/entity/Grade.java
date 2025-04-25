package cn.md.entity;

public class Grade {
    private int xid;
    private String grade;

    public Grade() {
    }

    public Grade(int xid, String grade) {
        this.xid = xid;
        this.grade = grade;
    }

    public int getXid() {
        return xid;
    }

    public void setXid(int xid) {
        this.xid = xid;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "xid=" + xid +
                ", grade='" + grade + '\'' +
                '}';
    }
}
