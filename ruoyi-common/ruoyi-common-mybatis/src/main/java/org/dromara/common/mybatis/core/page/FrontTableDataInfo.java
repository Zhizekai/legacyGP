package org.dromara.common.mybatis.core.page;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author Lion Li
 */

@Data
@NoArgsConstructor
public class FrontTableDataInfo<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<T> data;

    /**
     * 消息状态码
     */
    private int code;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public FrontTableDataInfo(List<T> list, long total) {
        this.data = list;
        this.total = total;
    }

    public static <T> FrontTableDataInfo<T> build(IPage<T> page) {
        FrontTableDataInfo<T> rspData = new FrontTableDataInfo<>();
        rspData.setCode(HttpStatus.HTTP_OK);
        rspData.setMsg("查询成功");
        rspData.setData(page.getRecords());
        rspData.setTotal(page.getTotal());
        return rspData;
    }

    public static <T> FrontTableDataInfo<T> build(List<T> list) {
        FrontTableDataInfo<T> rspData = new FrontTableDataInfo<>();
        rspData.setCode(HttpStatus.HTTP_OK);
        rspData.setMsg("查询成功");
        rspData.setData(list);
        rspData.setTotal(list.size());
        return rspData;
    }

    public static <T> FrontTableDataInfo<T> build() {
        FrontTableDataInfo<T> rspData = new FrontTableDataInfo<>();
        rspData.setCode(HttpStatus.HTTP_OK);
        rspData.setMsg("查询成功");
        return rspData;
    }

}
