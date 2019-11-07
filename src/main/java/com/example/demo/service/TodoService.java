package com.example.demo.service;

import com.example.demo.domain.Task;
import com.example.demo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    @Transactional(readOnly = true)
    public List<Task> findAllTasks() {
        List<Task> allTasks = todoRepository.findAll();
        allTasks.sort(Comparator.comparing(Task::getDeadLine));
        return allTasks;
    }

    @Transactional(readOnly = false)
    public Task createTask(Task task) {
        return todoRepository.save(task);
    }

    @Transactional(readOnly = true)
    public Optional<Task> findOneTask(Integer id) {
        return todoRepository.findById(id);
    }

    @Transactional(readOnly = false)
    public Task updateTask(Task task) {
        return todoRepository.save(task);
    }

    @Transactional(readOnly = false)
    public void deleteTask(Integer id) {
        todoRepository.deleteById(id);
    }
}
