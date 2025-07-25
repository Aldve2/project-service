package yps.systems.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yps.systems.ai.model.Project;
import yps.systems.ai.object.ProjectObjective;
import yps.systems.ai.object.ProjectPerson;
import yps.systems.ai.object.ProjectTask;
import yps.systems.ai.object.ProjectTeam;
import yps.systems.ai.repository.IProjectRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
@RestController
@RequestMapping("/projectService")
public class ProjectCommandController {

    private final IProjectRepository projectRepository;

    @Autowired
    public ProjectCommandController(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    ResponseEntity<List<Project>> getAll() {
        return ResponseEntity.ok(projectRepository.findAll());
    }

    @GetMapping("/{elementId}")
    ResponseEntity<Project> getByElementId(@PathVariable String elementId) {
        Optional<Project> optionalProject = projectRepository.findById(elementId);
        return optionalProject.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getTeamElementIdByElementId/{elementId}")
    ResponseEntity<String> getTeamElementIdByElementId(@PathVariable String elementId) {
        String teamElementId = projectRepository.getTeamElementIdByElementId(elementId);
        if (teamElementId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(teamElementId);
    }

    @GetMapping("/getTutorElementIdByElementId/{elementId}")
    ResponseEntity<String> getTutorElementIdByElementId(@PathVariable String elementId) {
        String tutorElementId = projectRepository.getTutorElementIdByElementId(elementId);
        if (tutorElementId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tutorElementId);
    }

    @GetMapping("/getObjectiveElementIdByElementId/{elementId}")
    ResponseEntity<String> getObjectiveElementIdByElementId(@PathVariable String elementId) {
        String objectiveElementId = projectRepository.getObjectiveElementIdByElementId(elementId);
        if (objectiveElementId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(objectiveElementId);
    }

    @GetMapping("/getTaskElementIdByElementId/{elementId}")
    ResponseEntity<String> getTaskElementIdByElementId(@PathVariable String elementId) {
        String taskElementId = projectRepository.getTaskElementIdByElementId(elementId);
        if (taskElementId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskElementId);
    }

    @PostMapping
    ResponseEntity<String> save(@RequestBody Project project) {
        Project createdProject = projectRepository.save(project);
        return new ResponseEntity<>("Project saved with ID: " + createdProject.getElementId(), HttpStatus.CREATED);
    }

    @PostMapping("/setTeam")
    ResponseEntity<Project> setTeamTo(@RequestBody ProjectTeam projectTeam) {
        Project projectSaved = projectRepository.setTeamToProject(projectTeam.projectElementId(), projectTeam.teamElementId());
        if (projectSaved == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projectSaved);
    }

    @PostMapping("/setTutor")
    ResponseEntity<Project> setTutorTo(@RequestBody ProjectPerson projectPerson) {
        Project projectSaved = projectRepository.setTutorToProject(projectPerson.projectElementId(), projectPerson.personElementId());
        if (projectSaved == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projectSaved);
    }

    @PostMapping("/setObjective")
    ResponseEntity<Project> setObjectiveTo(@RequestBody ProjectObjective projectObjective) {
        Project projectSaved = projectRepository.setObjectiveToProject(projectObjective.projectElementId(), projectObjective.objectiveElementId());
        if (projectSaved == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projectSaved);
    }

    @PostMapping("/setTask")
    ResponseEntity<Project> setTaskTo(@RequestBody ProjectTask projectTask) {
        Project projectSaved = projectRepository.setTaskToProject(projectTask.projectElementId(), projectTask.taskElementId());
        if (projectSaved == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projectSaved);
    }

    @PutMapping("/{elementId}")
    ResponseEntity<String> update(@PathVariable String elementId, @RequestBody Project project) {
        Optional<Project> optionalProject = projectRepository.findById(elementId);
        if (optionalProject.isPresent()) {
            project.setElementId(optionalProject.get().getElementId());
            projectRepository.save(project);
            return ResponseEntity.ok("Project updated successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    ResponseEntity<String> delete(@RequestParam String projectElementId) {
        projectRepository.deleteById(projectElementId);
        return ResponseEntity.ok("Project deleted successfully");
    }

    @DeleteMapping("/removeTeam")
    ResponseEntity<String> removeTeamFrom(@RequestBody ProjectTeam projectTeam) {
        projectRepository.removeTeamFromProject(projectTeam.projectElementId(), projectTeam.teamElementId());
        return ResponseEntity.ok("Team removed from project successfully");
    }

    @DeleteMapping("/removeTutor")
    ResponseEntity<String> removeTutorFrom(@RequestBody ProjectPerson projectPerson) {
        projectRepository.removeTutorFromProject(projectPerson.projectElementId(), projectPerson.personElementId());
        return ResponseEntity.ok("Tutor removed from project successfully");
    }

    @DeleteMapping("/removeObjective")
    ResponseEntity<String> removeObjectiveFrom(@RequestBody ProjectObjective projectObjective) {
        projectRepository.removeObjectiveFromProject(projectObjective.projectElementId(), projectObjective.objectiveElementId());
        return ResponseEntity.ok("Objective removed from project successfully");
    }

    @DeleteMapping("/removeTask")
    ResponseEntity<String> removeTaskFrom(@RequestBody ProjectTask projectTask) {
        projectRepository.removeTaskFromProject(projectTask.projectElementId(), projectTask.taskElementId());
        return ResponseEntity.ok("Task removed from project successfully");
    }

}
