package com.morethan.game.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.morethan.game.entity.Score;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ScoreMapper  extends BaseMapper<Score> {

    @Select("SELECT score_id, player_id, api_entry_time, entry_amount, game_entry_token FROM t_score WHERE game_entry_time IS NOT NULL AND (exit_time IS null OR exit_amount IS null) LIMIT 0,1")
    Score whichOneNoExit(Long playerId);

    @Select("SELECT score_id, player_id, api_entry_time, entry_amount, game_entry_token FROM t_score WHERE game_entry_time IS NOT NULL AND (exit_time IS null OR exit_amount IS null)")
    List<Score> listNoExit();

    @Select("SELECT score_id, player_id, api_entry_time, entry_amount, game_entry_token FROM t_score WHERE game_entry_time IS null")
    List<Score> listNoGameEnter();

    @Select("SELECT score_id, player_id, exit_amount, exit_time, game_entry_time FROM t_score WHERE game_entry_token = #{token}")
    Score whichOneByToken(@Param("token")String token);

}
