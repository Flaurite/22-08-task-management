package com.company.taskmanagement.listener;

import com.company.taskmanagement.entity.SubTask;
import com.company.taskmanagement.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.core.event.EntitySavingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("tm_SubTaskEventListener")
public class SubTaskEventListener {

    private DataManager dataManager;

    public SubTaskEventListener(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @EventListener
    public void onSubTaskSaving(EntityChangedEvent<SubTask> event) {
        Task task;
        if (event.getType() == EntityChangedEvent.Type.DELETED) {
            Id<Task> taskId = event.getChanges().getOldReferenceId("task");
            task = dataManager.load(taskId).one();
        } else {
            SubTask subTask = dataManager.load(event.getEntityId()).one();
            task = subTask.getTask();
        }

        Integer totalEstimation = task.getSubTasks().stream()
                .map(SubTask::getEstimation)
                .filter(Objects::nonNull)
                .reduce(0, Integer::sum);

        task.setTotalEstimation(totalEstimation);
        dataManager.save(task);
    }
}