package com.example.pcmarketsecurity.diler;

import com.example.pcmarketsecurity.entity.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDto {
    private Integer star;
    @NotNull
    private Integer user;
    private String message;
}
