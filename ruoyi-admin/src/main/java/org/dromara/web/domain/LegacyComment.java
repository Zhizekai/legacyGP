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
 * 评论对象 legacy_comment
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("legacy_comment")
public class LegacyComment extends FrontBaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章或沸点id
     */
    private Long sourceId;

    /**
     * (1:文章，2：沸点）
     */
    private Integer sourceType;

    /**
     * （source,comment,reply）
     */
    private String type;

    /**
     * 父评论id
     */
    private Long parentId;

    /**
     * 回复某个评论的id
     */
    private Long replyId;

    /**
     * 评论对象创建者id
     */
    private Long targetUser;

    /**
     * 评论的内容
     */
    private String content;

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
