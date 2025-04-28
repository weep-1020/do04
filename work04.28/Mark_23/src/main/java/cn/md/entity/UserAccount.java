package cn.md.entity;

import java.util.Date;

public class UserAccount {
    private int uid; // 用户ID
    private String uname; // 用户名称
    private String phone; //手机号码 账号
    private String password; // 账号密码
    private String role; // 人员权限 级别
    private Clz clz;
    private int sex; //人员性别
    private Date birthday; //生日
    private int pic; //图片ID
    private Double excellence; //优秀值 学生每科的平均分 教师学生评分的平均分
    private int xid; //学期
    private String grade; //年级
    private int is_deleted; //是否被删除，1是已删除，0是未删除

    public UserAccount() {
        this.clz = new Clz();
    }

    public UserAccount(int uid, String uname, String phone,
                       String password, String role, Clz clz, int sex,
                       Date birthday, int pic, Double excellence,
                       int xid, String grade, int is_deleted) {
        this.uid = uid;
        this.uname = uname;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.clz = clz;
        this.sex = sex;
        this.birthday = birthday;
        this.pic = pic;
        this.excellence = excellence;
        this.xid = xid;
        this.grade = grade;
        this.is_deleted = is_deleted;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Clz getClz() {
        return clz;
    }

    public void setClz(Clz clz) {
        this.clz = clz;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public Double getExcellence() {
        return excellence;
    }

    public void setExcellence(Double excellence) {
        this.excellence = excellence;
    }

    public int getXid() {
        return xid;
    }

    public void setXid(int xid) {
        this.xid = xid;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        DateFormatExample dateFormatExample = new DateFormatExample();
        return "UserAccount{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", clz=" + clz +
                ", sex=" + sex +
                ", birthday=" +  dateFormatExample.date(birthday)+
                ", pic='" + pic + '\'' +
                ", excellence=" + excellence +
                ", xid=" + xid +
                ", grade='" + grade + '\'' +
                ", is_deleted=" + is_deleted +
                '}';
    }
}
