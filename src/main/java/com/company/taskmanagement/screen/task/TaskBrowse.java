package com.company.taskmanagement.screen.task;

import com.company.taskmanagement.app.TaskImportService;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.taskmanagement.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("tm_Task.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
public class TaskBrowse extends StandardLookup<Task> {

    @Autowired
    private TaskImportService taskImportService;

    @Autowired
    private Notifications notifications;

    @Autowired
    private CollectionLoader<Task> tasksDl;

    @Subscribe("taskImportBtn")
    public void onTaskImportBtnClick(Button.ClickEvent event) {
        int taskCount = taskImportService.importTasks();

        tasksDl.load();

        showImportedTaskCount(taskCount);
    }

    private void showImportedTaskCount(int taskCount) {
        notifications.create()
                .withPosition(Notifications.Position.TOP_RIGHT)
                .withCaption("Imported the following task count: " + taskCount)
                .show();
    }
}