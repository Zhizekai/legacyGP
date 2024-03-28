package org.dromara.web.domain.bo;

import org.dromara.common.mybatis.core.domain.FrontBaseEntity;
import org.dromara.web.domain.LegacyArticle;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;

/**
 * 文章业务对象 legacy_article
 *
 * @author Lion Li
 * @date 2024-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = LegacyArticle.class, reverseConvertGenerate = false)
public class LegacyArticleBo extends FrontBaseEntity {

    /**
     * 文章id
     */
    @NotNull(message = "文章id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 文章标题
     */
    @NotBlank(message = "文章标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String title;

    /**
     * 文章介绍
     */
    @NotBlank(message = "文章介绍不能为空", groups = { AddGroup.class, EditGroup.class })
    private String intro;

    /**
     * 文章内容
     */
    @NotBlank(message = "文章内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 文章类型id
     */
    @NotBlank(message = "文章类型id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String category;

    /**
     * 状态（1：正常，0：下架）
     */
    @NotNull(message = "状态（1：正常，0：下架）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer status;

    /**
     * 访问量
     */
    @NotNull(message = "访问量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer pageView;

    /**
     * 作者id
     */
    @NotNull(message = "作者id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long author;

    /**
     * 创建时间
     */
    @NotNull(message = "创建时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date createDate;

    /**
     * 修改时间
     */
    @NotNull(message = "修改时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date modifiedDate;


}
