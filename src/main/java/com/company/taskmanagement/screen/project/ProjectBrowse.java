package com.company.taskmanagement.screen.project;

import com.company.taskmanagement.app.TaskImportService;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import com.company.taskmanagement.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("tm_Project.browse")
@UiDescriptor("project-browse.xml")
@LookupComponent("projectsTable")
public class ProjectBrowse extends StandardLookup<Project> {
}