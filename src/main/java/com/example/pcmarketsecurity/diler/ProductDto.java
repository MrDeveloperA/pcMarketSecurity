package com.example.pcmarketsecurity.diler;

import com.example.pcmarketsecurity.entity.Attachment;
import com.example.pcmarketsecurity.entity.Brand;
import com.example.pcmarketsecurity.entity.Category;
import com.example.pcmarketsecurity.entity.Review;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    @NotNull
    private String name;
    @NotNull
    private Integer attachment;
    @NotNull
    private String description;
    @NotNull
    private Integer price;
    @NotNull
    private Integer brand;
    @NotNull
    private Integer review;
    @NotNull
    private Integer category;
}
