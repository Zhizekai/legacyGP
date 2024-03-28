package org.dromara.web.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.web.domain.LegacyShortmsg;
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
 * 短消息视图对象 legacy_shortmsg
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LegacyShortmsg.class)
public class LegacyShortmsgVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 短消息id
     */
    @ExcelProperty(value = "短消息id")
    private Long id;

    /**
     * 短消息内容
     */
    @ExcelProperty(value = "短消息内容")
    private String content;

    /**
     * 图片
     */
    @ExcelProperty(value = "图片")
    private String images;

    /**
     * 分组
     */
    @ExcelProperty(value = "分组")
    private String smGroup;

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
