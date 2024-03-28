package org.dromara.web.service;

import org.dromara.web.domain.LegacyArticle;
import org.dromara.web.domain.vo.LegacyArticleVo;
import org.dromara.web.domain.bo.LegacyArticleBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 文章Service接口
 *
 * @author Lion Li
 * @date 2024-03-27
 */
public interface ILegacyArticleService {

    /**
     * 查询文章
     */
    LegacyArticleVo queryById(Long id);

    /**
     * 查询文章列表
     */
    TableDataInfo<LegacyArticleVo> queryPageList(LegacyArticleBo bo, PageQuery pageQuery);

    /**
     * 查询文章列表
     */
    List<LegacyArticleVo> queryList(LegacyArticleBo bo);

    /**
     * 新增文章
     */
    Boolean insertByBo(LegacyArticleBo bo);

    /**
     * 修改文章
     */
    Boolean updateByBo(LegacyArticleBo bo);

    /**
     * 校验并批量删除文章信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
