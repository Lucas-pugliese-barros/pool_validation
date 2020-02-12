package com.pugliese.annotation.validation.models;

import com.pugliese.annotation.validation.fieldValidation.annotations.EqualsTo;

public class MyModel {

    @EqualsTo(value = "610")
    private Integer evento;

    @EqualsTo(value = "codigo-orgao")
    private String codigoOrgao;

    public MyModel() {
    }

    public MyModel(Integer evento, String codigoOrgao) {
        this.evento = evento;
        this.codigoOrgao = codigoOrgao;
    }

    public Integer getEvento() {
        return evento;
    }

    public void setEvento(Integer evento) {
        this.evento = evento;
    }

    public String getCodigoOrgao() {
        return codigoOrgao;
    }

    public void setCodigoOrgao(String codigoOrgao) {
        this.codigoOrgao = codigoOrgao;
    }

    @Override
    public String toString() {
        return "MyModel [codigoOrgao=" + codigoOrgao + ", evento=" + evento + "]";
    }
}