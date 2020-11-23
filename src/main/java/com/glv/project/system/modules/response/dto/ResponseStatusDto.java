package com.glv.project.system.modules.response.dto;

import com.glv.project.system.modules.response.enums.ResponseStatusEnum;
import com.glv.project.system.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用于后台处理返回的JSON格式数据的Bean。
 * 建议后台返回的所有JSON数据都使用此类进行封装。
 *
 * @author Oscar
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "请求响应数据模型")
public class ResponseStatusDto<T> implements Serializable {

    /**
     * 返回的状态信息
     */
    @ApiModelProperty("响应状态名称")
    private String status = ResponseStatusEnum.FAILURE.name;

    /**
     * 返回的状态代码
     */
    @ApiModelProperty("响应状态代码")
    private String code = ResponseStatusEnum.FAILURE.code;

    /**
     * 返回的提示消息
     */
    @ApiModelProperty("响应消息")
    private String message;

    /**
     * 附加返回的数据
     */
    @ApiModelProperty("响应数据")
    private T data;

    public boolean isSuccess() {
        return StringUtils.equalsIgnoreCase(
                status, ResponseStatusEnum.SUCCESS.name);
    }

    public static <T> ResponseStatusDto<T> success() {
        return build(ResponseStatusEnum.SUCCESS, "操作成功", null);
    }

    public static <T> ResponseStatusDto<T> success(T data) {
        return build(ResponseStatusEnum.SUCCESS, "操作成功", data);
    }

    public static <T> ResponseStatusDto<T> success(T data, String message) {
        return build(ResponseStatusEnum.SUCCESS, message, data);
    }

    public static <T> ResponseStatusDto<T> failure() {
        return build(ResponseStatusEnum.FAILURE, "操作失败", null);
    }

    public static <T> ResponseStatusDto<T> failure(String message) {
        return build(ResponseStatusEnum.FAILURE, message, null);
    }

    public static <T> ResponseStatusDto<T> warning(String message) {
        return build(ResponseStatusEnum.WARNING, message, null);
    }

    public static <T> ResponseStatusDto<T> notAuthority(String message) {
        return build(ResponseStatusEnum.NOT_AUTHORITY, message, null);
    }

    public static <T> ResponseStatusDto<T> notAuthentication(String message) {
        return build(ResponseStatusEnum.NOT_AUTHENTICATION, message, null);
    }

    private static <T> ResponseStatusDto<T> build(ResponseStatusEnum responseStatusEnum, String message, T data) {
        return new ResponseStatusDto<T>()
                .setStatus(responseStatusEnum.name)
                .setCode(responseStatusEnum.code)
                .setMessage(message)
                .setData(data);
    }

}
