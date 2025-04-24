package cn.md.service;

import cn.md.entity.LargeFile;

public interface LargeFileService {

    int delete(int pic);

    int add(String originalFilename, byte[] bytes);

    LargeFile findOne(int id);

    public int findPic(String filename);
    public int findPicByName(String filename);
}
