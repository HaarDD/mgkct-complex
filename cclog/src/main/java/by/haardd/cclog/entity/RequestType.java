package by.haardd.cclog.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "request_types")
public class RequestType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Size(max = 50)
    @NotNull
    @Column(name = "readable_name", nullable = false, length = 50)
    private String readableName;

    @Type(type = "text")
    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "request_types_fields_types",
            joinColumns = @JoinColumn(name = "request_type_id"),
            inverseJoinColumns = @JoinColumn(name = "field_type_id"))
    private Set<RequestFieldType> requestFieldTypes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "requestType")
    private Set<Request> requests = new LinkedHashSet<>();

}