package org.dromara.web.frontController;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.web.domain.LegacyMessage;
import org.dromara.web.domain.bo.LegacyCommentBo;
import org.dromara.web.domain.bo.LegacyMessageBo;
import org.dromara.web.domain.vo.LegacyCommentVo;
import org.dromara.web.service.ILegacyCommentService;
import org.dromara.web.service.ILegacyMessageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论
 *
 * @author keloid
 * @date 2024-03-28
 */
//@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/web/comments")
@Tag(name = "前台评论模块")
@SaIgnore
public class LegacyCommentFrontController extends BaseController {

    private final ILegacyCommentService legacyCommentService;

    private final ILegacyMessageService messageService;

    /**
     * 查询评论列表
     */
//    @SaCheckPermission("web:comment:list")
    @GetMapping("/getArticleList")
    @Operation(summary = "获取所有评论列表")

    public TableDataInfo<LegacyCommentVo> list(LegacyCommentBo bo, PageQuery pageQuery) {
        return legacyCommentService.queryPageList(bo, pageQuery);
    }

    @GetMapping("/list/{sourceId}")
    @Operation(summary = "获取前台某个文章的评论列表")

    public TableDataInfo<LegacyCommentVo> getCommentsByArticleId(
        @NotEmpty(message = "文章id不能为空") @PathVariable Long sourceId , PageQuery pageQuery) {

        LegacyCommentBo legacyCommentBo = new LegacyCommentBo();
        legacyCommentBo.setSourceId(sourceId);
        return legacyCommentService.queryPageList(legacyCommentBo, pageQuery);

    }


    @GetMapping("/myList")
    @Operation(summary = "获取我的评论列表")
    public TableDataInfo<LegacyCommentVo> getCommentsByUserId(PageQuery pageQuery) {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        LegacyCommentBo legacyCommentBo = new LegacyCommentBo();
        legacyCommentBo.setTargetUser(loginIdAsLong);
        legacyCommentBo.setSourceType(2);
        return legacyCommentService.queryPageList(legacyCommentBo, pageQuery);

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
    @PostMapping("/create")
    @Operation(summary = "新增前台评论")
    public R<Void> add( @RequestBody LegacyCommentBo bo) {

        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        bo.setCreateBy(loginIdAsLong);


        Boolean aBoolean = legacyCommentService.insertByBo(bo);
        if (aBoolean) {
            LegacyMessageBo legacyMessageBo = new LegacyMessageBo();
            legacyMessageBo.setSourceId(bo.getId());
            legacyMessageBo.setType(1);
            legacyMessageBo.setUserId(bo.getTargetUser());

            return toAjax(messageService.insertByBo(legacyMessageBo));

        }else {
            return toAjax(false);
        }
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
    @DeleteMapping("/remove/{ids}")
    @SaIgnore
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(legacyCommentService.deleteWithValidByIds(List.of(ids), true));
    }
}
