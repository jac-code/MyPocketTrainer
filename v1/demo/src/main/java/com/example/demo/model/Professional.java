package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.*;

@Entity
@Table(name = "PROFESSIONALS")
@Getter
@Setter
@PrimaryKeyJoinColumn(referencedColumnName="user_id")
public class Professional extends ModelUser{
    @Column(name = "work_zone", nullable = true)
    private String work_zone;

    @Column(name="rating", nullable = true)
    private int rating;

    @ManyToMany
    @JoinTable( 
        name = "ProfessionalClients", 
        joinColumns = @JoinColumn(name = "client_id"), 
        inverseJoinColumns = @JoinColumn(name = "professional_id")
    )
    private Set<Client> clients;

    // owning side
    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    // @ManyToMany
    // @JoinTable(
    // name = "PROFESSIONAL_CREATED_ROUTINES",
    // joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
    // inverseJoinColumns = @JoinColumn(name = "routine_id", referencedColumnName = "routine_id"))
    // Set<Routine> routines;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Routine> routines;

    // @ManyToMany
    // @JoinTable(
    // name = "PROFESSIONAL_CREATED_DIETS",
    // joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
    // inverseJoinColumns = @JoinColumn(name = "diet_id", referencedColumnName = "diet_id"))
    // Set<Diet> diets;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Diet> diets;

    public Professional() {
        
    }
}