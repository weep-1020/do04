package cn.md.service;

import cn.md.dao.CourseMapper;
import cn.md.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl  implements CourseService{
    @Autowired
    CourseMapper courseMapper;

    @Override
    public List<Course> findAll() {
        return courseMapper.findAll();
    }

    @Override
    public int add(Course c) {
        return courseMapper.add(c);
    }

    @Override
    public int update(Course c) {
        return courseMapper.update(c);
    }

    @Override
    public int remove(String cno) {
        return courseMapper.remove(cno);
    }

    @Override
    public Course findOne(String cno) {
        return courseMapper.findOne(cno);
    }
}
