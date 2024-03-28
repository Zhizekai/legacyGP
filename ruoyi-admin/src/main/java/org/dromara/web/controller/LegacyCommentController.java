package org.dromara.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.web.domain.vo.LegacyCommentVo;
import org.dromara.web.domain.bo.LegacyCommentBo;
import org.dromara.web.service.ILegacyCommentService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 评论
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/web/comment")
@Tag(name = "后台评论模块")
public class LegacyCommentController extends BaseController {

    private final ILegacyCommentService legacyCommentService;

    /**
     * 查询评论列表
     */
    @SaCheckPermission("web:comment:list")
    @GetMapping("/list")
    public TableDataInfo<LegacyCommentVo> list(LegacyCommentBo bo, PageQuery pageQuery) {
        return legacyCommentService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出评论列表
     */
    @SaCheckPermission("web:comment:export")
    @Log(title = "评论", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(LegacyCommentBo bo, HttpServletResponse response) {
        List<LegacyCommentVo> list = legacyCommentService.queryList(bo);
        ExcelUtil.exportExcel(list, "评论", LegacyCommentVo.class, response);
    }

    /**
     * 获取评论详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("web:comment:query")
    @GetMapping("/{id}")
    public R<LegacyCommentVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyCommentService.queryById(id));
    }

    /**
     * 新增评论
     */
    @SaCheckPermission("web:comment:add")
    @Log(title = "评论", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LegacyCommentBo bo) {
        return toAjax(legacyCommentService.insertByBo(bo));
    }

    /**
     * 修改评论
     */
    @SaCheckPermission("web:comment:edit")
    @Log(title = "评论", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LegacyCommentBo bo) {
        return toAjax(legacyCommentService.updateByBo(bo));
    }

    /**
     * 删除评论
     *
     * @param ids 主键串
     */
    @SaCheckPermission("web:comment:remove")
    @Log(title = "评论", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyCommentService.deleteWithValidByIds(List.of(ids), true));
    }
}
