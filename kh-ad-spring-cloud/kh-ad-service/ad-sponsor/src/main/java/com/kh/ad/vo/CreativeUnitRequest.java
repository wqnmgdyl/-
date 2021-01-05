package com.kh.ad.vo;

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
public class CreativeUnitRequest {

    private List<CreativeUnitItem> unitItems;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreativeUnitItem {
        private Long creativeId;
        private Long unitId;
    }
}
