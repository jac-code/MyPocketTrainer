package com.example.demo.model;

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

    @ManyToOne
    @JoinColumn(name = "diet_id")
    private Diet diet;

    @ManyToOne
    @JoinColumn(name = "routine_id")
    private Routine routine;
}