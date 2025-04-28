package cn.md.controller.adm;

import cn.md.entity.Carousel;
import cn.md.service.CarouselService;
import cn.md.utils.XJson;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController //用 @RestController 去替代 @Controller+@Responsebody
@RequestMapping("/api/adm/photo")
public class AdmPhotoController {
    @Autowired
    CarouselService carouselService;
    @RequestMapping("/findAll")
    public Object findAll(HttpServletResponse response) throws IOException {
        List<Carousel> carousels = carouselService.selectAll();

        return carousels;
    }
    @RequestMapping("/show")
    public void show(int cid, HttpServletResponse response) throws IOException {
//        return new XJson(0,"？？？","测试");
        Carousel carousel = carouselService.findById(cid);
        System.out.println(carousel.getContent());
        if (carousel.getContent() == null) {
            System.out.println("没有图片");
            return ;
        }
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write((byte[]) carousel.getContent());
        outputStream.flush();
        outputStream.close();
    }
}
