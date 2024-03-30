package org.dromara.web.frontController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
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
import org.dromara.web.domain.bo.LegacyFollowBo;
import org.dromara.web.domain.vo.LegacyFollowVo;
import org.dromara.web.service.ILegacyFollowService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 关注
 *
 * @author Lion Li
 * @date 2024-03-30
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/web/follow")
public class LegacyFollowFrontController extends BaseController {

    private final ILegacyFollowService legacyFollowService;

    /**
     * 查询关注列表
     */
    @GetMapping("/list")
    public TableDataInfo<LegacyFollowVo> list(LegacyFollowBo bo, PageQuery pageQuery) {
        return legacyFollowService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出关注列表
     */

    @PostMapping("/export")
    public void export(LegacyFollowBo bo, HttpServletResponse response) {
        List<LegacyFollowVo> list = legacyFollowService.queryList(bo);
        ExcelUtil.exportExcel(list, "关注", LegacyFollowVo.class, response);
    }

    /**
     * 获取关注详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<LegacyFollowVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyFollowService.queryById(id));
    }

    /**
     * 新增关注
     */
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LegacyFollowBo bo) {
        return toAjax(legacyFollowService.insertByBo(bo));
    }

    /**
     * 修改关注
     */
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LegacyFollowBo bo) {
        return toAjax(legacyFollowService.updateByBo(bo));
    }

    /**
     * 删除关注
     *
     * @param ids 主键串
     */
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyFollowService.deleteWithValidByIds(List.of(ids), true));
    }
}
