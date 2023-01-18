package com.example.application.data.entity;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cuenta")
public class Cuenta extends AbstractEntity{

    @Nonnull
    private String Numero;
}