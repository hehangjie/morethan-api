package com.morethan.game.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.morethan.game.entity.Player;
import com.morethan.game.mapper.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * PlayerService
 *
 * @Description: PlayerService
 * @Author: 伯符
 * @CreateDate: 2018/11/10
 * @Version: 1.0
 */

@Service
public class PlayerService extends ServiceImpl<PlayerMapper, Player> {

    @Autowired
    private PlayerMapper playerMapper;

    public Player whichOne(Long id) {
        return selectById(id);
    }

    public List<Player> playersNotInGame(){ return playerMapper.listNoGamePlayer();}
}
