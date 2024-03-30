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
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.web.domain.bo.LegacyArticleBo;
import org.dromara.web.domain.vo.LegacyArticleVo;
import org.dromara.web.service.ILegacyArticleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章
 *
 * @author Lion Li
 * @date 2024-03-27
 */
//@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/web/arts")
@Tag(name = "前台文章模块")
public class LegacyArticleFrontController extends BaseController {

    private final ILegacyArticleService legacyArticleService;

    /**
     * 查询文章列表
     */
//    @SaCheckPermission("system:article:list")
    @GetMapping("/list")
    @Operation(summary = "获取前台文章列表")
    @SaIgnore
    public TableDataInfo<LegacyArticleVo> list(LegacyArticleBo bo, PageQuery pageQuery) {
        System.out.println(bo.toString());
        return legacyArticleService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取文章详细信息
     *
     * @param id 主键
     */
//    @SaCheckPermission("system:article:query")
    @GetMapping("/detail/{id}")
    @Operation(summary = "获取前台文章详细信息")
    @SaIgnore
    public R<LegacyArticleVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyArticleService.queryById(id));
    }

    /**
     * 新增文章
     */
//    @SaCheckPermission("system:article:add")
//    @Log(title = "文章", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/create")
    @Operation(summary = "新增前台文章")
    @SaIgnore
    public R<Void> add(@RequestBody LegacyArticleBo bo) {
        return toAjax(legacyArticleService.insertByBo(bo));
    }


    // 发布文章
    @PostMapping("/publish/{id}")
    @Operation(summary = "发布前台文章")
    @SaIgnore
    public R<String> publishArticle(@PathVariable Long id, Long author) {
        LegacyArticleVo legacyArticleVo = legacyArticleService.queryById(id);
        legacyArticleVo.setStatus(1);
        legacyArticleVo.setAuthor(author);
        LegacyArticleBo legacyArticleBo = MapstructUtils.convert(legacyArticleVo, LegacyArticleBo.class);
        if (legacyArticleService.updateByBo(legacyArticleBo)) {
            return R.ok("发布成功");
        }else {
            return R.fail("文档没找到，发布失败");
        }
    }
    // 修改文章
//    @SaCheckPermission("system:article:edit")
//    @Log(title = "文章", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/update/{id}")
    @Operation(summary = "修改前台文章")
    @SaIgnore
    public R<Void> edit(@PathVariable Long id, @RequestBody LegacyArticleBo bo) {
        // 防止修改除了allow_keys 以外的其他属性。这个可以之后先转成json，在json里删除之后再变成对象
//        let allow_keys = ['title', 'intro', 'content', 'category', 'tags']
//        Object.keys(body).forEach(key => {
//        if (!allow_keys.includes(key)) {
//            delete body[key]
//         }
//        })
        bo.setId(id);
        return toAjax(legacyArticleService.updateByBo(bo));
    }

    /**
     * 删除文章
     *
     * @param ids 主键串
     */
//    @SaCheckPermission("system:article:remove")
//    @Log(title = "文章", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{ids}")
    @Operation(summary = "删除前台文章")
    @SaIgnore
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyArticleService.deleteWithValidByIds(List.of(ids), true));
    }
}
