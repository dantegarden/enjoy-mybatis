package com.enjoy.quickstart.model.querybean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: lij
 * @create: 2019-09-27 14:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserUsernameDeleteStatusQueryBean {
    private String username;
    private Integer deleteStatus;
}
