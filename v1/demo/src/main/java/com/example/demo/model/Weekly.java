package com.example.demo.model;

import java.util.List;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "WEEKLY")
@Getter
@Setter
@NoArgsConstructor
public class Weekly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weekly_id")
    private Long weekly_id;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    @ManyToMany
    @JoinTable(
            name = "WEEKLY_DAILY",
            joinColumns = @JoinColumn(name = "weekly_id", referencedColumnName = "weekly_id"), 
            inverseJoinColumns = @JoinColumn(name = "daily_id", referencedColumnName = "daily_id")
    )
    private List<Daily> dailies;

    /*************** DAILY ***************/

    public void linkDaily(Daily daily) {
        this.dailies.add(daily);
    }

    public void removeLinkedDaily(Daily daily) {
        this.dailies.remove(daily);
    }
}