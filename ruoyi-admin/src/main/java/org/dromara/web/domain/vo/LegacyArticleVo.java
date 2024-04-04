package org.dromara.web.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.dromara.web.domain.LegacyArticle;
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
 * 文章视图对象 legacy_article
 *
 * @author Lion Li
 * @date 2024-03-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LegacyArticle.class)
public class LegacyArticleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    @ExcelProperty(value = "文章id")
    private Long id;

    /**
     * 文章标题
     */
    @ExcelProperty(value = "文章标题")
    private String title;

    /**
     * 文章介绍
     */
    @ExcelProperty(value = "文章介绍")
    private String introduction;

    /**
     * 文章内容
     */
    @ExcelProperty(value = "文章内容")
    private String content;

    /**
     * 文章类型id
     */
    @ExcelProperty(value = "文章类型id")
    private String category;

    /**
     * 状态（1：正常，0：下架）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=：正常，0：下架")
    private Integer status;

    /**
     * 访问量
     */
    @ExcelProperty(value = "访问量")
    private Integer pageView;

    /**
     * 作者id
     */
    @ExcelProperty(value = "作者id")
    private Long author;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LegacyUser user;

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

    // 点赞数
    private Long praiseCount;

    // 收藏数
    private Long collectionCount;


}
