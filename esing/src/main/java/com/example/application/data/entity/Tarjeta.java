package com.example.application.data.entity;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tarjeta")
public class Tarjeta extends AbstractEntity{

    @Nonnull
    private String Numero;
}