package com.example.demo.controller;

import com.example.demo.domain.Task;
import com.example.demo.form.TaskForm;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class TodoController {
    private static final String TASKS = "tasks";

    private static final String REDIRECT_TO = "redirect:/" + TASKS;

    @Autowired
    TodoService todoService;

    @GetMapping("/tasks")
    public ModelAndView readAllTasks() {
        TaskForm form = createInitialForm();
        ModelAndView mv = toTasksPage();
        mv.addObject("form", form);
        List<Task> tasks = todoService.findAllTasks();
        mv.addObject(TASKS, tasks);
        return mv;
    }

    private ModelAndView toTasksPage() {
        return new ModelAndView(TASKS);
    }

    @PostMapping("/tasks")
    public ModelAndView createOneTask(@ModelAttribute TaskForm form, ModelAndView mv) {
        createTaskForm(form);
        mv.setViewName(REDIRECT_TO);
        return mv;
    }

    @GetMapping("/tasks/{id}")
    public ModelAndView readOneTask(@PathVariable Integer id) {
        Optional<TaskForm> form = readTaskFormId(id);

        if (!form.isPresent()) {
            return new ModelAndView(REDIRECT_TO);
        }

        ModelAndView mv = toTasksPage();
        mv.addObject("taskId", id);
        mv.addObject("form", form.get());
        List<Task> tasks = todoService.findAllTasks();
        mv.addObject(TASKS, tasks);
        return mv;
    }

    @PutMapping("/tasks/{id}")
    public ModelAndView updateOneTask(
            @PathVariable Integer id,
            @ModelAttribute TaskForm form
    ) {
        updateTask(id, form);
        return new ModelAndView(REDIRECT_TO);
    }

    @DeleteMapping("/tasks/{id}")
    public ModelAndView deleteOneTask(@PathVariable Integer id) {
        deleteTask(id);
        return new ModelAndView(REDIRECT_TO);
    }

    private TaskForm createInitialForm() {
        String formSubject = "";
        LocalDate formDeadLine = LocalDate.now();
        Boolean isNewTask = true;
        Boolean hasDone = false;
        return new TaskForm(
                formSubject,
                formDeadLine,
                hasDone,
                isNewTask
        );
    }

    private void createTaskForm(TaskForm form) {
        String subject = form.getSubject();
        LocalDate deadLine = form.getDeadLine();
        Boolean hasDone = form.getHasDone();
        Task task = new Task(subject, deadLine, hasDone);
        todoService.createTask(task);
    }

    private Optional<TaskForm> readTaskFormId(Integer id) {
        Optional<Task> task = todoService.findOneTask(id);
        if (!task.isPresent()) {
            return Optional.ofNullable(null);
        }

        String formSubject = task.get().getSubject();
        LocalDate formDeadLine = task.get().getDeadLine();
        Boolean hasDone = task.get().getHasDone();
        Boolean isNewTask = false;
        TaskForm form = new TaskForm(
                formSubject,
                formDeadLine,
                hasDone,
                isNewTask
        );
        return Optional.ofNullable(form);
    }

    private void updateTask(Integer id, TaskForm form) {
        String subject = form.getSubject();
        LocalDate deadLine = form.getDeadLine();
        Boolean hasDone = form.getHasDone();
        Task task = new Task(id, subject, deadLine, hasDone);
        todoService.updateTask(task);
    }

    private void deleteTask(Integer id) {
        Optional<Task> task = todoService.findOneTask(id);
        if (task.isPresent()) {
            todoService.deleteTask(id);
        }
    }
}
