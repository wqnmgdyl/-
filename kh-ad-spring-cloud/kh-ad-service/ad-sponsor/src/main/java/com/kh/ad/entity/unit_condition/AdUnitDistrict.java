package com.kh.ad.entity.unit_condition;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author han.ke
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "ad_unit_district")
public class AdUnitDistrict {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long unitId;

    private String province;

    private String city;

    public AdUnitDistrict(Long unitId, String province, String city) {
        this.unitId = unitId;
        this.province = province;
        this.city = city;
    }
}
