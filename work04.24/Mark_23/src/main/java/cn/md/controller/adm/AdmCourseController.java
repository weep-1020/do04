package cn.md.controller.adm;

import cn.md.entity.Course;
import cn.md.service.CourseService;
import cn.md.utils.PackJsn;
import cn.md.utils.XJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//课程管理
@CrossOrigin
@RestController //用 @RestController 去替代 @Controller+@Responsebody
@RequestMapping("/api/adm/course")
public class AdmCourseController {
    @Autowired
    CourseService courseService;
    //获取添加数据， 并写到数据库
    @PostMapping("/add")
    public XJson doAdd(@RequestBody Course course) {  //为了测试练习， 采用 Json 方式提交
        System.out.println("Add Course=" + course);
        courseService.add(course);
        List<Course> lst = courseService.findAll();
        return PackJsn.pack(200, "添加成功", lst);
    }
    //列表
    @GetMapping("/findAll")
    public XJson  findAll() {
        //获取课程数据， 显示页面
        List<Course> lst = courseService.findAll();
        return PackJsn.pack(200, "", lst);
    }
    //单个对象
    @RequestMapping("/findOne/{cno}")
    public XJson  findOne(@PathVariable("cno") String cno) {
        //获取课程数据， 显示页面
        Course c= courseService.findOne(cno);
        return PackJsn.pack(200, "", c);
    }

    //获取更新数据， 并写到数据库
    @PostMapping("/update")    // /api/adm/course/update
    public XJson  doUpdate(@RequestBody Course course) {  //为了测试练习， 采用 Json 方式提交
        System.out.println("update Course=" + course);
        courseService.update(course);
        List<Course> lst = courseService.findAll();
        return PackJsn.pack(200, "更新成功", lst);
    }

    //删除
    @GetMapping("/del")
    public XJson doDel(String cno) {
        courseService.remove(cno);
        List<Course> lst = courseService.findAll();
        return PackJsn.pack(200, "删除成功", lst);
    }
}
