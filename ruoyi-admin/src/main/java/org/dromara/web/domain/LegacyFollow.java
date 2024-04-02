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
 * 关注对象 legacy_follow
 *
 * @author Lion Li
 * @date 2024-03-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("legacy_follow")
public class LegacyFollow extends FrontBaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * follow的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 粉丝id
     */
    private Long fansId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifiedDate;


}
