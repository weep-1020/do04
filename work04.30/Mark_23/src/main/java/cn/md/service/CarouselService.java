package cn.md.service;

import cn.md.entity.Carousel;

import java.util.List;

public interface CarouselService {
    List<Carousel> selectAll();
    Carousel findById(Integer cid);
    int del (Integer cid);
    int add(Carousel carousel);
    int update(Carousel carousel);

}
