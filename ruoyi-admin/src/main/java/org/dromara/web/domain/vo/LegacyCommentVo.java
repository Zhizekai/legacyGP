package org.dromara.web.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.web.domain.LegacyComment;
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
 * 评论视图对象 legacy_comment
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LegacyComment.class)
public class LegacyCommentVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @ExcelProperty(value = "评论id")
    private Long id;

    /**
     * 文章或沸点id
     */
    @ExcelProperty(value = "文章或沸点id")
    private Long sourceId;

    /**
     * (1:文章，2：沸点）
     */
    @ExcelProperty(value = "(1:文章，2：沸点）")
    private Integer sourceType;

    /**
     * （source,comment,reply）
     */
    @ExcelProperty(value = "", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "s=ource,comment,reply")
    private String type;

    /**
     * 父评论id
     */
    @ExcelProperty(value = "父评论id")
    private Long parentId;

    /**
     * 回复某个评论的id
     */
    @ExcelProperty(value = "回复某个评论的id")
    private Long replyId;

    /**
     * 评论对象创建者id
     */
    @ExcelProperty(value = "评论对象创建者id")
    private Long targetUser;

    /**
     * 评论的内容
     */
    @ExcelProperty(value = "评论的内容")
    private String content;

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
