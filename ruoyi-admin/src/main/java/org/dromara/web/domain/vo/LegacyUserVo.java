package org.dromara.web.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.dromara.web.domain.LegacyUser;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 用户视图对象 legacy_user
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LegacyUser.class)
public class LegacyUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long id;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    private String phone;

    /**
     * 用户名
     */
    @ExcelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @ExcelProperty(value = "密码")
    private String password;

    /**
     * 头像
     */
    @ExcelProperty(value = "头像")
    private String avatar;

    /**
     * 介绍信息
     */
    @ExcelProperty(value = "介绍信息")
    private String introduction;

    /**
     * 位置信息
     */
    @ExcelProperty(value = "位置信息")
    private String position;

    /**
     * 公司
     */
    @ExcelProperty(value = "公司")
    private String company;

    /**
     * 能力
     */
    @ExcelProperty(value = "能力")
    private String juePower;

    /**
     * 好评
     */
    @ExcelProperty(value = "好评")
    private Integer goodNum;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Integer readNum;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createDate;

    /**
     * 修改时间
     */
    @ExcelProperty(value = "修改时间")
    private Date modifiedDate;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token; // 用户的token

}
