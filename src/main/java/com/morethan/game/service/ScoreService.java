package com.morethan.game.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.morethan.game.entity.Player;
import com.morethan.game.entity.Score;
import com.morethan.game.mapper.PlayerMapper;
import com.morethan.game.mapper.RecordMapper;
import com.morethan.game.mapper.ScoreMapper;
import com.morethan.game.utils.DateUtil;
import com.morethan.game.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * 描述:
 *
 * @outhor anthony
 * @create 2019-05-08 下午6:31
 */
@Service
public class ScoreService extends ServiceImpl<ScoreMapper, Score> {

    private final Logger logger = LoggerFactory.getLogger(ScoreService.class);

    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private PlayerMapper playerMapper;


    public boolean entryScore(Long playerId, Double amount){

        Score noExitScore = scoreMapper.whichOneNoExit(playerId);
        if(null != noExitScore) {
            // TODO 执行下分操作
            return false;
        }

        Score score = new Score();
        score.setPlayerId(playerId);
        score.setEntryAmount(amount);
        score.setApiEntryTime(DateUtil.getCurrentTime());
        score.setGameEntryToken(TokenUtil.getToken(playerId));
        return insert(score);
    }

    @Transactional
    public boolean exitScore(Player player){
        Score noExitScore = scoreMapper.whichOneNoExit(player.getPlayerId());
        if(null == noExitScore) {
            logger.warn("noExitScore ->" , player.getPlayerId());
            return false;
        }

        if(noExitScore.getEntryAmount() == null || noExitScore.getScoreId() == null){
            logger.warn("empty score ->" , noExitScore.getScoreId());
            return false;
        }

        Double exitAmount = noExitScore.getEntryAmount() + recordMapper.sumScoreAmount(noExitScore.getScoreId());
        if(exitAmount < 0) {
            logger.error("负数异常->", exitAmount,",scoreId->", noExitScore.getScoreId());
            exitAmount = 0.0;
        }

        noExitScore.setExitAmount(exitAmount);
        noExitScore.setExitTime(DateUtil.getCurrentTime());
        try {
            updateById(noExitScore);//下分
            player.setExperience(exitAmount);
            boolean isUpdate = retBool(playerMapper.updateById(player));//刷账户
            if(!isUpdate){
                throw new Exception("乐观锁更新失败");
            }
        }catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return true;
    }

    public List<Score> listNoExit(){
        return scoreMapper.listNoExit();
    }

    public List<Score> listNoGameEntry(){
        return scoreMapper.listNoGameEnter();
    }

    public Score whichOneByToken(String token) {return scoreMapper.whichOneByToken(token);}
}

