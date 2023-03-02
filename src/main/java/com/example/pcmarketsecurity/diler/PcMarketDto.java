package com.example.pcmarketsecurity.diler;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PcMarketDto {
    @NotNull
    private String name;
    @NotNull
    private Integer product;
    @NotNull
    private Integer review;
    @NotNull
    private Integer partner;
    @NotNull
    private Integer brand;
}
