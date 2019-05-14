package com.morethan.game.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.morethan.game.common.Constant;
import com.morethan.game.utils.DateUtil;
import org.apache.commons.lang.RandomStringUtils;

import java.io.Serializable;

/**
 * Player
 *
 * @Description: Player
 * @Author: 伯符
 * @CreateDate: 2018/11/10
 * @Version: 1.0
 */
@TableName("t_player")
public class Player implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 8611315895915302286L;

    @TableId
    private Long playerId;
    private String playerName;
    private Double experience;
    private String headImg;
    @Version
    private Integer version;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Double getExperience() {
        return experience;
    }

    public void setExperience(Double experience) {
        this.experience = experience;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
