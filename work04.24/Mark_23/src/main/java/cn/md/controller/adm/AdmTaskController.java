package cn.md.controller.adm;

import cn.md.entity.TaskInfo;
import cn.md.entity.TaskInfo;
import cn.md.service.TaskService;
import cn.md.utils.PackJsn;
import cn.md.utils.XJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //用 @RestController 去替代 @Controller+@Responsebody
@RequestMapping("/api/adm/task")
public class AdmTaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/findAll")
    public XJson findAll() {
        List<TaskInfo> tasks = taskService.findAll();
        return PackJsn.pack(200, "", tasks);
    }
    @GetMapping("/del")
    public XJson doDel(int kid) {
        System.out.println("task del kid=" + kid);
        taskService.remove(kid);
        return findAll();
    }
    @PostMapping("/add")
    public XJson doAdd(@RequestBody TaskInfo taskInfo) {
        System.out.println("task add=" + taskInfo);
        taskService.add(taskInfo);
        return findAll();
    }
}
