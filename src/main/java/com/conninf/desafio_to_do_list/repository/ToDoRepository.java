package com.conninf.desafio_to_do_list.repository;

import com.conninf.desafio_to_do_list.entity.todo.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo,Long> {
}
