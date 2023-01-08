package com.example.application.data.entity;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "cuenta")
public class Cuenta extends AbstractEntity{

    @Nonnull
    private String Numero;
}
