package com.changgou.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/brand")
public class BrandController {


    @Autowired
    private BrandService brandService;


    //分页查询+搜索条件
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPageByCondition(@RequestBody Brand brand,@PathVariable("page") int page,@PathVariable("size") int size) {
        PageInfo<Brand> pageByCondition = brandService.findPageByCondition(brand, page, size);
        return new Result<>(true,StatusCode.OK,"分页查询检索成功",pageByCondition);
    }

    //分页查询
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@PathVariable("page") int page,@PathVariable("size") int size) {
        PageInfo<Brand> brandPageInfo = brandService.findPage(page, size);
        return new Result<PageInfo<Brand>>(true,StatusCode.OK,"分页查询成功",brandPageInfo);
    }


    @PostMapping("/selectList")
    public Result<List<Brand>> selectList(@RequestBody Brand brand) {
        List<Brand> brandList = brandService.selectList(brand);
        return new Result<List<Brand>>(true,StatusCode.OK,"条件查询成功",brandList);
    }


    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        brandService.delete(id);
        return new Result<Brand>(true,StatusCode.ERROR,"删除成功");
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id")Integer id,@RequestBody Brand brand) {
        brand.setId(id);
        brandService.update(brand);
        return new Result<Brand>(true,StatusCode.ERROR,"修改成功");
    }




    @RequestMapping
    public Result<Brand> getAll() {
       List<Brand> brandList = brandService.getAll();
       if(brandList != null) {
           return new Result<>(true, StatusCode.OK,"查询成功",brandList);
       }else {
           return new Result<Brand>(false,StatusCode.ERROR,"查询失败");
       }
    }

    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable(value = "id") Integer id) {
        Brand brand = brandService.findById(id);
        if(brand == null) {
            return new Result<Brand>(false,StatusCode.NOTFOUNDERROR,"没有这条数据");
        }else {
            return new Result<Brand>(true,StatusCode.OK,"查找成功",brand);
        }
    }

    @PostMapping
    public Result add(@RequestBody Brand brand) {
        brandService.add(brand);
        return new Result(true,StatusCode.OK,"添加成功");
    }



}
