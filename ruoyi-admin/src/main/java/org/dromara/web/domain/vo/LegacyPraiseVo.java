package org.dromara.web.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.web.domain.LegacyPraise;
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
 * 点赞视图对象 legacy_praise
 *
 * @author Lion Li
 * @date 2024-03-30
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LegacyPraise.class)
public class LegacyPraiseVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 点赞id
     */
    @ExcelProperty(value = "点赞id")
    private Long id;

    /**
     * 文章或沸点ID
     */
    @ExcelProperty(value = "文章或沸点ID")
    private Long targetId;

    /**
     * 1: 文章，2: 沸点
     */
    @ExcelProperty(value = "1: 文章，2: 沸点")
    private String targetType;

    /**
     * 目标用户ID
     */
    @ExcelProperty(value = "目标用户ID")
    private Long targetUser;

    /**
     * 1: 点赞，2: 收藏
     */
    @ExcelProperty(value = "1: 点赞，2: 收藏")
    private Integer type;

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


}
