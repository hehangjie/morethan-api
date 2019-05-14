package com.morethan.game.dto;


/**
 * 描述:
 *
 * @outhor anthony
 * @create 2019-05-10 下午4:35
 */
public class Lottery {

    private String title;
    private String type;
    private Integer[] items;
    private Double amount;
    private Double betAmount;
    private Double lotteryAmount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer[] getItems() {
        return items;
    }

    public void setItems(Integer[] items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(Double betAmount) {
        this.betAmount = betAmount;
    }

    public Double getLotteryAmount() {
        return lotteryAmount;
    }

    public void setLotteryAmount(Double lotteryAmount) {
        this.lotteryAmount = lotteryAmount;
    }
}

