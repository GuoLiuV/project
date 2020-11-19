package com.glv.music.system.utils;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 空间计算工具
 *
 * @author ZHOUXIANG
 */
@Slf4j
public class GeometryUtils {

    /**
     * 将字符串转成坐标List， 并计算中心点
     * JSONArray 形式 [[lng,lat],[lng,lat],...]
     */
    public static String[] getCentroid(String text) {
        JSONArray jsonArray = JSONArray.parseArray(text);
        List<double[]> coords = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONArray point = jsonArray.getJSONArray(i);
                if (point.size() == 2) {
                    coords.add(new double[]{
                            point.getDoubleValue(0),
                            point.getDoubleValue(1)});
                }
            }
        }
        return getCentroid(coords);
    }

    /**
     * 多边形中心点计算方法
     */
    public static String[] getCentroid(List<double[]> coordinateList) {
        int total = coordinateList.size();
        double X = 0;
        double Y = 0;
        double Z = 0;
        for (double[] coordinate : coordinateList) {
            double lat = coordinate[1] * Math.PI / 180;
            double lon = coordinate[0] * Math.PI / 180;
            X += Math.cos(lat) * Math.cos(lon);
            Y += Math.cos(lat) * Math.sin(lon);
            Z += Math.sin(lat);
        }
        X = X / total;
        Y = Y / total;
        Z = Z / total;
        double lon2 = Math.atan2(Y, X);
        double hyp = Math.sqrt(X * X + Y * Y);
        double lat2 = Math.atan2(Z, hyp);
        double longitude = lon2 * 180 / Math.PI;
        double latitude = lat2 * 180 / Math.PI;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMinimumFractionDigits(6);
        return new String[]{
                nf.format(longitude),
                nf.format(latitude)
        };
    }
}
