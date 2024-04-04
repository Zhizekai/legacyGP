package org.dromara.web.domain.bo;

import org.dromara.common.mybatis.core.domain.FrontBaseEntity;
import org.dromara.web.domain.LegacyShortmsg;
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
 * 短消息业务对象 legacy_shortmsg
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = LegacyShortmsg.class, reverseConvertGenerate = false)
public class LegacyShortmsgBo extends FrontBaseEntity {

    /**
     * 短消息id
     */
    @NotNull(message = "短消息id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 短消息内容
     */
    @NotBlank(message = "短消息内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 图片
     */
    @NotBlank(message = "图片不能为空", groups = { AddGroup.class, EditGroup.class })
    private String images;

    /**
     * 分组
     */
    @NotBlank(message = "分组不能为空", groups = { AddGroup.class, EditGroup.class })
    private String smGroup;

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



    private Long createdBy;


}
