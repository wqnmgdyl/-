package com.kh.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author han.ke
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    private String username;

    public boolean validate() {
        return !StringUtils.isEmpty(username);
    }
}
