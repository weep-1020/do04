package cn.md.service;

import cn.md.dao.ClzMapper;
import cn.md.entity.Clz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClzServiceImpl implements ClzService {
    @Autowired
    ClzMapper clzMapper;
    @Override
    public List<Clz> findAll() {
        return clzMapper.findAll();
    }

    @Override
    public int add(Clz clz) {
        return clzMapper.add(clz);
    }

    @Override
    public int update(Clz clz) {
        return clzMapper.update(clz);
    }

    @Override
    public int remove(String clzno) {
        return clzMapper.remove(clzno);
    }

    @Override
    public Clz findOne(String clzno) {
        return clzMapper.findOne(clzno);
    }
}
