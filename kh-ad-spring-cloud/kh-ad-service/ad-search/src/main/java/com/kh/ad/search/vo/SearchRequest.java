package com.kh.ad.search.vo;

import com.kh.ad.search.vo.feature.DistrictFeature;
import com.kh.ad.search.vo.feature.FeatureRelation;
import com.kh.ad.search.vo.feature.ItFeature;
import com.kh.ad.search.vo.feature.KeywordFeature;
import com.kh.ad.search.vo.media.AdSlot;
import com.kh.ad.search.vo.media.App;
import com.kh.ad.search.vo.media.Device;
import com.kh.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han.ke
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {

    /**
     * 媒体放的请求标识
     */
    private String mediaId;

    /**
     * 请求基本信息
     */
    private RequestInfo requestInfo;

    /**
     * 匹配信息
     */
    private FeatureInfo featureInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestInfo {
        private String requestId;

        private List<AdSlot> adSlots;
        private App app;
        private Geo geo;
        private Device device;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeatureInfo {
        private KeywordFeature keywordFeature;
        private DistrictFeature districtFeature;
        private ItFeature itFeature;

        private FeatureRelation relation = FeatureRelation.AND;
    }
}
