package ivancecchi.reservations.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "workspaces")
@Getter
@Setter
public class Workspace implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(unique = true)
    private String description;
    @Enumerated(EnumType.STRING)
    private WorkspaceType type;
    @ManyToOne
    private Building building;
    private int maxOccupants;


    public Workspace(String description, WorkspaceType type, Building building, int maxOccupants) {
        this.description = description;
        this.type = type;
        this.building = building;
        this.maxOccupants = maxOccupants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workspace workspace)) return false;
        return maxOccupants == workspace.maxOccupants && Objects.equals(id, workspace.id) && Objects.equals(description, workspace.description) && type == workspace.type && Objects.equals(building, workspace.building);
    }

    @Override
    public String toString() {
        return "Workspace{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", building=" + building +
                ", maxOccupants=" + maxOccupants +
                '}';
    }
}
