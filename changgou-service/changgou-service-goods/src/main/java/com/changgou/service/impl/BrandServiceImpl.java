package com.changgou.service.impl;

import com.changgou.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    //分页查询
    public PageInfo<Brand> findPage(int page,int size) {
        PageHelper.startPage(page,size);
        return new PageInfo<Brand>(brandMapper.selectAll());
    }

    /**
     * 分页查询 + 搜索条件
     * @param brand
     * @param page  当前页
     * @param size   当前页显示的数目
     * @return
     */
    @Override
    public PageInfo<Brand> findPageByCondition(Brand brand, int page, int size) {

        PageHelper.startPage(page,size);

        Example example = createExample(brand);

        List<Brand> brandList = brandMapper.selectByExample(example);

        return new PageInfo<Brand>(brandList);

    }


    //查找所有
    @Override
    public List<Brand> getAll() {
        List<Brand> brands = brandMapper.selectAll();
        return brands;
    }

    //通过id进行查找
    @Override
    public Brand findById(Integer id) {

        Brand brand = brandMapper.selectByPrimaryKey(id);

        return brand;
    }
    //添加

    @Override
    public void add(Brand brand) {
      //name字段的名字不能一致
        String name = brand.getName();
        List<Brand> brands = brandMapper.selectAll();
        for (Brand brand1 : brands) {
            if(name.equals(brand1.getName())) {
                throw  new RuntimeException("该昵称已被占用,请重新输入");
            }
        }
        brandMapper.insert(brand);
    }

    //通过id进行修改
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    //通过条件进行查询
    @Override
    public List<Brand> selectList(Brand brand) {

        Example example = createExample(brand);

        return brandMapper.selectByExample(example);
    }

    //条件拼装
    public Example createExample(Brand brand) {
        Example example = new Example(Brand.class);

        Example.Criteria criteria = example.createCriteria();

        //拼装名称模糊查询
        if(!StringUtil.isEmpty(brand.getName())) {
            criteria.andLike("name","%"+brand.getName()+"%");
        }
        //根据首字母查询
        if(!StringUtil.isEmpty(brand.getLetter())) {
            criteria.andEqualTo("letter",brand.getLetter());
        }

        return  example;

    }

}
