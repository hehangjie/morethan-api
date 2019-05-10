package com.morethan.game.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 描述:
 *
 * @outhor anthony
 * @create 2019-05-08 下午6:25
 */
@TableName("t_score")
public class Record implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 8611315895915302288L;

    @TableId
    private Long recordId;
    private Long playerId;
    private String beginTime;
    private String recordTime;
    private Double amount;
    private String bet;
    private Integer scoreId;
    private Boolean dominate;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBet() {
        return bet;
    }

    public void setBet(String bet) {
        this.bet = bet;
    }

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public Boolean getDominate() {
        return dominate;
    }

    public void setDominate(Boolean dominate) {
        this.dominate = dominate;
    }
}

