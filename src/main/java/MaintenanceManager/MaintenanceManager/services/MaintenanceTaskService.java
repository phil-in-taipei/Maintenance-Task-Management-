package MaintenanceManager.MaintenanceManager.services;
import MaintenanceManager.MaintenanceManager.models.tasks.MaintenanceTask;
import MaintenanceManager.MaintenanceManager.models.tasks.TaskStatusEnum;
import MaintenanceManager.MaintenanceManager.repositories.tasks.MaintenanceTaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MaintenanceTaskService {
    @Autowired
    MaintenanceTaskRepo maintenanceTaskRepo;

    public List<MaintenanceTask> getAllUserTasks(Long userId) {

        return maintenanceTaskRepo.findAllByUserId(userId);
    }

    public List<MaintenanceTask> getAllUserTasksByDate(Long userId, LocalDate date) {

        return maintenanceTaskRepo.findAllByUserIdAndDate(userId, date);
    }

    public List<MaintenanceTask> getAllUncompletedPastUserTasks(Long userId) {
        return maintenanceTaskRepo.findByStatusIsNotAndDateBefore(
                TaskStatusEnum.COMPLETED, LocalDate.now());
    }

    @Transactional
    public void saveTask(MaintenanceTask task) //MaintenanceTask
            throws IllegalArgumentException {
        maintenanceTaskRepo.save(task);
    }

    @Transactional
    public void confirmTaskCompletion(MaintenanceTask task)
            throws IllegalArgumentException {
                 task.setStatus(TaskStatusEnum.COMPLETED);
        maintenanceTaskRepo.save(task);
    }

    public MaintenanceTask getMaintenanceTask(Long id) {
        return maintenanceTaskRepo.findById(id)
                .orElse(null);
    }
}
