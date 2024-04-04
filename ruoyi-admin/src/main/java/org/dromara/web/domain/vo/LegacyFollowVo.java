package org.dromara.web.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.web.domain.LegacyFollow;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.web.domain.LegacyUser;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 关注视图对象 legacy_follow
 *
 * @author Lion Li
 * @date 2024-03-30
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LegacyFollow.class)
public class LegacyFollowVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * follow的id
     */
    @ExcelProperty(value = "follow的id")
    private Long id;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 粉丝id
     */
    @ExcelProperty(value = "粉丝id")
    private Long fansId;

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

    // 粉丝用户信息
    private LegacyUser legacyUser;

}
