package com.yilami.archie.business.dao;

import com.yilami.archie.business.entity.BaseEntity;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Weihua
 * @since 1.0.0
 */
public interface BaseDao<T extends BaseEntity> extends Mapper<T> {
}
