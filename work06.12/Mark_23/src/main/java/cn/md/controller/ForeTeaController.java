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

    /**
     * 处理添加或更新分数的请求
     * 该方法接收一个Mark对象列表，对每个Mark对象，根据其markId决定是更新还是添加
     * 如果markId不为0，则更新该分数信息；如果markId为0，则添加新的分数信息
     *
     * @param marks 分数对象列表，包含需要添加或更新的分数信息
     * @return 返回一个XJson对象，包含处理结果的状态码、消息和数据
     */
    @RequestMapping("/addMark")
    public XJson addMark(@RequestBody List<Mark> marks) {
        // 打印接收到的分数信息
        System.out.println("+++"+ marks);

        try {
            // 遍历接收到的分数信息列表
            for (Mark m:marks) {
                // 如果分数ID不为0，则执行更新操作
                if (m.getMarkId()!=0) {
                    markService.update(m);
                }
                // 如果分数ID为0，则执行添加操作
                else if (m.getMarkId() == 0) {
                    // 创建一个新的分数对象
                    Mark mark = new Mark();
                    // 设置学生的ID
                    mark.setStudentId(m.getUserAccount().getUid());
                    // 设置班级编号
                    mark.getClz().setClzno(m.getClz().getClzno());
                    // 设置课程编号
                    mark.getCourse().setCno(m.getCourse().getCno());
                    // 设置学期ID
                    mark.setTermId(m.getTermId());
                    // 设置平时成绩
                    mark.setUsualScore(m.getUsualScore());
                    // 设置考试成绩
                    mark.setExamScore(m.getExamScore());
                    // 添加新的分数信息
                    markService.add(mark);
                }
            }
        } catch (Exception e) {
            // 如果发生异常，记录错误日志，并返回错误的响应
            logger.error("更新分数失败",e);
            return PackJsn.pack(500, "更新分数失败", null);
        }

        // 如果操作成功，返回成功的响应
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
