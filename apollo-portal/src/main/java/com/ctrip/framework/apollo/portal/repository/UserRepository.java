package com.ctrip.framework.apollo.portal.repository;

import com.ctrip.framework.apollo.portal.entity.po.UserPO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author lepdou 2017-04-08
 */
public interface UserRepository extends PagingAndSortingRepository<UserPO, Long> {

  List<UserPO> findFirst20ByEnabled(int enabled);

  List<UserPO> findByUsernameLikeAndEnabled(String username, int enabled);

  UserPO findByUsername(String username);

  List<UserPO> findByUsernameIn(List<String> userNames);

  UserPO findByEmail(String email);

  UserPO findByUsernameOrEmail(String username, String email);

  /**
   * 根据用户标识更新用户邮箱
   * @param username 用户标识
   * @param email 用户邮箱
   * @return 操作受影响的行数
   */
  @Modifying
  @Query("update UserPO o set o.email = :email where o.username = :username")
  int updateEmail(@Param("username") String username, @Param("email") String email);
}
