package cn.md.controller.adm;

import cn.md.entity.Carousel;
import cn.md.service.CarouselService;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
}
