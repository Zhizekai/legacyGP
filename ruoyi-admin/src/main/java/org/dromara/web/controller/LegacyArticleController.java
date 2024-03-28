package org.dromara.web.controller;

import java.util.List;

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
import org.dromara.web.domain.vo.LegacyArticleVo;
import org.dromara.web.domain.bo.LegacyArticleBo;
import org.dromara.web.service.ILegacyArticleService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 文章
 *
 * @author Lion Li
 * @date 2024-03-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/article")
public class LegacyArticleController extends BaseController {

    private final ILegacyArticleService legacyArticleService;

    /**
     * 查询文章列表
     */
    @SaCheckPermission("system:article:list")
    @GetMapping("/list")
    public TableDataInfo<LegacyArticleVo> list(LegacyArticleBo bo, PageQuery pageQuery) {
        return legacyArticleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出文章列表
     */
    @SaCheckPermission("system:article:export")
    @Log(title = "文章", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(LegacyArticleBo bo, HttpServletResponse response) {
        List<LegacyArticleVo> list = legacyArticleService.queryList(bo);
        ExcelUtil.exportExcel(list, "文章", LegacyArticleVo.class, response);
    }

    /**
     * 获取文章详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:article:query")
    @GetMapping("/{id}")
    public R<LegacyArticleVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyArticleService.queryById(id));
    }

    /**
     * 新增文章
     */
    @SaCheckPermission("system:article:add")
    @Log(title = "文章", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LegacyArticleBo bo) {
        return toAjax(legacyArticleService.insertByBo(bo));
    }

    /**
     * 修改文章
     */
    @SaCheckPermission("system:article:edit")
    @Log(title = "文章", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LegacyArticleBo bo) {
        return toAjax(legacyArticleService.updateByBo(bo));
    }

    /**
     * 删除文章
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:article:remove")
    @Log(title = "文章", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyArticleService.deleteWithValidByIds(List.of(ids), true));
    }
}