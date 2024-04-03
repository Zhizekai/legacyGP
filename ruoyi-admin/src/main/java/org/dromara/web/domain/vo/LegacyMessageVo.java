package org.dromara.web.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.web.domain.LegacyMessage;
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
 * 消息视图对象 legacy_message
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LegacyMessage.class)
public class LegacyMessageVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    @ExcelProperty(value = "消息id")
    private Long id;

    /**
     * 接受消息的用户 ID
     */
    @ExcelProperty(value = "接受消息的用户 ID")
    private Long userId;

    /**
     * 评论/收藏/关注的 ID
     */
    @ExcelProperty(value = "评论/收藏/关注的 ID")
    private Long sourceId;

    /**
     * 1: 评论，2: 收藏和赞，3: 新增粉丝
     */
    @ExcelProperty(value = "1: 评论，2: 收藏和赞，3: 新增粉丝")
    private Integer type;

    /**
     * 0: 未读，1: 已读
     */
    @ExcelProperty(value = "0: 未读，1: 已读")
    private Integer status;

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


    private Integer count;

}
