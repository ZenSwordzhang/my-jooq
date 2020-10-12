package com.zsx.model;

import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseModel implements Serializable {

    public Integer id;

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
