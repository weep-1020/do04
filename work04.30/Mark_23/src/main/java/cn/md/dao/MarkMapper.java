package cn.md.dao;

import cn.md.entity.Mark;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MarkMapper {
    List<Mark> findBySno(String sno);
    List<Mark> findByUid(int uid);
    int add(Mark mark);
    int update(Mark mark);
    Mark findByUidCno(int uid, String cno);
}
