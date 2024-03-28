package org.dromara.web.frontController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.web.domain.bo.LegacyCommentBo;
import org.dromara.web.domain.vo.LegacyCommentVo;
import org.dromara.web.service.ILegacyCommentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论
 *
 * @author Lion Li
 * @date 2024-03-28
 */
//@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/web/comment")
@Tag(name = "前台评论模块")
public class LegacyCommentFrontController extends BaseController {

    private final ILegacyCommentService legacyCommentService;

    /**
     * 查询评论列表
     */
//    @SaCheckPermission("web:comment:list")
    @GetMapping("/list")
    @Operation(summary = "获取前台评论列表")
    @SaIgnore
    public TableDataInfo<LegacyCommentVo> list(LegacyCommentBo bo, PageQuery pageQuery) {
        return legacyCommentService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取评论详细信息
     *
     * @param id 主键
     */
//    @SaCheckPermission("web:comment:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取前台评论详细信息")
    public R<LegacyCommentVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyCommentService.queryById(id));
    }

    /**
     * 新增评论
     */
//    @SaCheckPermission("web:comment:add")
//    @Log(title = "评论", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增前台评论")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LegacyCommentBo bo) {
        return toAjax(legacyCommentService.insertByBo(bo));
    }

    /**
     * 修改评论
     */
//    @SaCheckPermission("web:comment:edit")
//    @Log(title = "评论", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/editList")
    @SaIgnore
    public R<Void> edit(@RequestBody LegacyCommentBo bo) {
        return toAjax(legacyCommentService.updateByBo(bo));
    }

    /**
     * 删除评论
     *
     * @param ids 主键串
     */
//    @SaCheckPermission("web:comment:remove")
//    @Log(title = "评论", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyCommentService.deleteWithValidByIds(List.of(ids), true));
    }
}
