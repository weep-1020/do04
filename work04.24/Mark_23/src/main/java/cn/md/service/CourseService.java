package cn.md.service;

import cn.md.entity.Course;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CourseService {
    public List<Course> findAll();
    public int add(Course c);
    public int update(Course c);
    public int remove(String cno);
    public Course findOne(String cno);
}
