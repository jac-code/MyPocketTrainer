package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
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
    private List<Client> clients = new ArrayList<>();

    // owning side
    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    public Professional() {
        
    }

    public String getWork_zone() {
        return this.work_zone;
    }

    public void setWork_zone(String work_zone) {
        this.work_zone = work_zone;
    }

    public Business getBusiness() {
        return this.business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}