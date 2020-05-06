package com.example.demo.pojo;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.pojo
 * @ClassName: FY_pojo
 * @Author: sxtc
 * @Description: FY_pojo
 * @Date: 2020/4/26 16:48
 * @LatestUpdate:
 * @Version: 1.0
 */

import io.swagger.models.auth.In;

/**
 * 分页查询的pojo类
 */
public class FY_pojo {
    private Integer pageNum;
    private Integer pageSize;
    private Integer count;
    private Object data;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
