package com.enjoy.mybatis.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @description:
 * @author: lij
 * @create: 2019-09-29 17:09
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TRelation {
    private Long id;
    private String relationName;
    private Integer relationAge;
    private Integer deleteStatus;

}
