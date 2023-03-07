package MaintenanceManager.MaintenanceManager.controllers;
import MaintenanceManager.MaintenanceManager.models.tasks.MaintenanceTask;
import MaintenanceManager.MaintenanceManager.models.tasks.forms.SearchTasksByDate;
import MaintenanceManager.MaintenanceManager.models.user.UserPrincipal;
import MaintenanceManager.MaintenanceManager.models.weather.DailyForecast;
import MaintenanceManager.MaintenanceManager.services.tasks.MaintenanceTaskService;
import MaintenanceManager.MaintenanceManager.services.weather.WeatherApiService;
import MaintenanceManager.MaintenanceManager.services.weather.WeatherDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    MaintenanceTaskService maintenanceTaskService;

    @Autowired
    WeatherApiService weatherApiService;

    @Autowired
    WeatherDataService weatherDataService;

    @GetMapping("/")
    public String homePage(Model model) {
        List<DailyForecast> weather = weatherApiService.getDailyWeatherForecastData(LocalDate.now().toString());
        model.addAttribute("weather", weather);
        return "index";
    }

    @GetMapping("/landing")
    public String landingPage(Authentication authentication, Model model) {
        // get user info
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        // get all uncompleted past tasks
        List<MaintenanceTask> uncompletedTasks = maintenanceTaskService.getAllUncompletedPastUserTasks(user.getId());
        // get all user's tasks for the current date
        List<MaintenanceTask> maintenanceTasks = maintenanceTaskService.getAllUserTasksByDate(
                user.getId(), LocalDate.now());
        SearchTasksByDate searchTasksByDate = new SearchTasksByDate();
        Integer chanceOfRain = weatherDataService.getRainProbability(LocalDate.now().toString());
        // the chance of rain in landing: to display warning about weather dependent tasks
        List<DailyForecast> weather = weatherApiService.getDailyWeatherForecastData(LocalDate.now().toString());
        model.addAttribute("uncompletedTasks", uncompletedTasks);
        model.addAttribute("dailyTasks", maintenanceTasks);
        model.addAttribute("user", user);
        model.addAttribute("weather", weather);
        model.addAttribute("chanceOfRain", chanceOfRain);
        return "landing";
    }

    // get rid of this route
    @GetMapping("/forecast")
    public String testApiForecast(Authentication authentication, Model model) {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        System.out.println("This is the user: " + user);
        List<DailyForecast> weather = weatherApiService.getDailyWeatherForecastData(LocalDate.now().toString());
        model.addAttribute("user", user);
        model.addAttribute("weather", weather);
        return "test-forecast";
    }
}
