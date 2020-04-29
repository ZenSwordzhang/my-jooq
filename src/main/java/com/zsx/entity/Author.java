package com.zsx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Author implements Serializable {

    private String name;

    private Integer age;

    private Boolean gender;

    private String remarks;
}
