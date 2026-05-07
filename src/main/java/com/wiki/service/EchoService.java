package com.wiki.service;

import com.wiki.dao.EchoMapper;
import com.wiki.entity.EchoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EchoService {

    @Autowired
    private EchoMapper echoMapper;

    public List<EchoInfo> getAllEchoes() {
        return echoMapper.getAllEchoes();
    }

    public boolean saveEcho(EchoInfo echo) {
        return echoMapper.insertEcho(echo) > 0;
    }

    public boolean updateEcho(EchoInfo echo) {
        return echoMapper.updateEcho(echo) > 0;
    }

    public boolean deleteEcho(Integer id) {
        return echoMapper.deleteEcho(id) > 0;
    }
}