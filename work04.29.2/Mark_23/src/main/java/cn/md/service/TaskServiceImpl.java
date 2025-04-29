package cn.md.service;

import cn.md.dao.TaskMapper;
import cn.md.entity.TaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    TaskMapper taskMapper;

    @Override
    public List<TaskInfo> findAll() {
        logger.debug("查询所有任务");
        List<TaskInfo> taskInfos = taskMapper.findAll();
        for (TaskInfo taskInfo : taskInfos) {
            logger.debug("TaskInfo: {}", taskInfo);
        }
        return taskInfos;
    }

    @Override
    public void add(TaskInfo taskInfo) {
        logger.debug("添加任务: {}", taskInfo);
        int add = taskMapper.add(taskInfo);
        logger.debug("添加了 {} 条数据", add);
    }

    @Override
    public void update(TaskInfo taskInfo) {
        logger.debug("更新任务: {}", taskInfo);
        int update = taskMapper.update(taskInfo);
        logger.debug("更新了 {} 条数据", update);
    }

    @Override
    public void remove(int kid) {
        int del = taskMapper.del(kid);
        logger.debug("删除了 {} 条数据", del);
    }

    @Override
    public List<TaskInfo> findByTid(int tid) {
        return taskMapper.findById(tid);
    }

    @Override
    public List<TaskInfo> findByClzno(String clzno) {
        return taskMapper.findByClzno(clzno);
    }

    @Override
    public List<TaskInfo> findByUid(int uid) {
        return taskMapper.findByUid(uid);
    }
}
