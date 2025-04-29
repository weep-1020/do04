package cn.md.service;

import cn.md.dao.UserMapper;
import cn.md.entity.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    UserMapper userMapper;  //注入Dao

    @Override
    public UserAccount findOneByPhone(String phone) {
        UserAccount user = null;
        try {
            user = userMapper.findOneByPhone(phone);
            logger.debug(user.toString());
        } catch (Exception e) {
            return null;
        }

        return user;
    }

    @Override
    public UserAccount findByUid(int uid) {
        logger.debug("findByUid=" + uid);
        UserAccount byUid = userMapper.findByUid(uid);
        logger.debug("findByUid=" + byUid);
        return byUid;
    }

    @Override
    public int add(UserAccount userAccount) {
        System.out.println("add=" + userAccount);
        return userMapper.add(userAccount);
    }

    /**
     * 重写更新用户密码的方法
     * 此方法主要用于更新用户账户的密码，确保密码在数据库中是加密存储的
     * 如果提供的密码为空或null，将密码设置为null，否则对密码进行加密处理
     *
     * @param userAccount 用户账户对象，包含需要更新的密码信息
     * @return 更新操作影响的行数，用于判断更新操作是否成功
     */
    @Override
    public int updatePwd(UserAccount userAccount) {
        // 打印出更新密码前的用户账户信息
        System.out.println("update pwd=" + userAccount);

        // 获取用户账户的密码
        String pwd = userAccount.getPassword();

        // 检查密码是否为空或null
        if (pwd == null || pwd.length() == 0) {
            // 如果密码为空或null，将密码设置为null
            userAccount.setPassword(null);
        } else {
            // 否则，对密码进行加密处理
            userAccount.setPassword(passwordEncoder.encode(pwd));
        }

        // 记录更新密码后的用户账户信息
        logger.debug("update pwd2=" + userAccount);

        // 调用用户映射器的更新方法，执行数据库更新操作，并返回更新结果
        return userMapper.update(userAccount);
    }

    @Override
    public List<UserAccount> findByRole(String stu) {
        List<UserAccount> lst = userMapper.findByRole(stu);
        for (UserAccount userAccount : lst) {
            logger.debug("所有的学生"+ userAccount.toString());
        }
        return lst;
    }

    @Override
    public void update(UserAccount user) {
        logger.debug("更改的 update=" + user);
        userMapper.update(user);
    }

    @Override
    public void remove(int uid ,int is_deleted) {
        logger.debug("remove删除=" + uid);
        userMapper.remove(uid, 1);
    }

    @Override
    public List<UserAccount> findPageByRole(String role, Integer pagenum,
                                            Integer lines, String uname,
                                            Integer clzno, Date birthday, Integer xid) {
        List<UserAccount> pageByRole = userMapper.findPageByRole(role, pagenum,
                lines, uname, clzno, birthday, xid);
        return pageByRole;
    }

    @Override
    public int getCount(String role ,String uname, Integer clzno, Date birthday, Integer xid) {
        return userMapper.getCount(role,uname, clzno, birthday, xid);
    }

    @Override
    public int updatePwdFromPhone(String password, String phone) {
        boolean b = passwordEncoder.upgradeEncoding(password);
        return userMapper.updatePwdFromPhone(password,phone);
    }

    @Override
    public List<UserAccount> findMark(String clzno, String cno) {
        List<UserAccount> mark = userMapper.findMark(clzno, cno);
        return mark;
    }
}
