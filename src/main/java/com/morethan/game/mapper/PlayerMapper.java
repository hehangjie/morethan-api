package com.morethan.game.mapper;

import com.morethan.game.entity.Score;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.morethan.game.entity.Player;

import java.util.List;

/**
 * PlayerMapper
 *
 * @Description: 玩家Mapper类
 * @Author: 伯符
 * @CreateDate: 2018/4/21
 * @UpdateUser: Anthony
 * @UpdateDate: 2018/4/21 1:13 PM
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public interface PlayerMapper extends BaseMapper<Player>{

    @Select("SELECT player_id, experience FROM t_player WHERE player_id NOT IN (SELECT player_id FROM t_score WHERE exit_time is null) LIMIT 0,5")
    List<Player> listNoGamePlayer();

}
