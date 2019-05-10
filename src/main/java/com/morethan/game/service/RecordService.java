package com.morethan.game.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.morethan.game.entity.Record;
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
public class RecordService extends ServiceImpl<RecordMapper, Record> {

    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private RecordMapper recordMapper;


    public Record newRound(Score score) {
        //没有被下分的才行
        if(null == score || score.getScoreId() != null || score.getExitAmount() != null || score.getExitTime() != null) {
            return null;
        }


        return null;
    }

}

