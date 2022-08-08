package com.company.taskmanagement.screen.taskprioritychart;

import com.company.taskmanagement.entity.TaskPriority;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.core.ValueLoadContext;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@UiController("tm_TaskPriorityChartScreen")
@UiDescriptor("task-priority-chart-screen.xml")
public class TaskPriorityChartScreen extends Screen {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private Messages messages;

    @Install(to = "tasksDl", target = Target.DATA_LOADER)
    private List<KeyValueEntity> tasksDlLoadDelegate(ValueLoadContext valueLoadContext) {
        return dataManager.loadValues(valueLoadContext)
                .stream().peek(entity -> {
                    TaskPriority priority = TaskPriority.fromId(entity.getValue("priority"));
                        entity.setValue("priority", priority != null
                                ? messages.getMessage(priority)
                                : "No priority");
                }).collect(Collectors.toList());
    }
}