package cn.md.service;

import cn.md.entity.TaskInfo;

import java.util.List;

public interface TaskService {
    List<TaskInfo> findAll();

    void add(TaskInfo taskInfo);

    void update(TaskInfo taskInfo);

    void remove(int kid);

    List<TaskInfo> findByTid(int tid);

    List<TaskInfo> findByClzno(String clzno);

    List<TaskInfo> findByUid(int uid);
}
