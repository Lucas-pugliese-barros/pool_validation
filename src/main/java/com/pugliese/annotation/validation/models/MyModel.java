package com.pugliese.annotation.validation.models;

import com.pugliese.annotation.validation.fieldValidation.annotations.EqualsTo;

public class MyModel {

    @EqualsTo(value = "610")
    public Integer evento;

    @EqualsTo(value = "codigo-orgao")
    public String codigoOrgao;

    public MyModel() {
    }

    public MyModel(Integer evento, String codigoOrgao) {
        this.evento = evento;
        this.codigoOrgao = codigoOrgao;
    }

    @Override
    public String toString() {
        return "MyModel [codigoOrgao=" + codigoOrgao + ", evento=" + evento + "]";
    }
}