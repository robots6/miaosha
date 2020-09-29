package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.ItemCountDO;
import com.miaoshaproject.dataobject.ItemDO;

import java.util.List;

public interface ItemDOMapper {

    List<ItemDO> listItem();

    int deleteByPrimaryKey(Integer id);

    int insert(ItemDO record);

    int insertSelective(ItemDO record);

    ItemDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemDO record);

    int updateByPrimaryKey(ItemDO record);

//    List<ItemCountDO> countByTitle();
}