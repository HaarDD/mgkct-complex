package by.haardd.cclog.entity;

import by.haardd.cclog.entity.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "statuses")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private StatusEnum name;

    @Column(name = "readable_name", nullable = false)
    private String readableName;

    @Column(name = "color_hex", nullable = false, length = 6)
    private String colorHex;

    @OneToMany(mappedBy = "status")
    private Set<Request> requests = new LinkedHashSet<>();

}