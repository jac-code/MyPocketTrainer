package com.example.demo.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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

    @Column(name = "recipe_name")
    private String recipe_name;

    @Column(name = "recipe_description")
    private String recipe_description;

    @Lob    // BLOB = binary data || CLOB = text data
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image")
    private byte[] image;

    // @Column(name = "body_part")
    // private List<String> body_parts;

    // @Column(name = "tools")
    // private List<String> tools;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    // quitamos esto para que sea UNIDIRECCIONAL
    // @ManyToMany(mappedBy = "recipes")
    // Set<Diet> diets;
}