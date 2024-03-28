package org.dromara.web.domain.bo;

import org.dromara.common.mybatis.core.domain.FrontBaseEntity;
import org.dromara.web.domain.LegacyUser;
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
 * 用户业务对象 legacy_user
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = LegacyUser.class, reverseConvertGenerate = false)
public class LegacyUserBo extends FrontBaseEntity {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String phone;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String password;

    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空", groups = { AddGroup.class, EditGroup.class })
    private String avatar;

    /**
     * 介绍信息
     */
    @NotBlank(message = "介绍信息不能为空", groups = { AddGroup.class, EditGroup.class })
    private String introduc;

    /**
     * 位置信息
     */
    @NotBlank(message = "位置信息不能为空", groups = { AddGroup.class, EditGroup.class })
    private String position;

    /**
     * 公司
     */
    @NotBlank(message = "公司不能为空", groups = { AddGroup.class, EditGroup.class })
    private String company;

    /**
     * 能力
     */
    @NotBlank(message = "能力不能为空", groups = { AddGroup.class, EditGroup.class })
    private String juePower;

    /**
     * 好评
     */
    @NotNull(message = "好评不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer goodNum;

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer readNum;

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
