package com.example.demo.model;

import java.util.Base64;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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

    @Column(name = "recipe_type")
    private String recipe_type;

    @Lob    // BLOB = binary data || CLOB = text data
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image")
    private byte[] image;

    @Transient
    private String imageBase64;

    @Column(name = "equipment")
    @ElementCollection
    private List<String> equipment;

    @Column(name = "ingredients")
    @ElementCollection
    private List<String> ingredients;

    @Column(name = "cooking_time")
    private String cooking_time;

    @Column(name = "price")
    private String price;
    
    @Column(name = "calories")
    private String calories;

    @Column(name = "protein")
    private String protein;

    @Column(name = "fat")
    private String fat;

    @Column(name = "carbs")
    private String carbs;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    // quitamos esto para que sea UNIDIRECCIONAL
    // @ManyToMany(mappedBy = "recipes")
    // Set<Diet> diets;

    public void setImageBase64(byte[] image) {
        this.imageBase64 = Base64.getEncoder().encodeToString(image);
    }
}