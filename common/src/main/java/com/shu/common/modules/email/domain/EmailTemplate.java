package com.shu.common.modules.email.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author JGod
 * @create 2021-11-14-14:49
 */
@Data
public class EmailTemplate implements Serializable {

    private static final long serialVersionUID = -7940850834597331012L;
    /**
     * 接收人邮箱(多个逗号分开)
     */
    private String receiveEmail;

    /**
     * 主题
     */
    private String subject;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送类型
     */
    private EmailType emailType;

    /**
     * 发送类型描述
     */
    private String desc;

    /**
     * 发送时间
     */
    private Long sendTime;

    /**
     * 延时时长
     */
    private Long delay;

}
