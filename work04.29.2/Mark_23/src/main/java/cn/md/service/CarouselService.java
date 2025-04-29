package cn.md.service;

import cn.md.entity.Carousel;

import java.util.List;

public interface CarouselService {
    List<Carousel> selectAll();
    Carousel findById(Integer cid);
}
