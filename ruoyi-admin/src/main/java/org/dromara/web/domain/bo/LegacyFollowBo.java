package org.dromara.web.domain.bo;

import org.dromara.common.mybatis.core.domain.FrontBaseEntity;
import org.dromara.web.domain.LegacyFollow;
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
 * 关注业务对象 legacy_follow
 *
 * @author Lion Li
 * @date 2024-03-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = LegacyFollow.class, reverseConvertGenerate = false)
public class LegacyFollowBo extends FrontBaseEntity {

    /**
     * follow的id
     */
    @NotNull(message = "follow的id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 粉丝id
     */
    @NotNull(message = "粉丝id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long fansId;

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
