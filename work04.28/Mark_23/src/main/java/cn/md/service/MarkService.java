package cn.md.service;

import cn.md.entity.Mark;

import java.util.List;

public interface MarkService {
    List<Mark> findMarkInfo(String clzno, String cno);

    void update(Mark marks);

    List<Mark> findUid(int uid);

    int add(Mark mark);

//    Mark findByUidCno(int uid, String cno);
    Mark findByUidSno(int uid,String sno);

}
