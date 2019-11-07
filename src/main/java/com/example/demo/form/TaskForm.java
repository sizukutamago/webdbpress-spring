package com.example.demo.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskForm {
    String subject;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate deadLine;

    Boolean hasDone;

    Boolean isNewTask;
}
