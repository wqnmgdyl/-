package com.kh.ad.vo;

import com.kh.ad.entity.unit_condition.AdUnitDistrict;
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
public class AdUnitDistrictRequest {

    private List<UnitDistrict> unitDistricts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnitDistrict {
        private Long unitId;
        private String province;
        private String city;
    }
}
