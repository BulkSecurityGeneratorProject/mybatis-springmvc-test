package cn.com.sinosoft.platform.ircs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.joda.time.DateTime;

import cn.com.sinosoft.platform.ircs.domain.User;

public interface UserDao {
	//@Select("select * from JHI_USER u where u.activation_key=#{activationKey}")
	User findOneByActivationKey(String activationKey);

	@Select("select * from JHI_USER u where u.activated=0 and created_date < #{dateTime}")
	List<User> findAllByActivatedIsFalseAndCreatedDateBefore(DateTime dateTime);

	//@Select("select * from JHI_USER u where u.login=#{login}")
	//@Select("select u.*, a.`name` from JHI_USER u  left join JHI_USER_AUTHORITY ua on ua.user_id=u.id left join JHI_AUTHORITY a on a.`name`=ua.authority_name where u.login=#{login}")
	//@ResultMap("findOneByLogin")
	User findOneByLogin(String login);

	@Select("select * from JHI_USER u where u.email=#{email}")
	User findOneByEmail(String email);

	void save(User user);
	
	@Delete("delete from JHI_USER where id=#{id}")
	void delete(User user);
}
