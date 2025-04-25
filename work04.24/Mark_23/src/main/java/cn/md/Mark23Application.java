package cn.md;

import cn.md.dao.ClzMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Mark23Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ioc
                = SpringApplication.run(Mark23Application.class, args);

        ClzMapper bean = ioc.getBean(ClzMapper.class);
        System.out.println(bean);
    }

}
