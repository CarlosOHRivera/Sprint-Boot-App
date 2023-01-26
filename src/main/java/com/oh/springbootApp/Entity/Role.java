package com.oh.springbootApp.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "role")
public class Role  {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "native")
    @GenericGenerator(name="native",strategy = "native")
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    public Role() {
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
