package com.example.demo.model;

import java.util.List;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "DAILY")
@Getter
@Setter
@NoArgsConstructor
public class Daily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_id")
    private Long daily_id;

    @Column(name ="week_day")
    private String week_day;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    @ManyToMany
    @JoinTable(
            name = "DAILY_ROUTINE",
            joinColumns = @JoinColumn(name = "daily_id", referencedColumnName = "daily_id"), 
            inverseJoinColumns = @JoinColumn(name = "routine_id", referencedColumnName = "routine_id")
    )
    private List<Routine> routines;

    @ManyToMany
    @JoinTable(
            name = "DAILY_DIET",
            joinColumns = @JoinColumn(name = "daily_id", referencedColumnName = "daily_id"), 
            inverseJoinColumns = @JoinColumn(name = "diet_id", referencedColumnName = "diet_id")
    )
    private List<Diet> diets;

    /*************** ROUTINES ***************/

    public void addRoutine(Routine routine) {
        this.routines.add(routine);
    }

    public void deleteRoutine(Routine routine) {
        this.routines.remove(routine);
    }

    /*************** DIETS ***************/
    
    public void addDiet(Diet diet) {
        this.diets.add(diet);
    }

    public void deleteDiet(Diet diet) {
        this.diets.remove(diet);
    }
}