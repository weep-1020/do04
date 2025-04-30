package cn.md.controller;

//.anyRequest().authenticated()   //authened 已经登录成功后才能访问 /api/user/改密码
// authenticated 认证成功的

import cn.md.entity.UserAccount;
import cn.md.service.UserService;
import cn.md.utils.PackJsn;
import cn.md.utils.XJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController  //(@Controller + @ResponseBody) 每个方法就无需写 @ResponseBody
@RequestMapping("/api/user")   //swiper
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/info")
    public XJson getUserInfo() {
        Object who = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (who==null) {
            return PackJsn.pack(401,"没有登录或登录超时","");
        }
        UserDetails usr = (UserDetails) who;
        String phone = usr.getUsername();
        UserAccount xu = userService.findOneByPhone(phone);
        if (xu==null) {
            return PackJsn.pack(401,"该用户不存在","");
        }
        logger.info("用户信息：" + xu.toString());
        Map<String, Object> map = new HashMap<>();
        map.put("uname", xu.getUname());
        map.put("phone", xu.getPhone());
        map.put("role", xu.getRole());
        logger.info("图片ID："+xu.getPic());
        map.put("pic", xu.getPic());
        return PackJsn.pack(200,"用户信息",map);
    }

    @PostMapping("/updatePwd")
    public XJson updatePwd(String oldPwd, String newPwd) {
        //重点： 从 session 获取当前登录的教师的对象， 对象里面有教师的 UID 主键及电话号码
        System.out.println("更新密码：" + oldPwd);
        Object who = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //向下转型
        UserDetails user = (UserDetails)who;

        if (user == null) {
            //如果是空指针， 后台的 Token 失效了， 意味没有登录
            return PackJsn.pack(401, "更新失败， 登录过期", null);
        }

        UserAccount newUser = userService.findOneByPhone(user.getUsername());
        if (newUser == null) {
            //如果是空指针， 后台的 Token 失效了， 意味没有登录
            return PackJsn.pack(401,"该用户不存在","");
        }

        if (!passwordEncoder.matches(oldPwd, newUser.getPassword())) {
            System.out.println("旧密码比对失败");
            System.out.println(newUser.getPassword());
            return PackJsn.pack(401,"旧密码输入有误","");
        }
        newUser.setUname(null); newUser.setPhone(null); newUser.setRole(null);
        newUser.setPassword(newPwd);
        int x= userService.updatePwd(newUser);
        if ( x ==0 ) {
            return PackJsn.pack(401,"密码修改失败","");
        }
        return PackJsn.pack(200,"密码成功修改","");
    }
}
