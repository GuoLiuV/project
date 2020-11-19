package com.glv.music.system.modules.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 由于在使用中经常要继承Jpa相关的两个接口，所以这里框架进行了继承，方便使用
 *
 * @author ZHOUXIANG
 */
@NoRepositoryBean
public interface StriveRepository<T, ID>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
