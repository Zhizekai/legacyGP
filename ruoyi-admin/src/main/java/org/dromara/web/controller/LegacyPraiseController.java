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
import org.dromara.web.domain.vo.LegacyPraiseVo;
import org.dromara.web.domain.bo.LegacyPraiseBo;
import org.dromara.web.service.ILegacyPraiseService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 点赞
 *
 * @author Lion Li
 * @date 2024-03-30
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/web/praise")
public class LegacyPraiseController extends BaseController {

    private final ILegacyPraiseService legacyPraiseService;

    /**
     * 查询点赞列表
     */
    @SaCheckPermission("web:praise:list")
    @GetMapping("/list")
    public TableDataInfo<LegacyPraiseVo> list(LegacyPraiseBo bo, PageQuery pageQuery) {
        return legacyPraiseService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出点赞列表
     */
    @SaCheckPermission("web:praise:export")
    @Log(title = "点赞", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(LegacyPraiseBo bo, HttpServletResponse response) {
        List<LegacyPraiseVo> list = legacyPraiseService.queryList(bo);
        ExcelUtil.exportExcel(list, "点赞", LegacyPraiseVo.class, response);
    }

    /**
     * 获取点赞详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("web:praise:query")
    @GetMapping("/{id}")
    public R<LegacyPraiseVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyPraiseService.queryById(id));
    }

    /**
     * 新增点赞
     */
    @SaCheckPermission("web:praise:add")
    @Log(title = "点赞", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LegacyPraiseBo bo) {
        return toAjax(legacyPraiseService.insertByBo(bo));
    }

    /**
     * 修改点赞
     */
    @SaCheckPermission("web:praise:edit")
    @Log(title = "点赞", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LegacyPraiseBo bo) {
        return toAjax(legacyPraiseService.updateByBo(bo));
    }

    /**
     * 删除点赞
     *
     * @param ids 主键串
     */
    @SaCheckPermission("web:praise:remove")
    @Log(title = "点赞", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyPraiseService.deleteWithValidByIds(List.of(ids), true));
    }
}
