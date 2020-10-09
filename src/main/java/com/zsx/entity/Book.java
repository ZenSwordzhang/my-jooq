package com.zsx.entity;

import com.zsx.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Book extends BaseModel {

    private String title;

    private String name;

    private Author author;

    private List<Author> authors;

    private Integer page;

    private Boolean deleted;
}
