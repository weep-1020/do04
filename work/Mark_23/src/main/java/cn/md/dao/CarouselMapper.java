package cn.md.dao;

import cn.md.entity.Carousel;
import cn.md.entity.LargeFile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CarouselMapper {
    @Select("SELECT * FROM t_carousel")
    List<Carousel> selectAll();

    @Select("SELECT * FROM t_carousel WHERE image_id=#{image_id}")
    Carousel selectById(Integer image_id);

    @Insert("INSERT INTO t_carousel (image_id, content) VALUES (#{imageId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "carouselId")
    int insert(Carousel carousel);

    @Select("SELECT * FROM t_carousel WHERE carousel_id = #{carouselId}")
    Carousel selectCarouselWithImageData(Integer carouselId);
}
