package cn.md.service;

import cn.md.entity.Grade;

import java.util.List;

public interface GradeService {
    List<Grade>findAll();

    Grade findByXid(int xid);
    int add(Grade grade);
    int del(int xid);
    int update(Grade grade);
}
