package com.example.pcmarketsecurity.diler;

import com.example.pcmarketsecurity.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasketDto {
    private Integer product;
    private Integer numberProduct;
}
