package org.dromara.web.service;

import org.dromara.web.domain.LegacyPraise;
import org.dromara.web.domain.vo.LegacyPraiseVo;
import org.dromara.web.domain.bo.LegacyPraiseBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 点赞Service接口
 *
 * @author Lion Li
 * @date 2024-03-30
 */
public interface ILegacyPraiseService {

    /**
     * 查询点赞
     */
    LegacyPraiseVo queryById(Long id);

    /**
     * 查询点赞列表
     */
    TableDataInfo<LegacyPraiseVo> queryPageList(LegacyPraiseBo bo, PageQuery pageQuery);

    /**
     * 查询点赞列表
     */
    List<LegacyPraiseVo> queryList(LegacyPraiseBo bo);

    /**
     * 新增点赞
     */
    Boolean insertByBo(LegacyPraiseBo bo);

    /**
     * 修改点赞
     */
    Boolean updateByBo(LegacyPraiseBo bo);

    /**
     * 校验并批量删除点赞信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
