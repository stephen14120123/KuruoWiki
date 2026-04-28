package com.wiki.service;

import com.wiki.dao.StrategyMapper;
import com.wiki.entity.StrategyGuide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class StrategyService {

    @Autowired
    private StrategyMapper strategyMapper;

    public List<StrategyGuide> testCascade() {
        return strategyMapper.getStrategyWithCascade();
    }
}