package com.glv.music.system.modules.request.dto;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glv.music.system.utils.ObjectUtils;
import com.glv.music.system.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 分页请求接口
 *
 * @author ZHOUXIANG
 */
@Data
@Slf4j
@ApiModel(description = "分页查询请求接口")
public class PageRequest<T> {

    @ApiModelProperty(value = "当前页码", example = "1")
    private long page;

    @ApiModelProperty(value = "当前页显示条数", example = "15")
    private long rows;

    @ApiModelProperty(value = "排序,field:sort形式")
    private String sort;

    @ApiModelProperty(value = "请求条件数据")
    T condition;

    /**
     * 构建Mybatis Plus分页查询
     *
     * @return Mybatis Plus分页插件
     */
    public <R> Page<R> buildMybatisPlusPage() {
        return new Page<>(this.page, this.rows);
    }

    /**
     * 获取Spring Data 分页组件
     *
     * @return 分页组件
     */
    public Pageable getPageable() {
        Sort sort = Sort.unsorted();
        if (StringUtils.isNotBlank(this.sort)) {
            String[] arr = StringUtils.split(this.sort, ":");
            int len = 2;
            if (ObjectUtils.notNull(arr) && arr.length == len) {
                Sort.Direction direction = Sort.Direction.ASC;
                String desc = "desc";
                if (desc.equalsIgnoreCase(arr[1])) {
                    direction = Sort.Direction.DESC;
                }
                sort = Sort.by(direction, arr[0]);
            }
        }
        // spring data的第1页从0开始
        this.page = Math.max(0, this.page - 1);
        return org.springframework.data.domain
                .PageRequest.of((int) this.page, (int) this.rows, sort);
    }

    /**
     * 获取当前页起始记录
     *
     * @param flag 0/1，从0开始还是从1开始
     * @return 当前页起始记录
     */
    public long getStart(int flag) {
        long start = (this.page - 1) * this.rows;
        return flag == 0 ? start : start + 1;
    }
}
