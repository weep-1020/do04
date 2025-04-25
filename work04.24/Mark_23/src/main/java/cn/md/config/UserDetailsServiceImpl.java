package cn.md.config;

import cn.md.entity.UserAccount;
import cn.md.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsServiceImpl
        implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    /**
     * 重写UserDetailsService接口的loadUserByUsername方法
     * 根据用户名（在这里是手机号）加载用户详情
     * 此方法主要用于Spring Security进行用户认证
     *
     * @param username 用户名，这里指的是用户的手机号
     * @return UserDetails对象，包含用户的相关信息和权限
     * @throws UsernameNotFoundException 如果用户不存在或角色信息为空，则抛出此异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // 日志记录：尝试通过手机号加载用户
            logger.debug("尝试通过手机号加载用户: {}", username);
            UserAccount myUser = userService.findOneByPhone(username);

            // 如果用户不存在，则抛出异常
            if (myUser == null) {
                logger.warn("用户 {} 不存在", username);
                throw new UsernameNotFoundException("用户不存在");
            }

            // 检查角色是否为空，避免空指针异常
            String role = myUser.getRole();
            if (role == null || role.isEmpty()) {
                logger.error("用户 {} 的角色信息为空", username);
                throw new UsernameNotFoundException("用户角色信息为空");
            }

            // 安全性改进：避免直接打印密码
            logger.debug("用户 {} 加载成功，角色为 {}", username, role);

            // 使用常量替代硬编码权限
//            final String DEFAULT_AUTHORITY = "adm"; // 默认权限
            GrantedAuthority primaryAuthority = new SimpleGrantedAuthority(role); // 主权限
            GrantedAuthority secondaryAuthority = new SimpleGrantedAuthority(role); // 默认权限

            // 初始化权限列表
            List<GrantedAuthority> authorities = List.of(primaryAuthority, secondaryAuthority);

            // 返回 UserDetails 实现类
            return new User(username, myUser.getPassword(), authorities);
        } catch (Exception e) {
            // 日志记录异常信息
            logger.error("加载用户 {} 失败: {}", username, e.getMessage(), e);
            // 抛出自定义异常
            throw new UsernameNotFoundException("加载用户失败", e);
        }
    }


//    @Override
//    //user 浏览器登录窗口输入的账号 《input v-model="username"
////                <td><input type="text" name="username"/></td>
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        logger.debug("测试登录myUser点");
//        UserAccount myuser
//                = userService.findOneByPhone(username);
//        logger.debug("!!!!!!!测试登录myUser点 myUser == null");
//        if (myuser != null) {
//            logger.debug(myuser.toString());
//            logger.debug("loadUserByUsername-数据库密码:" +  myuser.getPassword());  // 111
//            //提供 UserDetails 的 实现类 User
//
//            // adm  stu  tea RBAC
//            GrantedAuthority p1 =
//                    new SimpleGrantedAuthority((String)myuser.getRole());  // 权限1
//            //GrantedAuthority p2 = new SimpleGrantedAuthority("clz");  // 权限2
//            GrantedAuthority p2 =
//                    new SimpleGrantedAuthority("adm");  // 权限2
//            List< GrantedAuthority> lst = new ArrayList<>();
//            lst.add(p1);
//            //lst.add(p2);
//            // 向上转型
//            return new User(username, myuser.getPassword(), lst);
//        }
//        logger.debug("myUser == null");
//       return null;
//    }
}

//System.out.println("怎样比对加密结果是由 111 加密产生");
// System.out.println(p2.matches("111", s1));