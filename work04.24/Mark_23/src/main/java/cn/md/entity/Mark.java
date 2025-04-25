package cn.md.entity;

import java.util.Date;

public class Mark {
    private int markId;
    private int termId;
    private int studentId;
    private int courseId;
    private UserAccount userAccount;
    private Clz clz;
    private Course course;
    private Double usualScore;
    private Double examScore;
    private Double totalScore;

    public Mark() {
        this.userAccount = new UserAccount();
        this.clz = new Clz();
        this.course = new Course();
    }

    public Mark(int markId, int termId, int studentId,
                int courseId, UserAccount userAccount,
                Clz clz, Course course, Double usualScore,
                Double examScore, Double totalScore) {
        this.markId = markId;
        this.termId = termId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.userAccount = userAccount;
        this.clz = clz;
        this.course = course;
        this.usualScore = usualScore;
        this.examScore = examScore;
        this.totalScore = totalScore;
    }

    public int getMarkId() {
        return markId;
    }

    public void setMarkId(int markId) {
        this.markId = markId;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Double getUsualScore() {
        return usualScore;
    }

    public void setUsualScore(Double usualScore) {
        this.usualScore = usualScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
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

    @Override
    public String toString() {
        return "Mark{" +
                "markId=" + markId +
                ", termId=" + termId +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", userAccount=" + userAccount +
                ", clz=" + clz +
                ", course=" + course +
                ", usualScore=" + usualScore +
                ", examScore=" + examScore +
                ", totalScore=" + totalScore +
                '}';
    }
}
