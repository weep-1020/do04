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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin
@RestController  //用 @RestController 去替代 @Controller+@Responsebody
@RequestMapping("/api/adm/stu")
public class AdmStuController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = false)
    UserService userService;
    @Autowired(required = false)
    LargeFileService largeFileService;
    @Autowired
    GradeService gradeService;
    @Autowired
    PasswordEncoder passwordEncoder;


    //获取添加数据， 并写到数据库
    @PostMapping("/add")
    public XJson doAdd(String uname, String phone,String password,
                       String birthday, Integer sex,String clzno,Integer xid,
                       @RequestParam(value="mypic", required = false) MultipartFile myfile)  throws Exception {
        System.out.println("Add Student="+  uname+", phone="+phone+", birthday="+birthday+", sex="+sex+", clzno="+clzno+", xid="+xid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday1 = sdf.parse(birthday);
        UserAccount u = new UserAccount();
        u.setUname(uname);
        u.setPhone(phone);
        u.setPassword(password);
        u.setBirthday(birthday1);
        u.getClz().setClzno(clzno);
        u.getGrade().setXid(xid);
        u.setXid(xid);
        u.setSex(sex);
        u.setRole("stu");
        System.out.println("Add Student=" + u);
        u.setRole("stu");
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
        System.out.println(u);
        userService.add(u);
        return PackJsn.pack(200, "", u);
    }
    //列表
    @GetMapping("/findAll")
    public XJson  findAll() {
        List<UserAccount> lst = userService.findByRole("stu");
        System.out.println("lst="+lst);
        return PackJsn.pack(200,"", lst);
    }


    // 分页
    @RequestMapping("/findPage")
    public XJson  findPage(Integer pagenum, Integer lines, String uname, Integer clzno, Date birthday, Integer xid) throws ParseException {
        System.out.println("是否到达！！！");
        System.out.println("pagenum="+pagenum+",lines="+lines+",uname="+uname+",clzno="+clzno+",birthday="+birthday+",xid="+xid);
        if (pagenum == 1) {
            pagenum--;
        }
        if (pagenum == null || pagenum<0) pagenum=0;
        if (lines == null || lines<1) lines =2;
        List<UserAccount> lst = userService.findPageByRole("stu",pagenum,lines, uname, clzno, birthday, xid);
        List<Grade> all = gradeService.findAll();
        System.out.println("lst="+lst);
        int total = userService.getCount("stu",uname, clzno, birthday, xid); //获取学生的总数
        System.out.println("total="+total);
        System.out.println("lst="+lst);
        Map<String, Object> data = new HashMap<>();
        data.put("grade", all);
        data.put("total", total);
        data.put("students", lst);
        return PackJsn.pack(200,"", data);
    }

    //获取单个学生
    @GetMapping("/findById/{uid}")
    public XJson findById( @PathVariable("uid")  int uid) {
        UserAccount user = userService.findByUid(uid);
        return PackJsn.pack(200, "", user);
    }

    //获取更新数据， 并写到数据库
    @PostMapping("/update")    //  /api/adm/stu/update
    public XJson  doUpdate( Integer uid, String uname,String phone, String clzno,Integer xid,Integer pic,
                           @RequestParam(value="mypic", required = false) MultipartFile myfile) throws Exception {
        UserAccount user = new UserAccount();
        System.out.println("update student="+  uname+", phone="+phone+", clzno="+clzno+", xid="+xid);
        user.setUid(uid);
        user.setUname(uname);
        user.setPhone(phone);
        user.getClz().setClzno(clzno);
        user.getGrade().setXid(xid);
//        user.setXid(xid);
        user.setRole("stu");

        if (myfile!=null && myfile.getSize()>0 && pic==null) {
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
        System.out.println("update student=" + user);
        userService.update(user);
        return PackJsn.pack(200, "", user);
    }
    //删除
    @GetMapping("/del")
    public XJson doDel(int uid) {
        System.out.println("del uid=" + uid);
        userService.remove(uid,1);
        return findAll();
    }
}
