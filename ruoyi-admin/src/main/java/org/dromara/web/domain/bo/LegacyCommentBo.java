package org.dromara.web.domain.bo;

import org.dromara.common.mybatis.core.domain.FrontBaseEntity;
import org.dromara.web.domain.LegacyComment;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 评论业务对象 legacy_comment
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = LegacyComment.class, reverseConvertGenerate = false)
public class LegacyCommentBo extends FrontBaseEntity {

    /**
     * 评论id
     */
    @NotNull(message = "评论id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 文章或沸点id
     */
    @NotNull(message = "文章或沸点id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sourceId;

    /**
     * (1:文章，2：沸点）
     */
    @NotNull(message = "(1:文章，2：沸点）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer sourceType;

    /**
     * （source,comment,reply）
     */
    @NotBlank(message = "（source,comment,reply）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 父评论id
     */
    @NotNull(message = "父评论id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;

    /**
     * 回复某个评论的id
     */
    @NotNull(message = "回复某个评论的id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long replyId;

    /**
     * 评论对象创建者id
     */
    @NotNull(message = "评论对象创建者id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long targetUser;

    /**
     * 评论的内容
     */
    @NotBlank(message = "评论的内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

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


    private Long createBy;


}
