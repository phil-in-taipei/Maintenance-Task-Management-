package MaintenanceManager.MaintenanceManager.models.tasks;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntervalTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String intervalTaskName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean noRainOnly;
}
