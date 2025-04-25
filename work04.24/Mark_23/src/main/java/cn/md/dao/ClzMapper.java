package cn.md.dao;

import cn.md.entity.Clz;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClzMapper {
    public List<Clz> findAll();
    public int add(Clz clz);
    public int update(Clz clz);
    public int remove(String clzno);
    public Clz findOne(String clzno);
    public int count();
    public List<Clz> findPage(Map<String ,Integer> map);
    public String del(Clz clz);


}
