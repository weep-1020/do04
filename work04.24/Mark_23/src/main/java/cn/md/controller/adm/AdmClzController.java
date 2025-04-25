package cn.md.controller.adm;

import cn.md.entity.Clz;
import cn.md.service.ClzService;
import cn.md.utils.PackJsn;
import cn.md.utils.XJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController   //用 @RestController 去替代 @Controller+@Responsebody
@RequestMapping("/api/adm/clz")
public class AdmClzController {
    @Autowired
    ClzService clzService;

    //获取添加数据， 并写到数据库
    @PostMapping("/add")
    public XJson doAdd(Clz clz) {     //为了测试练习， 采用键值对方式提交
        System.out.println("Add Clz=" + clz);
        clzService.add(clz);
        List<Clz> lst = clzService.findAll();
        return  PackJsn.pack(200,"添加成功", lst);
    }
    //获取单个对象
    @GetMapping("/findOne/{clzno}")
    public XJson findOne(@PathVariable("clzno") String clzno) {
        System.out.println("clzno=" + clzno);
        Clz clz = clzService.findOne(clzno);
        return PackJsn.pack(200,"", clz);
    }

    //列表
    @GetMapping("/findAll")
    public XJson findAll( ) {
        List<Clz> lst = clzService.findAll();
        return PackJsn.pack(200,"", lst);
    }

    //获取更新数据， 并写到数据库
    @PostMapping("/update")
    public XJson  doUpdate(Clz clz) { //为了测试练习， 采用键值对方式提交
        System.out.println("update Clz=" + clz);
        clzService.update(clz);
        List<Clz> lst = clzService.findAll();
        return PackJsn.pack(200,"更新成功", lst);
    }

    //删除
    @GetMapping("/del")
    public XJson doDel(String clzno ) {
        System.out.println("del clzno=" + clzno);
        clzService.remove(clzno);
        List<Clz> lst = clzService.findAll();
        return PackJsn.pack(200,"删除成功", lst);
    }
}
