package cn.com.sinosoft.platform.ircs.dao;

import org.apache.ibatis.annotations.Select;

import cn.com.sinosoft.platform.ircs.domain.Authority;

public interface AuthorityDao {

	@Select("select * from JHI_USER_AUTHORITY a where a.authority_name=#{authorityName}")
	Authority findOne(String authorityName);
	
	@Select(value="select * from JHI_USER_AUTHORITY a where a.user_id=#{userId}")
	Authority findOneByUserId(Long userId);
}
