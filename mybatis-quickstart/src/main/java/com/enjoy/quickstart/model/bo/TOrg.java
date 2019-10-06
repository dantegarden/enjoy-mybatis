package com.enjoy.quickstart.model.bo;

import com.enjoy.quickstart.bean.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TOrg {

    private Long id;
    private String orgName;
    private String orgCode;
    private Integer deleteStatus;
    private Long pid;
}