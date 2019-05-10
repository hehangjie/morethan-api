package com.morethan.game.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.morethan.game.entity.Score;
import org.apache.ibatis.annotations.Select;

public interface ScoreMapper  extends BaseMapper<Score> {

    @Select("SELECT score_id, player_id, entry_time, entry_amount FROM t_score WHERE exit_time IS null OR exit_amount IS null")
    Score whichOneNoExit(Long playerId);
}
