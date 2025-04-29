package cn.md.dao;

import cn.md.entity.TaskInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskMapper {
    List<TaskInfo> findAll();

    int add(TaskInfo taskInfo);

    int update(TaskInfo taskInfo);

    int del(int kid);

    List<TaskInfo> findById(@Param("tid") int tid);

    List<TaskInfo> findByClzno(String clzno);

    List<TaskInfo> findByUid(int uid);
}

