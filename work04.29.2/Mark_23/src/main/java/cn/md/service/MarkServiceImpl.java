package cn.md.service;

import cn.md.dao.ClzMapper;
import cn.md.dao.CourseMapper;
import cn.md.dao.MarkMapper;
import cn.md.dao.UserMapper;
import cn.md.entity.Clz;
import cn.md.entity.Course;
import cn.md.entity.Mark;
import cn.md.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class MarkServiceImpl implements MarkService{
    @Autowired
    MarkMapper markMapper;

    @Autowired
    ClzMapper clzMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 根据班级编号和课程编号查找成绩信息
     * 此方法首先会从数据库中获取指定班级和课程的信息，然后遍历该班级的所有学生，
     * 对于每个学生，创建一个Mark对象，填充其班级、课程和学生信息，并尝试从数据库中获取该学生的成绩信息
     * 如果数据库中有该学生的成绩信息，则更新Mark对象中的成绩信息最后将所有学生的成绩信息返回
     *
     * @param clzno 班级编号
     * @param cno 课程编号
     * @return 包含所有学生成绩信息的列表
     */
    @Override
    public List<Mark> findMarkInfo(String clzno, String cno) {
        // 初始化一个空的成绩列表
        List<Mark> mks = new ArrayList<>();

        // 根据班级编号获取班级信息
        Clz clz = clzMapper.findOne(clzno);

        // 根据课程编号获取课程信息
        Course cou = courseMapper.findOne(cno);

        // 获取指定班级的所有学生信息
        List<UserAccount> stus = userMapper.findStusByclz(clzno);

        // 打印学生、班级和课程信息，用于调试
        System.out.println(stus);
        System.out.println(clz);
        System.out.println(cou);

        // 遍历每个学生，创建并填充成绩对象
        for (UserAccount s : stus) {
            Mark mark = new Mark();

            // 填充班级信息
            mark.getClz().setClzno(clz.getClzno()); // 获取班级
            mark.getClz().setClzname(clz.getClzname());  // 获取班级名

            // 填充课程信息
            mark.getCourse().setCno(cou.getCno());  // 获取课程
            mark.getCourse().setCname(cou.getCname());   // 获取课程名

            // 填充学生信息
            mark.getUserAccount().setUid(s.getUid());   // 获取学生id
            mark.getUserAccount().setUname(s.getUname());  // 获取学生姓名

//            mark.setTermId(s.getXid());

            // 初始化成绩信息
            mark.setMarkId(0);
            mark.setUsualScore(0.0); // 平时分
            mark.setExamScore(0.0);  // 考试分
            mark.setTotalScore(0.0);  // 总分

            // 尝试从数据库中获取该学生的成绩信息
            Mark byUidCno = markMapper.findByUidCno(s.getUid(), cno);

            // 如果找到了成绩信息，则更新Mark对象中的成绩信息
            if (byUidCno != null) {
                mark.setMarkId(byUidCno.getMarkId());
                mark.setUsualScore(byUidCno.getUsualScore());
                mark.setExamScore(byUidCno.getExamScore());
                mark.setTotalScore(byUidCno.getTotalScore());
            }

            // 打印当前学生的成绩信息，用于调试
            System.out.println(mark);

            // 将当前学生的成绩信息添加到成绩列表中
            mks.add(mark);
        }

        // 返回所有学生的成绩信息列表
        return mks;
    }

    @Override
    public void update(Mark marks) {
        int update = markMapper.update(marks);
    }
    @Override
    public int add(Mark marker) {
        int add = markMapper.add(marker);
        return add;
    }

    @Override
    public List<Mark> findUid(int uid) {
        List<Mark> byUid = markMapper.findByUid(uid);
        System.out.println(byUid);
        return byUid;
    }

    @Override
    public Mark findByUidSno(int uid, String sno) {
        Mark byUidCno = markMapper.findByUidCno(uid, sno);
        System.out.println(byUidCno);
        return byUidCno;
    }
}
