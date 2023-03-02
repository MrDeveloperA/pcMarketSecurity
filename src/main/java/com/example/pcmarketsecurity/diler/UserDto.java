package com.example.pcmarketsecurity.diler;

import com.example.pcmarketsecurity.entity.Basket;
import com.example.pcmarketsecurity.entity.Currency;
import com.example.pcmarketsecurity.entity.TypePayment;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.print.attribute.IntegerSyntax;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String email;
    @NotNull
    private Integer basket;
    @NotNull
    private Integer currency;
    @NotNull
    private Integer typePayment;
}
