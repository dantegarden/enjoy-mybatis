package com.enjoy.quickstart.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @description:
 * @author: lij
 * @create: 2019-09-29 17:01
 */
@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class TRole {
    private Long id;
    private String roleName;
    private String roleCode;
    private Integer deleteStatus;
}
