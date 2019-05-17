package com.morethan.game.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * 描述:
 *
 * @outhor anthony
 * @create 2019-05-08 下午6:25
 */
@TableName("t_record")
public class Record implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 8611315895915302288L;

    @TableId(type = IdType.INPUT)
    private String recordId;
    private Long playerId;
    private String recordTime;
    private Double amount;
    private String bet;
    private Long scoreId;
    private Boolean dominate;
    private Double betAmount;

    public Record(){

    }

    public Record(String recordId, Long playerId, String recordTime,
                  Double amount, String bet, Long scoreId,
                  Boolean dominate, Double betAmount){
        this.recordId = recordId;
        this.playerId = playerId;
        this.recordTime = recordTime;
        this.amount = amount;
        this.bet = bet;
        this.scoreId = scoreId;
        this.dominate = dominate;
        this.betAmount = betAmount;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
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

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Boolean getDominate() {
        return dominate;
    }

    public void setDominate(Boolean dominate) {
        this.dominate = dominate;
    }

    public Double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(Double betAmount) {
        this.betAmount = betAmount;
    }

}

