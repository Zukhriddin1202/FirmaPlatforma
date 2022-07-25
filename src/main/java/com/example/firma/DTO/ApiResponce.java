package com.example.firma.DTO;

import lombok.AllArgsConstructor;


import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponce {
    private String xabar;
    private boolean holat;
    private Object object;

    public ApiResponce(String xabar, boolean holat) {
        this.xabar = xabar;
        this.holat = holat;
    }
}
