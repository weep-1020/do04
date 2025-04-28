package cn.md.controller;

import cn.md.entity.Mark;
import cn.md.entity.TaskInfo;
import cn.md.entity.UserAccount;
import cn.md.service.MarkService;
import cn.md.service.TaskService;
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

import java.util.List;

@CrossOrigin
@RestController   //用 @RestController 去替代 @Controller+@Responsebody
@RequestMapping("/api/fore/tea")
public class ForeTeaController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;
    @Autowired
    MarkService markService;
    @Autowired
    PasswordEncoder passwordEncoder;
    /**
     * 查找当前登录教师的任务信息
     * 此方法首先从SecurityContextHolder中获取当前登录的教师对象，
     * 然后根据对象中的电话号码查询用户账户信息，
     * 最后根据用户UID获取任务信息并返回
     *
     * @return 包含任务信息的XJson对象 如果查询失败或用户未发现，返回相应的错误信息
     */
    @GetMapping("/findMyTask")  // api/fore/tea/findMyTask
    public XJson findMyTask() {
        //重点： 从 SecurityContextHolder 获取当前登录的教师的对象， 对象里面有教师的 UID 主键及电话号码
        Object who = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //向下转型
        UserDetails user = (UserDetails)who;
        System.out.println(user);
        //如果用户对象为空，表明登录信息已失效
        if (user == null) {
            return PackJsn.pack(401, "查询失败， 登录过期", null);
        }
        //根据用户电话号码查询用户账户信息
        UserAccount newUser = userService.findOneByPhone(user.getUsername());
        //如果用户账户信息为空，表明用户不存在
        if (newUser == null) {
            return PackJsn.pack(404, "用户未发现", null);
        }
        //根据用户UID获取任务信息
        List<TaskInfo> tasks = taskService.findByTid(newUser.getUid());
        //返回包含任务信息的响应
        return PackJsn.pack(200,"", tasks);
    }

    @RequestMapping("/findMark")
    public XJson findMark(String clzno, String cno) {
        List<Mark> markInfoByClzAndCourse = markService.findMarkInfo(clzno, cno);
        System.out.println(markInfoByClzAndCourse);
        return PackJsn.pack(200, "", markInfoByClzAndCourse);
    }
    @RequestMapping("/addMark")
    public XJson addMark(@RequestBody List<Mark> marks) {
        System.out.println("批量提交分数：Marks=" + marks);
        try {
            for (Mark m:marks) {
                if (m.getMarkId()!=0) {
                    markService.update(m);
                }else if (m.getMarkId() == 0) {
                    Mark mark = new Mark();
                    mark.setStudentId(m.getUserAccount().getUid());
                    mark.getClz().setClzno(m.getClz().getClzno());
                    mark.getCourse().setCno(m.getCourse().getCno());
                    mark.setTermId(m.getTermId());
                    mark.setUsualScore(m.getUsualScore());
                    mark.setExamScore(m.getExamScore());
                    markService.add(mark);
                }
            }
        } catch (Exception e) {
            logger.error("更新分数失败",e);
            return PackJsn.pack(500, "更新分数失败", null);
        }
        return PackJsn.pack(200, "更新分数成功", null);
    }

    /**
     * 更改当前登录教师的密码
     * 此方法首先从SecurityContextHolder中获取当前登录的教师对象，
     * 然后验证旧密码是否正确，如果正确则更新为新密码
     *
     * @param newPwd 新密码
     * @return 包含操作结果的XJson对象
     */
    @RequestMapping("/updatePwd")
    public XJson updatePwd(String newPwd) {
        // 从 SecurityContextHolder 获取当前登录的教师的对象
        Object who = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = (UserDetails) who;
        String phone = user.getUsername();

        String encode = passwordEncoder.encode(newPwd);
        System.out.println("encode=" + encode);
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
