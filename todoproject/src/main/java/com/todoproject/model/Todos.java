package com.todoproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Todos {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
   private  String title;
    String description;
    boolean status;
    String createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    Date updateDate;

}
