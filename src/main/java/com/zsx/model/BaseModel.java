package com.zsx.model;

import lombok.Data;
import org.springframework.data.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseModel implements Serializable {

    protected Integer id;

    @CreatedBy
    public String createdBy;

    @CreatedDate
    public LocalDateTime createdDate;

    @LastModifiedBy
    public String updatedBy;

    @LastModifiedDate
    public LocalDateTime updatedDate;

    @Version
    public int ver;
}
