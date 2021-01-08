package com.kh.ad.search.vo.feature;

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
public class DistrictFeature {

    private List<ProvinceAndCity> districts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ProvinceAndCity {
        private String province;
        private String city;
    }
}
