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
 * 点赞对象 legacy_praise
 *
 * @author Lion Li
 * @date 2024-03-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("legacy_praise")
public class LegacyPraise extends FrontBaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 点赞id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章或沸点ID
     */
    private Long targetId;

    /**
     * 1: 文章，2: 沸点
     */
    private String targetType;

    /**
     * 目标用户ID
     */
    private Long targetUser;

    /**
     * 1: 点赞，2: 收藏
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifiedDate;
    private Long createBy;

    // 是否已读（0: 未读，1: 已读）
    private Integer readStatus;
}
