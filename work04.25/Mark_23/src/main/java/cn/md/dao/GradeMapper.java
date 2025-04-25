package cn.md.dao;

import cn.md.entity.Grade;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GradeMapper {
    List<Grade> findAll();
    int add(Grade grade);
    int del(int xid);
    int update(Grade grade);
}
