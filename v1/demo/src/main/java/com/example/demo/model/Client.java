package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import java.util.List;

@Entity
@Table
@PrimaryKeyJoinColumn(referencedColumnName="user_id")
public class Client extends ModelUser{

    @Column(name = "work_zone", nullable = true)
    private String work_zone;

    @ManyToMany(mappedBy = "clients")
    private List<Professional> professionals;

    @Column(name = "meal_url", nullable = true)
    private String meal_url;

    public Client() {

    }

    public String getMeal_url() {
        return this.meal_url;
    }

    public void setMeal_url(String meal_url) {
        this.meal_url = meal_url;
    }

    public String getWork_zone() {
        return this.work_zone;
    }

    public void setWork_zone(String work_zone) {
        this.work_zone = work_zone;
    }

    public void addProfessional(Professional p) {
        this.professionals.add(p);
    }
}