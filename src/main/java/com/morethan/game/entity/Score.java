package com.morethan.game.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 描述:
 *
 * @outhor anthony
 * @create 2019-05-08 下午6:23
 */
@TableName("t_score")
public class Score implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 8611315895915302287L;

    @TableId
    private Long scoreId;
    private Long playerId;
    private String apiEntryTime;
    private String exitTime;
    private Double entryAmount;
    private Double exitAmount;
    private String gameEntryTime;
    private String gameEntryToken;
    private String lastRecordId;

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }


    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public Double getEntryAmount() {
        return entryAmount;
    }

    public void setEntryAmount(Double entryAmount) {
        this.entryAmount = entryAmount;
    }

    public Double getExitAmount() {
        return exitAmount;
    }

    public void setExitAmount(Double exitAmount) {
        this.exitAmount = exitAmount;
    }

    public String getApiEntryTime() {
        return apiEntryTime;
    }

    public void setApiEntryTime(String apiEntryTime) {
        this.apiEntryTime = apiEntryTime;
    }

    public String getGameEntryTime() {
        return gameEntryTime;
    }

    public void setGameEntryTime(String gameEntryTime) {
        this.gameEntryTime = gameEntryTime;
    }

    public String getGameEntryToken() {
        return gameEntryToken;
    }

    public void setGameEntryToken(String gameEntryToken) {
        this.gameEntryToken = gameEntryToken;
    }

    public String getLastRecordId() {
        return lastRecordId;
    }

    public void setLastRecordId(String lastRecordId) {
        this.lastRecordId = lastRecordId;
    }
}

