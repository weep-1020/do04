package cn.md.dao;

import cn.md.entity.UserAccount;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {
    //User 类里面加了一个属性: clzname
    public UserAccount findOneByPhone(String phone);  //登录使用

    //如下 2 个查询都用 左连接完成， 这样就适应教师和学生的要求
    public List<UserAccount> findByRole(String role);

    public List<UserAccount> findStusByclz(String clzno);  //查找某个班级的所有学生

    public UserAccount findByUid(int uid);

    public int add(UserAccount userAccount);   //角色 及班级编号在 教师和学生控制器中完成

    public int update(UserAccount userAccount); //角色 及班级编号在 教师和学生控制器中完成

    public int updatePwd(UserAccount userAccount);

    public int remove(int uid,int is_deleted); //逻辑删除
    List<UserAccount> findPageByRole(String role, Integer pagenum,
                                     Integer lines , String uname,
                                     Integer clzno, Date birthday, Integer xid); //分页查询
    int getCount(String role ,String uname, Integer clzno, Date birthday, Integer xid);
    int updatePwdFromPhone(String password,String phone);

    List<UserAccount> findMark(String clzno, String cno);
}
