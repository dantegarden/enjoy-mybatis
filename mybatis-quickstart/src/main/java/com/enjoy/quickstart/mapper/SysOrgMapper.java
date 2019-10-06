package com.enjoy.quickstart.mapper;

import com.enjoy.quickstart.model.entity.SysOrg;
import com.enjoy.quickstart.model.entity.SysOrgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysOrgMapper {
    long countByExample(SysOrgExample example);

    int deleteByExample(SysOrgExample example);

    int insert(SysOrg record);

    int insertSelective(SysOrg record);

    List<SysOrg> selectByExample(SysOrgExample example);

    int updateByExampleSelective(@Param("record") SysOrg record, @Param("example") SysOrgExample example);

    int updateByExample(@Param("record") SysOrg record, @Param("example") SysOrgExample example);
}