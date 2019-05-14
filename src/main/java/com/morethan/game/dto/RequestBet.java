package com.morethan.game.dto;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @outhor anthony
 * @create 2019-05-07 下午4:46
 */
public class RequestBet {
    private Integer gameId;
    private List<Bet> betList;

    public List<Bet> getBetList() {
        return betList;
    }

    public void setBetList(List<Bet> betList) {
        this.betList = betList;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }
}

