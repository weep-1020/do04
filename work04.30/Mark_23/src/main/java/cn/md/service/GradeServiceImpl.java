package cn.md.service;

import cn.md.dao.CourseMapper;
import cn.md.dao.GradeMapper;
import cn.md.entity.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService{

    @Autowired
    GradeMapper gradeMapper;

    @Override
    public List<Grade> findAll() {
        List<Grade> all = gradeMapper.findAll();
        return all;
    }

    @Override
    public Grade findByXid(int xid) {
        return null;
    }

    @Override
    public int add(Grade grade) {
        int add = gradeMapper.add(grade);
        return add;
    }

    @Override
    public int del(int xid) {
        int del = gradeMapper.del(xid);
        return del;
    }

    @Override
    public int update(Grade grade) {
        int update = gradeMapper.update(grade);
        return update;
    }
}
