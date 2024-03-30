package org.dromara.web.domain.bo;

import org.dromara.common.mybatis.core.domain.FrontBaseEntity;
import org.dromara.web.domain.LegacyPraise;
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
 * 点赞业务对象 legacy_praise
 *
 * @author Lion Li
 * @date 2024-03-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = LegacyPraise.class, reverseConvertGenerate = false)
public class LegacyPraiseBo extends FrontBaseEntity {

    /**
     * 点赞id
     */
    @NotNull(message = "点赞id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 文章或沸点ID
     */
    @NotNull(message = "文章或沸点ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long targetId;

    /**
     * 1: 文章，2: 沸点
     */
    @NotBlank(message = "1: 文章，2: 沸点不能为空", groups = { AddGroup.class, EditGroup.class })
    private String targetType;

    /**
     * 目标用户ID
     */
    @NotNull(message = "目标用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long targetUser;

    /**
     * 1: 点赞，2: 收藏
     */
    @NotNull(message = "1: 点赞，2: 收藏不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer type;

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
