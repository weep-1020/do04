package cn.md.controller.adm;

import cn.md.entity.Carousel;
import cn.md.service.CarouselService;
import cn.md.service.LargeFileService;
import cn.md.utils.PackJsn;
import cn.md.utils.XJson;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController //用 @RestController 去替代 @Controller+@Responsebody
@RequestMapping("/api/adm/photo")
public class AdmPhotoController {
    @Autowired
    CarouselService carouselService;
    @Autowired
    LargeFileService largeFileService;
    @RequestMapping("/findAll")
    public Object findAll() throws IOException {
        List<Carousel> carousels = carouselService.selectAll();
        return carousels;
    }

    @RequestMapping("/del")
    public void del(int cid) throws IOException {
        int del = carouselService.del(cid);
        if (del == 0) {
            System.out.println("删除失败");
        }
        System.out.println("删除成功");
    }

    @RequestMapping("/add")
    public XJson add(String description,Integer isOpen,
                     @RequestParam(value="mypic", required = false) MultipartFile myfile) throws IOException {
        System.out.println("add description"+description+", isOpen="+isOpen);
        Carousel carousel = new Carousel();
        carousel.setDescription(description);

        carousel.setIfOpen(isOpen);
        if (myfile!=null && myfile.getSize()>0) {
            int pic = 0;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date now = new Date();
                String formattedDate = dateFormat.format(now);
                System.out.println(formattedDate);
                String picName = myfile.getOriginalFilename()+ "_"+formattedDate;
                largeFileService.add(picName, myfile.getBytes());
                carousel.setPname(picName);
                pic = largeFileService.findPic(picName);
            } catch (IOException e) {
                System.out.println("文件上传失败"+e.getMessage());
            }
            if (pic != 0) {
                carousel.setPic(pic);
            }
        }
        int add = carouselService.add(carousel);
        if (add == 0) {
            return PackJsn.pack(500, "添加失败", add);
        }
        return PackJsn.pack(200, "添加成功", add);
    }

    @RequestMapping("/ifOpen")
    public XJson ifOpen(int cid,int ifOpen){
        Carousel carousel = new Carousel();
        carousel.setCid(cid);
        carousel.setIfOpen(ifOpen);
        int update = carouselService.update(carousel);
        if (update == 0) {
            return PackJsn.pack(500, "修改失败", update);
        }
        return PackJsn.pack(200, "修改成功", update);
    }
}
