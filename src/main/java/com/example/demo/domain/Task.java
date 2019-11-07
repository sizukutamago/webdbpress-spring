package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "task")
@Data
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "subject")
    String subject;

    @Column(name = "deadLine")
    @JsonProperty("dead-line")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate deadLine;

    @Column(name = "hasDone")
    @JsonProperty("done")
    Boolean hasDone;

    public Task() {
    }

    public Task(String subject, LocalDate deadLine, Boolean hasDone) {
        this.subject = subject;
        this.deadLine = deadLine;
        this.hasDone = hasDone;
    }
}
