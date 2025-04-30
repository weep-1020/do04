package cn.md.controller.adm;


import cn.md.entity.Grade;
import cn.md.entity.UserAccount;
import cn.md.service.GradeService;
import cn.md.service.LargeFileService;
import cn.md.service.UserService;
import cn.md.utils.PackJsn;
import cn.md.utils.XJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController //用 @RestController 去替代 @Controller+@Responsebody
@RequestMapping("/api/adm/tea")
public class AdmTeaController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;
    @Autowired
    LargeFileService largeFileService;
    @Autowired
    GradeService gradeService;
    @Autowired
    PasswordEncoder passwordEncoder;



    //获取添加数据， 并写到数据库
    @PostMapping("/add")
    public XJson doAdd(String uname, String phone,String password, int sex ,Date birthday, @RequestParam(value="mypic", required = false) MultipartFile myfile)  throws Exception {
        System.out.println("Add Teacher="+  uname+", phone="+phone+", birthday="+birthday+", sex="+sex);
        UserAccount u = new UserAccount();
        u.setUname(uname);
        u.setPhone(phone);
        u.setPassword(password);
        u.setBirthday(birthday);
        u.setRole("tea");
        if (myfile!=null && myfile.getSize()>0) {
            int pic = 0;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date now = new Date();
                String formattedDate = dateFormat.format(now);
                System.out.println(formattedDate);
                String picName = myfile.getOriginalFilename()+ "_"+formattedDate;
                largeFileService.add(picName, myfile.getBytes());
                pic = largeFileService.findPic(picName);
            } catch (IOException e) {
                System.out.println("文件上传失败"+e.getMessage());
            }
            if (pic != 0) {
                u.setPic(pic);
            }
        }
        String encode = passwordEncoder.encode(u.getPassword());
        u.setPassword(encode);
        userService.add(u);
        System.out.println("添加教师:"  +  u);
        return PackJsn.pack(200, "", u);
    }
    //列表
    @GetMapping("/findAll")
    public XJson findALl( ) {
        //获取教师 role=tea  数据， 显示页面
        List<UserAccount> lst = userService.findByRole("tea");
        return PackJsn.pack(200, "", lst);
    }

    @RequestMapping("/findPage")
    public XJson  findPage(Integer pagenum, Integer lines, String uname, Integer clzno, Date birthday, Integer xid) {
        System.out.println("是否到达！！！");
        if (pagenum == 1) {
            pagenum--;
        }
        if (pagenum == null || pagenum<0) pagenum=0;
        if (lines == null || lines<1) lines =2;
        List<UserAccount> lst = userService.findPageByRole("tea",pagenum,lines, uname, clzno, birthday, xid);
        System.out.println("lst="+lst);
        int total = userService.getCount("tea",uname, clzno, birthday, xid); //获取学生的总数
        System.out.println("total="+total);
        System.out.println("lst="+lst);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("teachers", lst);
        return PackJsn.pack(200,"", data);
    }
    //获取单个老师
    @GetMapping("/findById/{uid}")
    public XJson findById( @PathVariable("uid")  int uid) {
        //获取教师 role=5 数据， 显示页面
        UserAccount user = userService.findByUid(uid);
        return PackJsn.pack(200, "", user);
    }


    //获取更新数据， 并写到数据库
    @PostMapping("/update")
    public XJson doUpdate(Integer uid, String uname,String phone,Integer pic,
                          @RequestParam(value="mypic", required = false) MultipartFile myfile) throws Exception {
        //如果有更新图片， 则更新， 没有就不更新
        UserAccount user = new UserAccount();
        user.setUid(uid);
        user.setUname(uname);
        user.setPhone(phone);
        user.setPic(pic);
//        user.setXid(xid);
        user.setRole("tea");
        System.out.println("update teacher=" + user);
        System.out.println("myfile=" + myfile);
        if (myfile!=null && myfile.getSize()>0 || user.getPic()==0) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date now = new Date();
                String formattedDate = dateFormat.format(now);
                System.out.println(formattedDate);

                String picName = myfile.getOriginalFilename()+ "_"+formattedDate;

                largeFileService.add(picName, myfile.getBytes());
                int pic2 = largeFileService.findPic(picName);
                user.setPic(pic2);
            } catch (IOException e) {
                logger.debug("文件上传失败" + e.getMessage());
            }
        }
        System.out.println(user);
        userService.update(user);
        return PackJsn.pack(200, "", user);
    }

    //删除
    @GetMapping("/del")
    public XJson doDel(int uid) {
        System.out.println("del uid=" + uid);
        userService.remove(uid ,1);
        return findALl();
    }

}