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

    public List<StrategyGuide> getByCharacterId(Integer charId) {
        return strategyMapper.getStrategiesByCharId(charId);
    }

    public List<StrategyGuide> getAllStrategies() {
        return strategyMapper.getAllStrategies();
    }

    public boolean addStrategy(StrategyGuide strategy) {
        return strategyMapper.insertStrategy(strategy) > 0;
    }

    public StrategyGuide getById(Integer id) {
        return strategyMapper.getById(id);
    }

    public boolean updateStrategy(StrategyGuide strategy) {
        return strategyMapper.updateStrategy(strategy) > 0;
    }

    public boolean removeStrategy(Integer id) {
        return strategyMapper.deleteStrategy(id) > 0;
    }
}
