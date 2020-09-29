package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.ItemStockdDO;
import org.apache.ibatis.annotations.Param;

public interface ItemStockdDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockdDO record);

    int insertSelective(ItemStockdDO record);

    ItemStockdDO selectByPrimaryKey(Integer id);

    ItemStockdDO selectByItemId(Integer itemId);

    int updateByPrimaryKeySelective(ItemStockdDO record);

    int updateByPrimaryKey(ItemStockdDO record);

    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}