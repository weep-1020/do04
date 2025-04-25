package cn.md.controller;

import cn.md.entity.Mark;
import cn.md.entity.TaskInfo;
import cn.md.entity.UserAccount;
import cn.md.service.MarkService;
import cn.md.service.TaskService;
import cn.md.service.UserService;
import cn.md.service.UserServiceImpl;
import cn.md.utils.PackJsn;
import cn.md.utils.XJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/fore/stu")
public class ForeStuController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;
    @Autowired
    MarkService markService;
    @Autowired
    PasswordEncoder passwordEncoder;

/**
 * 查找当前学生登录用户下的所有课程
 * 此方法首先获取当前登录的用户信息，然后根据用户信息查找并返回课程列表
 * 它使用Spring Security框架来获取当前认证用户的信息
 * @return 包含课程列表的XJson对象
 */
@RequestMapping("/findMyCourses")
public XJson findMyCourses() {
    // 使用 logger 记录日志信息
    logger.info("开始查找当前登录用户的课程");

    try {
        // 从 SecurityContextHolder 获取当前登录的用户对象
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)) {
            return PackJsn.pack(401, "查询失败，登录过期", null);
        }

        UserDetails user = (UserDetails) principal;
        String phone = user.getUsername();

        // 查询用户账户信息，避免重复查询
        UserAccount userAccount = userService.findOneByPhone(phone);
        logger.debug("查询到的用户账户信息：{}", userAccount);
        if (userAccount == null) {
            logger.warn("用户未找到，电话号码：{}", phone);
            return PackJsn.pack(404, "用户未发现", null);
        }

        // 获取班级编号
        if (userAccount.getClz() == null || userAccount.getClz().getClzno() == null) {
            logger.warn("用户所属班级信息缺失，电话号码：{}", phone);
            return PackJsn.pack(400, "用户班级信息缺失", null);
        }

        String clzno = userAccount.getClz().getClzno();

        // 查询课程信息
        List<TaskInfo> courses = taskService.findByClzno(clzno);
        logger.debug("查询到的课程：{}", courses);

        // 返回成功结果
        return PackJsn.pack(200, "", courses);
    } catch (Exception e) {
        // 捕获异常并返回错误信息
        logger.error("查询课程时发生异常", e);
        return PackJsn.pack(500, "系统内部错误", null);
    }
}

    @ResponseBody
    @RequestMapping("/findMyMarks")
    public XJson findMyMarks() {
        System.out.println("学生登录后， 查找我的成绩");
        String currentUser = "";
        Object principl = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("当前登录用户信息：" + principl);
        if(principl instanceof UserDetails) {
            currentUser = ((UserDetails)principl).getUsername();
        }else {
            currentUser = principl.toString();
        }
        UserAccount oneByPhone = userService.findOneByPhone(currentUser);
        List<Mark> uid = markService.findUid(oneByPhone.getUid());

        return PackJsn.pack(200,currentUser, uid);
    }
    /**
     * 更改当前登录教师的密码
     * 此方法首先从SecurityContextHolder中获取当前登录的教师对象，
     * 然后验证旧密码是否正确，如果正确则更新为新密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return 包含操作结果的XJson对象
     */
    @RequestMapping("/updatePwd")
    public XJson updatePwd(@RequestParam String oldPwd, @RequestParam String newPwd) {
        // 从 SecurityContextHolder 获取当前登录的教师的对象
        Object who = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = (UserDetails) who;
        String phone = user.getUsername();
        String encode = passwordEncoder.encode(newPwd);
        // 根据用户电话号码查询用户账户信息
        UserAccount newUser = userService.findOneByPhone(phone);
        if (newUser == null) {
            return PackJsn.pack(404, "用户未发现", null);
        }

        // 更新密码
        int i = userService.updatePwdFromPhone(encode, phone);
        if (i > 0) {
            return PackJsn.pack(200, "更改成功", i);
        } else {
            return PackJsn.pack(500, "密码更新失败", null);
        }
    }
}
