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
 * 文章对象 legacy_article
 *
 * @author Lion Li
 * @date 2024-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("legacy_article")
public class LegacyArticle extends FrontBaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章介绍
     */
    private String intro;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章类型id
     */
    private String category;

    /**
     * 状态（1：正常，0：下架）
     */
    private Integer status;

    /**
     * 访问量
     */
    private Integer pageView;

    /**
     * 作者id
     */
    private Long author;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifiedDate;


}
