package com.zsx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Book implements Serializable {

    private Integer id;

    private String title;

    private String name;

    private Author author;

    private List<Author> authors;

    private Integer page;

    private LocalDateTime modified;

    private Boolean deleted;
}
