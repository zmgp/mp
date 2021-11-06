package com.shu.common.modules.email.domain;

/**
 * @author JGod
 * @create 2021-11-14-14:51
 */
public enum EmailType {

    SINGLE(0, "发送单人"),
    LIST(1, "发送多人"),

    SINGLE_DELAY(2, "延迟发送单人"),
    LIST_DELAY(3, "延迟发送多人"),

    SINGLE_TIMER(4, "定时发送单人"),
    LIST_TIMER(5, "定时发送多人");


    private Integer type;

    private String desc;

    EmailType(int type, String desc){
        this.type = type;
        this.desc = desc;
    }

    public String desc(){
        return this.desc;
    }
}
