package com.lots.lotswxxw.domain.bo;

//import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页数据封装类
 *
 * @author lots
 */
@Data
public class JsonPage<T> {
    @ApiModelProperty(value = "每页显示多少条记录", example = "20")
    private Long pageSize = 20L;

    @ApiModelProperty(value = "当前页", example = "1")
    private Long page = 1L;

    @ApiModelProperty(value = "总记录数")
    private Long total = 0L;

    @ApiModelProperty(value = "总页数")
    private Long totalPage = 0L;

    @ApiModelProperty(value = "当前页的记录集")
    private List<T> records;

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    /*public static <T> JsonPage<T> restPage(List<T> list) {
        JsonPage<T> result = new JsonPage<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPage(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setRecords(pageInfo.getList());
        return result;
    }*/

    /**
     * 将SpringData分页后的list转为分页信息
     */
    public static <T> JsonPage<T> restPage(Page<T> pageInfo) {
        JsonPage<T> result = new JsonPage<T>();
        result.setTotalPage((long) pageInfo.getTotalPages());
        result.setPage((long) pageInfo.getNumber());
        result.setPageSize((long) pageInfo.getSize());
        result.setTotal(pageInfo.getTotalElements());
        result.setRecords(pageInfo.getContent());
        return result;
    }

}
