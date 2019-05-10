package com.morethan.game.dto;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @outhor anthony
 * @create 2019-05-07 下午4:46
 */
public class Bet {
    private Integer gameId;
    private List<Map<String, Double>> betList;

    public List<Map<String, Double>> getBetList() {
        return betList;
    }

    public void setBetList(List<Map<String, Double>> betList) {
        this.betList = betList;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }
}

