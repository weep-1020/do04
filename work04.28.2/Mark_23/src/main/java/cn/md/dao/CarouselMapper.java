package cn.md.dao;

import cn.md.entity.Carousel;
import cn.md.entity.LargeFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CarouselMapper {
    @Select("SELECT * FROM t_carousel where if_open = 0")
    List<Carousel> selectAll();

    @Select("SELECT * FROM t_carousel WHERE cid=#{cid}")
    Carousel selectById(Integer cid);

    @Insert("INSERT INTO t_carousel (image_id, content) VALUES (#{imageId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "carouselId")
    int insert(Carousel carousel);

    @Select("SELECT * FROM t_carousel WHERE carousel_id = #{carouselId}")
    Carousel selectCarouselWithImageData(Integer carouselId);
}
