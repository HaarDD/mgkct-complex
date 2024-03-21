package by.haardd.cclog.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "priorities")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "value", nullable = false)
    private Long value;


    @Column(name = "color_hex", nullable = false, length = 6)
    private String colorHex;

    @OneToMany(mappedBy = "priority")
    private Set<Request> requests = new LinkedHashSet<>();

    @OneToMany(mappedBy = "priority")
    private Set<Role> roles = new LinkedHashSet<>();

}