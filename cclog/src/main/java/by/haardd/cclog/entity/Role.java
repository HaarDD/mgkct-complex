package by.haardd.cclog.entity;

import by.haardd.cclog.entity.enums.RoleNameEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private RoleNameEnum name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "priority_id", nullable = false)
    private Priority priority;

    @OneToMany(mappedBy = "role")
    private Set<User> users = new LinkedHashSet<>();

}