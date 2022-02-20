package com.example.demo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = "RECIPES")
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipe_id;

    @Column(name = "recipe_url")
    private String recipe_url;

    // quitamos esto para que sea UNIDIRECCIONAL
    // @ManyToMany(mappedBy = "recipes")
    // Set<Diet> diets;
}