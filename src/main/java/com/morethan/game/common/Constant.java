package com.morethan.game.common;

/**
 * 常量
 * @author long
 *
 */
public class Constant {
 
	public static final String DEVACTIVE = "dev";
	public static final String TESTACTIVE = "test";
	public static final String PRODACTIVE = "prod";
	
	public static final String PLAYERINFOPREFIX = "player_info:";
	public static final String PLAYERTOKENPREFIX = "player_token:";
	public static final String BACCARATKEY = "baccarat:";
	public static final String BACCARATCOUNTKEY = "baccarat_count:";
	public static final String CHANGEEXPERIENCEKEY = "experience_sign_list:";
	public static final String RECODEKEY = "record_sign_list:";

	public static final String TOKEN_REDIS_KEY = "app_token_";
	public static final String RELIEF_MONEY_KEY = "app_relief_money_";
	public static final String LUCKY_TURNTABLE_KEY = "app_lucky_turntable_";

	public static final String REGISTER_GIFT_CONFIG = "registerGiftConfig"; //注册有礼（绑定送金）
	public static final String RELIEF_MONEY_CONFIG = "reliefMoneyConfig"; //救济金
	public static final String SIGN_CONFIG = "signConfig"; //每日签到
	public static final String LUCKY_TURNTABLE_CONFIG = "luckyTurntableConfig"; //幸运转盘
	public static final String FIRST_CHARGE_REWARD_CONFIG = "firstChargeRewardConfig";

	//弹框元素
	public static final String POP_REGISTER_GIFT = "popRegisterGift";
	public static final String POP_Notice = "popNotice";

	//
	public static final String HAS_RELIEF_MONEY = "hasReliefMoney";
	public static final String HAS_LUCKY_TURNTABLE = "hasLuckTurntable";
	public static final String HAS_FIRST_CHARGE = "hasFirstCharge";

	//输赢线
	public static final String THRESHOLDKEY = "threshold";
	public static final Double THRESHOLD = 5000d;
	public static final String DEFAULT_HEADER = "http://img.itmajor.cn/women6.png";
	//默认百家乐多少局开始新的一局
	public static final Integer DEFAULTBACCARATROUND = 50;
	public static final String DEFAULTBACCARATROUNDKEY = "default_baccarat_round";
	
	//玩家余额
	public static final String PLAYEREXPERIENCE = "player_experience:";
	
	//777奖池
	public static final String REWARDPOOL777 = "reward_pool_777";
	
	//socket服务器地址
	public static final String SOCKETSERVER = "socketServer";
	
	public static final String[] headImgs = new String[]{
            "http://img.itmajor.cn/man0.png",
            "http://img.itmajor.cn/man1.png",
            "http://img.itmajor.cn/man2.png",
            "http://img.itmajor.cn/man3.png",
            "http://img.itmajor.cn/man4.png",
            "http://img.itmajor.cn/man5.png",
            "http://img.itmajor.cn/man6.png",
            "http://img.itmajor.cn/man7.png",
            "http://img.itmajor.cn/man8.png",
            "http://img.itmajor.cn/man9.png",
            "http://img.itmajor.cn/man10.png",
            "http://img.itmajor.cn/man11.png",
            "http://img.itmajor.cn/man12.png",
            "http://img.itmajor.cn/man13.png",
            "http://img.itmajor.cn/man14.png",
            "http://img.itmajor.cn/man15.png",
            "http://img.itmajor.cn/man16.png",
            "http://img.itmajor.cn/man17.png",
            "http://img.itmajor.cn/man18.png",
            "http://img.itmajor.cn/man19.png",
            "http://img.itmajor.cn/man20.png",
            "http://img.itmajor.cn/man21.png",
            "http://img.itmajor.cn/man22.png",
            "http://img.itmajor.cn/man23.png",
            "http://img.itmajor.cn/women0.png",
            "http://img.itmajor.cn/women1.png",
            "http://img.itmajor.cn/women2.png",
            "http://img.itmajor.cn/women3.png",
            "http://img.itmajor.cn/women4.png",
            "http://img.itmajor.cn/women5.png",
            "http://img.itmajor.cn/women6.png",
            "http://img.itmajor.cn/women7.png",
            "http://img.itmajor.cn/women8.png",
            "http://img.itmajor.cn/women9.png",
            "http://img.itmajor.cn/women10.png",
            "http://img.itmajor.cn/women11.png",
            "http://img.itmajor.cn/women12.png",
            "http://img.itmajor.cn/women13.png",
            "http://img.itmajor.cn/women14.png",
            "http://img.itmajor.cn/women15.png",
            "http://img.itmajor.cn/women16.png",
            "http://img.itmajor.cn/women17.png",
            "http://img.itmajor.cn/women18.png",
            "http://img.itmajor.cn/women19.png",
            "http://img.itmajor.cn/women20.png",
            "http://img.itmajor.cn/women21.png",
            "http://img.itmajor.cn/women22.png",
            "http://img.itmajor.cn/women23.png"
    };
}
