package cn.md.service;

import cn.md.entity.Carousel;

import java.util.List;

public interface CarouselService {
    List<Carousel> getAllCarousels();
    Carousel getCarouselsById(Integer imageId);
    Carousel getCarouselWithImageData(Integer carouselId);
    List<Carousel> getAllCarouselsWithImageData(); // 新增方法
}
