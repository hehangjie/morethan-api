package com.morethan.game.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * Player
 *
 * @Description: Player
 * @Author: 伯符
 * @CreateDate: 2018/11/10
 * @Version: 1.0
 */
@TableName("t_game")
public class Game implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 8611315895915302285L;

    @TableId
    private Long gameId;
    private String gameDesc;
    private Double pool;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameDesc() {
        return gameDesc;
    }

    public void setGameDesc(String gameDesc) {
        this.gameDesc = gameDesc;
    }

    public Double getPool() {
        return pool;
    }

    public void setPool(Double pool) {
        this.pool = pool;
    }
}
