package org.dromara.web.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.common.mybatis.core.domain.FrontBaseEntity;

import java.io.Serial;

/**
 * 用户对象 legacy_user
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("legacy_user")
public class LegacyUser extends FrontBaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 介绍信息
     */
    private String introduc;

    /**
     * 位置信息
     */
    private String position;

    /**
     * 公司
     */
    private String company;

    /**
     * 能力
     */
    private String juePower;

    /**
     * 好评
     */
    private Integer goodNum;

    /**
     *
     */
    private Integer readNum;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifiedDate;


}
