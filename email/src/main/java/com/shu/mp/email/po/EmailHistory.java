package com.shu.mp.email.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author JGod
 * @create 2021-11-14-14:33
 */
@Data
@Entity
@Table(name = "email_history")
public class EmailHistory implements Serializable {

    private static final long serialVersionUID = -5164785418718625231L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 接收人邮箱(
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
     * 模板
     */
    private String template;

    /**
     * 发送时间
     */
    private Timestamp sendTime;

}
