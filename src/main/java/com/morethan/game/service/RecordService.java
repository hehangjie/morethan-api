package com.morethan.game.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.morethan.game.dto.Lottery;
import com.morethan.game.entity.Player;
import com.morethan.game.entity.Record;
import com.morethan.game.entity.Score;
import com.morethan.game.mapper.PlayerMapper;
import com.morethan.game.mapper.RecordMapper;
import com.morethan.game.mapper.ScoreMapper;
import com.morethan.game.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 *
 * @outhor anthony
 * @create 2019-05-08 下午6:31
 */
@Service
public class RecordService extends ServiceImpl<RecordMapper, Record> {

    private final Logger logger = LoggerFactory.getLogger(RecordService.class);
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private PlayerMapper playerMapper;

    /**
     * 插入记录,刷新用户余额,返回map
     *
     * @param lottery
     * @param player
     * @param bets
     * @param score
     * @param dominate
     * @return
     */
    @Transactional
    public Map<String, Object> record(Lottery lottery, Player player, String bets, Score score, boolean dominate) {

        Map<String, Object> resultMap = new HashMap<>();

        try {
            String recordId = Long.toHexString(DateUtil.getTimestamp()/100) + Long.toHexString(player.getPlayerId());
            //插入记录
            Record record = new Record(
                    recordId,
                    player.getPlayerId(),
                    DateUtil.getCurrentTime(),
                    lottery.getAmount(),
                    bets,
                    score.getScoreId(),
                    dominate,
                    lottery.getBetAmount());

            recordMapper.insert(record);
            resultMap.put("lottery", lottery);

            //刷新用户余额
            player.setExperience(BigDecimal.valueOf(player.getExperience()).add(BigDecimal.valueOf(lottery.getAmount())).doubleValue());
            int isUpdate = playerMapper.updateById(player);
            if (isUpdate == 0) {
                throw new Exception("乐观锁更新失败");
            }
            resultMap.put("experience", player.getExperience());
            resultMap.put("dominate", dominate);
            resultMap.put("record", record.getRecordId());

            //更新score表的lastRecordId, 用于链式验证
            scoreMapper.updateLastRecord(score.getScoreId(), recordId);
            /**
             * 注：链式验证
             * 例如777押大小，是根据上一次中奖的金额来下注的
             * 需要验证本次下注的金额是不是大于上一次注单的中奖金额
             */

        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return resultMap;
    }


    public Record newRound(Score score) {
        //没有被下分的才行
        if (null == score || score.getScoreId() != null || score.getExitAmount() != null || score.getExitTime() != null) {
            return null;
        }


        return null;
    }

    public Integer countRecordByScore(Long scoreId) {
        return recordMapper.countRecord(scoreId);
    }

    public Double sumRecordByScore(Long scoreId) {
        return recordMapper.sumScoreAmount(scoreId);
    }

}

