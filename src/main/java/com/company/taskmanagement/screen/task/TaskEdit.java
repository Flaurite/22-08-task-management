package com.company.taskmanagement.screen.task;

import com.company.taskmanagement.entity.Project;
import com.company.taskmanagement.entity.TaskPriority;
import com.company.taskmanagement.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import com.company.taskmanagement.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("tm_Task.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
public class TaskEdit extends StandardEditor<Task> {

    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Autowired
    private ComboBox<TaskPriority> priorityField;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Task> event) {
        User user = (User) currentAuthentication.getUser();
        event.getEntity().setAssignee(user);
    }

    @Subscribe(id = "taskDc", target = Target.DATA_CONTAINER)
    public void onTaskDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<Task> event) {
        if (!"project".equals(event.getProperty())) {
            return;
        }

        Project project = (Project) event.getValue();
        if (project == null) {
            priorityField.setValue(null);
        } else {
            priorityField.setValue(project.getDefaultTaskPriority());
        }
    }

//    @Subscribe("projectField")
//    public void onProjectFieldValueChange(HasValue.ValueChangeEvent<Project> event) {
//        Project project = event.getValue();
//        if (project == null) {
//            priorityField.setValue(null);
//        } else {
//            TaskPriority defaultTaskPriority = project.getDefaultTaskPriority();
//            priorityField.setValue(defaultTaskPriority);
//        }
//    }
}