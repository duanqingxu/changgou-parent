package com.changgou.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {
    List<Brand> getAll();

    Brand findById(Integer id);

    void add(Brand brand);

    void update(Brand brand);

    void delete(Integer id);

    List<Brand> selectList(Brand brand);

    PageInfo<Brand> findPage(int page,int size);

    PageInfo<Brand> findPageByCondition(Brand brand ,int page ,int size);
}
