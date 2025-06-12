package cn.md.service;

import cn.md.dao.CarouselMapper;
import cn.md.entity.Carousel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CarouselServiceImpl implements CarouselService{

    @Autowired
    CarouselMapper carouselMapper;

    @Override
    public List<Carousel> selectAll() {
        List<Carousel> carousels = carouselMapper.selectAll();
        return carousels;
    }

    @Override
    public Carousel findById(Integer cid) {
        Carousel carousel = carouselMapper.selectById(cid);
        return carousel;
    }

    @Override
    public int del(Integer cid) {
        int del = carouselMapper.del(cid);
        return del;
    }

    @Override
    public int add(Carousel carousel) {
        int add = carouselMapper.add(carousel);
        return add;
    }

    @Override
    public int update(Carousel carousel) {
        int update = carouselMapper.update(carousel);
        return update;
    }
}
