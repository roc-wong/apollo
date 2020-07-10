package com.ctrip.framework.apollo.portal.repository;

import com.ctrip.framework.apollo.portal.entity.po.Authorities;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author roc
 * @since 2020/6/11 13:22
 */
public interface AuthoritiesRepository extends PagingAndSortingRepository<Authorities, Long> {

    List<Authorities> findByUsername(String username);
}
