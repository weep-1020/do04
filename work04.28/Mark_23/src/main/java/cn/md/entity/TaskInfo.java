package cn.md.entity;

public class TaskInfo {
    private int kid;
    private UserAccount userAccount; // 教师id-->tid
    private Clz clz;  // 班级编号-->clzno
    private Course course; // 课程编号-->cno

    public TaskInfo() {
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Clz getClz() {
        return clz;
    }

    public void setClz(Clz clz) {
        this.clz = clz;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "kid=" + kid +
                ", userAccount=" + userAccount +
                ", clz=" + clz +
                ", course=" + course +
                '}';
    }
}
