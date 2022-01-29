package com.example.demo.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Business implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long business_id;

    @Column(name = "business_name", nullable = false)
    private String business_name;

    // inverse side 
    @OneToMany(mappedBy = "business")
    private Set<Professional> professionals;

    public Business() {
        super();
    }

    public Business(String business_name) {
        setBusiness_name(business_name);
    }

    public Long getId() {
        return this.business_id;
    }

    public void setId(Long id) {
        this.business_id = id;
    }

    public String getBusiness_name() {
        return this.business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public void addProfessional(Professional professional) {
        this.professionals.add(professional);
    }
}