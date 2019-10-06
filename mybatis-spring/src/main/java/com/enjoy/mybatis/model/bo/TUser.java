package com.enjoy.mybatis.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: lij
 * @create: 2019-09-26 14:34
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TUser {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private Integer deleteStatus;
    private Date createTime;
    private Date updateTime;
    private Integer sex;

    //一对一
    private TOrg organization;
    //一对多
    private List<TRelation> relations;
    //多对多
    private List<TRole> roles;

    //鉴别器
    private HealthReport healthReport;
}
