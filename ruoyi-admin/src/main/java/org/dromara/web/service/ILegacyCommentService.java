package org.dromara.web.service;

import org.dromara.web.domain.LegacyComment;
import org.dromara.web.domain.vo.LegacyCommentVo;
import org.dromara.web.domain.bo.LegacyCommentBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 评论Service接口
 *
 * @author Lion Li
 * @date 2024-03-28
 */
public interface ILegacyCommentService {

    /**
     * 查询评论
     */
    LegacyCommentVo queryById(Long id);

    /**
     * 查询评论列表
     */
    TableDataInfo<LegacyCommentVo> queryPageList(LegacyCommentBo bo, PageQuery pageQuery);

    /**
     * 查询评论列表
     */
    List<LegacyCommentVo> queryList(LegacyCommentBo bo);

    /**
     * 新增评论
     */
    Boolean insertByBo(LegacyCommentBo bo);

    /**
     * 修改评论
     */
    Boolean updateByBo(LegacyCommentBo bo);

    /**
     * 校验并批量删除评论信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
