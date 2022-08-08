package com.company.taskmanagement.app;

import com.company.taskmanagement.entity.Project;
import com.company.taskmanagement.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.core.EntitySet;
import io.jmix.core.SaveContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("tm_TaskImportService")
public class TaskImportService {
    private static final Logger log = LoggerFactory.getLogger(TaskImportService.class);

    private DataManager dataManager;

    public TaskImportService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public int importTasks() {
        List<String> taskNames = obtainTaskNames();
        Project defaultProject = getDefaultProject();

        List<Task> tasks = taskNames.stream()
                .map(name -> {
                    Task task = dataManager.create(Task.class);
                    task.setName(name);
                    task.setProject(defaultProject);
                    return task;
                })
                .collect(Collectors.toList());

        EntitySet entitySet = dataManager.save(new SaveContext().saving(tasks));

        log.info("Imported the following task count: " + entitySet.size());

        return entitySet.size();
    }

    private List<String> obtainTaskNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            names.add("Task " + RandomStringUtils.randomAlphabetic(5));
        }
        return names;
    }

    @Nullable
    private Project getDefaultProject() {
        return dataManager.load(Project.class)
                .query("select p from tm_Project p where p.defaultProject = :defaultProject")
                .parameter("defaultProject", true)
                .optional()
                .orElse(null);
    }
}