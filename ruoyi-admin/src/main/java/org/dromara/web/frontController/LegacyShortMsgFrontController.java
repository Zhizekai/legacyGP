package org.dromara.web.frontController;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.google.api.client.repackaged.com.google.common.base.Joiner;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.web.domain.bo.LegacyShortmsgBo;
import org.dromara.web.domain.vo.LegacyShortmsgVo;
import org.dromara.web.service.ILegacyShortmsgService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短消息，类似知乎的想法
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/web/shortMsg")
@Tag(name = "前台短消息沸点模块")
public class LegacyShortMsgFrontController extends BaseController {

    private final ILegacyShortmsgService legacyShortMsgService;

    // 查询短消息列表

    @GetMapping("/lists")
    public TableDataInfo<LegacyShortmsgVo> list(LegacyShortmsgBo bo, PageQuery pageQuery) {

        // 这里建议前端直接把userId 放到createdBy里面
        return legacyShortMsgService.queryPageList(bo, pageQuery);
    }


    // 获取短消息详细信息

    @GetMapping("/{id}")
    public R<LegacyShortmsgVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyShortMsgService.queryById(id));
    }

    // 新增短消息
//    @Log(title = "短消息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/create")
    public R<Void> add(@RequestBody LegacyShortmsgBo bo) {

        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        bo.setCreatedBy(loginIdAsLong);

        // TODO 图片列表
//        String a1 = Joiner.on(",").join(bo.getImages());

        // 这里建议前端直接把userId 放到createdBy里面
        return toAjax(legacyShortMsgService.insertByBo(bo));
    }

    // 修改短消息
//    @Log(title = "短消息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LegacyShortmsgBo bo) {
        return toAjax(legacyShortMsgService.updateByBo(bo));
    }

    // 删除短消息
//    @Log(title = "短消息", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyShortMsgService.deleteWithValidByIds(List.of(ids), true));
    }
}
