package com.kh.ad.index.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author han.ke
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDistrictObject {
    private Long unitId;
    private String province;
    private String city;
}
