package cn.md.dao;

import cn.md.entity.Carousel;
import cn.md.entity.LargeFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CarouselMapper {
    List<Carousel> selectAll();
    Carousel selectById(Integer cid);
    int add(Carousel carousel);

    int del (Integer cid);

    int update(Carousel carousel);
}
