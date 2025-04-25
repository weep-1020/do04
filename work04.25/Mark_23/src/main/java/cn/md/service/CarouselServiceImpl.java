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
}
