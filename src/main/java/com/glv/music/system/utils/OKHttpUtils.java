package com.glv.music.system.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 主要使用okHttp库进行http请求访问的操作
 *
 * @author ZHOUXIANG
 */
@Slf4j
public class OKHttpUtils {

    /**
     * okHttp get请求
     *
     * @param url 请求地址
     * @return 响应内容，如果响应失败返回null
     */
    public static String get(String url, Map<String, Object> params) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url))
                .newBuilder();
        if (ObjectUtils.notNull(params)) {
            params.forEach((key, value) -> urlBuilder.addQueryParameter(key, String.valueOf(value)));
        }
        Request request = new Request.Builder()
                .url(urlBuilder.build()).get().build();
        return call(request);
    }

    /**
     * okHttp post提交数据并返回字符串响应
     *
     * @param url    请求地址
     * @param params 请求的参数
     * @return 返回的字符串
     */
    public static String postForm(String url, Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (ObjectUtils.notNull(params)) {
            params.forEach((key, value) -> builder.add(key, String.valueOf(value)));
        }
        Request request = new Request.Builder()
                .url(url).post(builder.build()).build();
        return call(request);
    }

    /**
     * 提交JSON对象字符串
     *
     * @param url    请求地址
     * @param object 请求参数对象
     * @return 响应字符串
     */
    public static String postJson(String url, Object object) {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        String jsonString = JSONUtils.obj2Json(object);
        assert jsonString != null;
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(type, jsonString))
                .build();
        return call(request);
    }

    /**
     * 文件上传
     *
     * @param url  请求的地址
     * @param file 要上传的文件
     * @return 响应字符串
     */
    public static String postFile(String url, File file) {
        MediaType type = MediaType.parse("application/octet-stream");
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(type, file))
                .build();
        return call(request);
    }

    /**
     * MultipartBody 请求多种类型的数据
     *
     * @param url    请求地址
     * @param params 请求的参数
     * @param file   上传的文件
     * @return 响应字符串
     */
    public static String postMultipart(String url, Map<String, Object> params, File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (ObjectUtils.notNull(params)) {
            params.forEach((key, value) -> builder.addFormDataPart(key, String.valueOf(value)));
        }
        if (ObjectUtils.notNull(file)) {
            builder.addFormDataPart("file", file.getName(),
                    RequestBody.create(MediaType.parse("application/octet-stream"), file));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        return call(request);
    }

    /**
     * 文件下载
     * @param url 文件下载地址
     * @return 文件流
     */
    public static InputStream downloadFile(String url) {
        try {
            Request request = new Request.Builder()
                    .get().url(url).build();
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response.code() == HttpStatus.OK.value()) {
                assert response.body() != null;
                return response.body().byteStream();
            } else {
                log.warn("下载文件失败：{}", response.message());
            }
        } catch (Exception e) {
            log.error("OkHttp下载错误", e);
        }
        return null;
    }

    /**
     * 通用请求
     */
    public static String call(Request request) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();
            final Call call = client.newCall(request);
            Response response = call.execute();
            if (response.code() == HttpStatus.OK.value()) {
                ResponseBody body = response.body();
                if (ObjectUtils.notNull(body)) {
                    return body.string();
                }
            } else {
                log.warn("服务器请求失败:{}", response.toString());
            }
        } catch (Exception e) {
            log.error("OkHttp请求错误", e);
        }
        return null;
    }
}
