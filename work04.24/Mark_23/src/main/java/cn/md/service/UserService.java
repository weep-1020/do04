package cn.md.service;

import cn.md.entity.UserAccount;

import java.util.Date;
import java.util.List;

public interface UserService {
    //用 1 对 1 方式完成
    public UserAccount findOneByPhone(String phone);  //登录使用

    //如下 2 个查询都用 左连接完成， 这样就适应教师和学生的要求
    public UserAccount findByUid(int uid);

    public int add(UserAccount userAccount);
    public int updatePwd(UserAccount userAccount);

    List<UserAccount> findByRole(String role);

    void update(UserAccount user);

    void remove(int uid,int is_deleted);

    List<UserAccount> findPageByRole(String role, Integer pagenum,
                                     Integer lines,String uname,
                                     Integer clzno, Date birthday,Integer xid);

    int getCount(String role ,String uname, Integer clzno, Date birthday, Integer xid);
    int updatePwdFromPhone(String password,String phone);
    List<UserAccount> findMark(String clzno,String cno);
}
