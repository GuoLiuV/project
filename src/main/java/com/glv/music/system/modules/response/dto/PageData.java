package com.glv.music.system.modules.response.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.glv.music.system.modules.request.dto.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据
 *
 * @author ZHOUXIANG
 */
@Data
@Accessors(chain = true)
@ApiModel
public class PageData<T> {

    @ApiModelProperty(value = "当前页码", example = "1")
    private long page;

    @ApiModelProperty(value = "每页显示的记录数", example = "1")
    private long rows;

    @ApiModelProperty(value = "总记录数", example = "100000")
    private long total;

    @ApiModelProperty(value = "总页数", example = "39")
    private long pages;

    @ApiModelProperty(value = "数据")
    private List<T> content;

    public PageData() {
    }

    public <R> PageData(PageRequest<R> pageRequest) {
        this.page = pageRequest.getPage();
        this.rows = pageRequest.getRows();
        this.total = 0L;
        this.pages = 0L;
        this.content = new ArrayList<>();
    }

    public PageData(IPage<T> page) {
        this.page = page.getCurrent();
        this.rows = page.getSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
        this.content = page.getRecords();
    }

    public PageData(Page<T> page) {
        // spring data 分页第1页从0开始计
        this.page = page.getNumber() + 1;
        this.rows = page.getSize();
        this.total = page.getTotalElements();
        this.pages = page.getTotalPages();
        this.content = page.getContent();
    }

    /**
     * 计算总页数
     */
    public PageData<T> calcPages() {
        this.pages = this.total % this.rows == 0 ? this.total / this.rows
                : (total / this.rows + 1);
        return this;
    }
}
