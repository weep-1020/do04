package cn.md.service;


import cn.md.dao.LargeFileMapper;
import cn.md.entity.LargeFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LargeFileServiceImpl implements LargeFileService{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LargeFileMapper largeFileMapper;

    @Override
    public int delete(int pic) {
        logger.info("删除的图片："+pic);
        int delete = largeFileMapper.delete(pic);
        logger.info("删除结果："+delete);
        return delete;
    }

    @Override
    public int add(String originalFilename, byte[] bytes) {
        LargeFile largeFile = new LargeFile();
        largeFile.setFilename(originalFilename);
        largeFile.setContent(bytes);
        logger.info("添加的图片："+largeFile);
        int add = largeFileMapper.add(largeFile);
        logger.info("添加结果："+add);
        return add;
    }

    @Override
    public LargeFile findOne(int id) {
        LargeFile one = largeFileMapper.findOne(id);
        logger.info("查询结果："+one);
        return one;
    }


    @Override
    public int findPic(String filename) {
        int pic = largeFileMapper.findPic(filename);
        return pic;
    }

    @Override
    public int findPicByName(String filename) {
        int pics = largeFileMapper.findPicByName(filename);
        return pics;
    }

}
