package com.morethan.game.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.morethan.game.entity.Score;
import com.morethan.game.mapper.RecordMapper;
import com.morethan.game.mapper.ScoreMapper;
import com.morethan.game.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @outhor anthony
 * @create 2019-05-08 下午6:31
 */
@Service
public class ScoreService extends ServiceImpl<ScoreMapper, Score> {

    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private RecordMapper recordMapper;


    public boolean entryScore(Long playerId, Double amount){

        Score noExitScore = scoreMapper.whichOneNoExit(playerId);
        if(null != noExitScore) {
            // TODO 执行下分操作
            return false;
        }

        Score score = new Score();
        score.setPlayerId(playerId);
        score.setEntryAmount(amount);
        score.setEntryTime(DateUtil.getCurrentTime());
        return insert(score);
    }

    public boolean exitScore(Long playerId){
        Score noExitScore = scoreMapper.whichOneNoExit(playerId);
        if(null == noExitScore) {
            //TODO 无分可下，记录异常
            return false;
        }

        if(noExitScore.getEntryAmount() == null || noExitScore.getScoreId() == null){
            return false;
        }

        Double exitAmount = noExitScore.getEntryAmount() + recordMapper.sumScoreAmount(noExitScore.getScoreId());
        if(exitAmount < 0) {
            //TODO 负数异常
            exitAmount = 0.0;
        }

        noExitScore.setExitAmount(exitAmount);
        noExitScore.setExitTime(DateUtil.getCurrentTime());

        Wrapper<Score> wrapper = new EntityWrapper<>();
        wrapper.eq("","");
        baseMapper.update(noExitScore, wrapper);
        return true;
    }

}

