package MaintenanceManager.MaintenanceManager.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MaintenanceTaskSubmit {

    private String taskName;
    private String description;
    private String date;
}
