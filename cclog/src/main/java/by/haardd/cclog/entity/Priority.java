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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull
    @Column(name = "value", nullable = false)
    private Long value;

    @Size(max = 6)
    @NotNull
    @Column(name = "color_hex", nullable = false, length = 6)
    private String colorHex;

    @OneToMany(mappedBy = "priority")
    private Set<Request> requests = new LinkedHashSet<>();

    @OneToMany(mappedBy = "priority")
    private Set<Role> roles = new LinkedHashSet<>();

}