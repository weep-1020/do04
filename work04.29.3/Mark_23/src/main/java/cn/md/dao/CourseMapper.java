package cn.md.dao;

import cn.md.entity.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CourseMapper {
    public int add(Course course);
    public int update(Course course);
    List<Course> findAll();
    public Course findOne(String cno);
    public int remove(String course);
    public int count();
    public List<Course> findPage(Map<String, Integer> map);
}
