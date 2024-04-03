package org.dromara.web.frontController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
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
import org.dromara.web.domain.bo.LegacyPraiseBo;
import org.dromara.web.domain.vo.LegacyPraiseVo;
import org.dromara.web.service.ILegacyPraiseService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 点赞
 *
 * @author Lion Li
 * @date 2024-03-30
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/web/praises")
public class LegacyPraiseFrontController extends BaseController {

    private final ILegacyPraiseService legacyPraiseService;

    /**
     * 查询点赞列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取所有收藏和点赞列表")
    public TableDataInfo<LegacyPraiseVo> list(LegacyPraiseBo bo, PageQuery pageQuery) {
        return legacyPraiseService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取点赞详细信息
     *
     * @param id 主键
     */

    @GetMapping("/{id}")
    @Operation(summary = "获取点赞详细信息")
    public R<LegacyPraiseVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyPraiseService.queryById(id));
    }

    // 获取我的点赞和收藏列表

    @GetMapping("/myList")
    @Operation(summary = "获取我的点赞和收藏列表")
    public TableDataInfo<LegacyPraiseVo> getMyPraiseList(PageQuery pageQuery) {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        LegacyPraiseBo legacyPraiseBo = new LegacyPraiseBo();
        legacyPraiseBo.setTargetUser(loginIdAsLong);
        return legacyPraiseService.queryPageList(legacyPraiseBo, pageQuery);
    }

    /**
     * 新增点赞
     */
    @RepeatSubmit()
    @PostMapping("/toggle")
    public R<Void> add(@RequestBody LegacyPraiseBo bo) {
        // 如果点赞过就取消点赞
        List<LegacyPraiseVo> legacyPraiseVos = legacyPraiseService.queryList(bo);
        if (legacyPraiseVos.size() == 1) {
            Collection<Long> longs = new ArrayList<>();
            longs.add(legacyPraiseVos.get(0).getId());
            return toAjax(legacyPraiseService.deleteWithValidByIds(longs, false));
        } else {
            // 如果没有点赞就添加点赞
            return toAjax(legacyPraiseService.insertByBo(bo));
        }
    }

    /**
     * 修改点赞
     */
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
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyPraiseService.deleteWithValidByIds(List.of(ids), true));
    }
}
