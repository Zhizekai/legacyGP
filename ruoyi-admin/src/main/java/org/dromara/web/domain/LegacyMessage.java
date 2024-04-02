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
 * 消息对象 legacy_message
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("legacy_message")
public class LegacyMessage extends FrontBaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接受消息的用户 ID
     */
    private Long userId;

    /**
     * 评论/收藏/关注的 ID
     */
    private Long sourceId;

    /**
     * 1: 评论，2: 收藏和赞，3: 新增粉丝
     */
    private Integer type;

    /**
     * 0: 未读，1: 已读
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifiedDate;


}
