package org.dromara.web.domain.bo;

import org.dromara.common.mybatis.core.domain.FrontBaseEntity;
import org.dromara.web.domain.LegacyMessage;
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
 * 消息业务对象 legacy_message
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = LegacyMessage.class, reverseConvertGenerate = false)
public class LegacyMessageBo extends FrontBaseEntity {

    /**
     * 消息id
     */
    @NotNull(message = "消息id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 接受消息的用户 ID
     */
    @NotNull(message = "接受消息的用户 ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 评论/收藏/关注的 ID
     */
    @NotNull(message = "评论/收藏/关注的 ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sourceId;

    /**
     * 1: 评论，2: 收藏和赞，3: 新增粉丝
     */
    @NotNull(message = "1: 评论，2: 收藏和赞，3: 新增粉丝不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer type;

    /**
     * 0: 未读，1: 已读
     */
    @NotNull(message = "0: 未读，1: 已读不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer status;

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
