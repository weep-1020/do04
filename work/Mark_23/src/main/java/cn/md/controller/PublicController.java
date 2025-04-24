package cn.md.controller;
//antMatchers("/api/public/**").permitAll()  //无需登录，就可以访问的

import cn.md.dao.LargeFileMapper;
import cn.md.entity.Clz;
import cn.md.entity.Course;
import cn.md.entity.LargeFile;
import cn.md.entity.UserAccount;
import cn.md.service.ClzService;
import cn.md.service.CourseService;
import cn.md.service.LargeFileService;
import cn.md.service.UserService;
import cn.md.utils.PackJsn;
import cn.md.utils.XJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController  //(@Controller + @ResponseBody) 每个方法就无需写 @ResponseBody
@RequestMapping("/api/public")   //swiper
public class PublicController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ClzService clzService;
    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Resource
    LargeFileService largeFileService;
//    @GetMapping("/ok")
//    public String ok() {
//        logger.info("Handling request to /api/public/ok");
//        return "This is a public endpoint and it's working!";
//    }

//    @GetMapping("/findSwiper")   //swip  交换
//    public String findSwipper() {
//        System.out.println("公开的， 获取轮播图");
//        return "公开的， 获取轮播图";
//    }
//    @GetMapping("/findTopStus")   // Top 顶尖
//    public String findTopStus() {
//        System.out.println("公开的， 获取优秀学生信息");
//        return "公开的，获取优秀学生信息 ";
//    }

    @RequestMapping("/showimg/{uuid}")
    public void showimg(@PathVariable("uuid") int id, HttpServletResponse response) throws IOException {
        if (id != 0){
            //        LargeFile one = largeFileMapper.findOne(id);
            LargeFile one = largeFileService.findOne(id);
//        System.out.println(one.toString());
            Object content = one.getContent();
            ServletOutputStream os = response.getOutputStream();
            System.out.println("showimg测试点");
            response.setContentType("image/jpeg");
            byte[] buf = (byte[])content;
            os.write(buf);
            os.flush();
        }

        return ;
    }

    @GetMapping("/getClzs")
    public XJson getClzs( ) {
        //获取班级数据
        List<Clz> lst = clzService.findAll();
        return PackJsn.pack(200,"", lst);
    }
    @GetMapping("/getCourses")
    public XJson getCourses( ) {
        //获取课程数据， 显示页面
        List<Course> lst = courseService.findAll();
        return PackJsn.pack(200, "", lst);
    }
    @GetMapping("/getTeachers")
    public XJson getTeacher(){
        List<UserAccount> tea = userService.findByRole("tea");
        return PackJsn.pack(200,"",tea);
    }
}
