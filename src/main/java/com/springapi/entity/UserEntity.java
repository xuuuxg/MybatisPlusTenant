package com.springapi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@TableName("user")
@ApiModel
public class UserEntity {

    @ApiModelProperty(notes = "userId", required = false)
    private int id;

    @ApiModelProperty(notes = "name", required = false)
    private String name;

    @TableField("tenant_id")
    @ApiModelProperty(notes = "tenantId", hidden = true, required = false)
    private int tenantId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }
}
