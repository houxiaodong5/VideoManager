package com.example.demo.entity;

import io.swagger.models.auth.In;

/**
 * @ProjectName: VideoManager
 * @Package: com.example.demo.entity
 * @ClassName: Result
 * @Author: sxtc
 * @Description: Result
 * @Date: 2020/4/26 13:39
 * @LatestUpdate:
 * @Version: 1.0
 */
public class Result {
    private String message;
    private Integer code;
    private Object data;
    private Integer currentPage;
    private Integer pageSize;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
