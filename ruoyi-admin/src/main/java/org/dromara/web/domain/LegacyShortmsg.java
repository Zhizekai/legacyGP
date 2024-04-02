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
 * 短消息对象 legacy_shortmsg
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("legacy_shortmsg")
public class LegacyShortmsg extends FrontBaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 短消息id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 短消息内容
     */
    private String content;

    /**
     * 图片
     */
    private String images;

    /**
     * 分组
     */
    private String smGroup;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifiedDate;


}
