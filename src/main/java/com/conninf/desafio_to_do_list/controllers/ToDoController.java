package com.conninf.desafio_to_do_list.controllers;

import com.conninf.desafio_to_do_list.entity.todo.ToDo;
import com.conninf.desafio_to_do_list.services.ToDoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/tarefas")
public class ToDoController {
    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public ResponseEntity<?> listAll(){
      return ResponseEntity.ok(toDoService.listAll());
      }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id){
        try {
            return ResponseEntity.ok(toDoService.find(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ToDo todo) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(toDoService.create(todo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ToDo toDoModify){
        try{
            return  ResponseEntity.status(HttpStatus.OK).body(toDoService.update(id,toDoModify));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
     public ResponseEntity<?> delete (@PathVariable Long id){
        try {
            toDoService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
     }

    @PostMapping("check/{id}")
    public ResponseEntity<?> check(@PathVariable Long id){
        try {
           return ResponseEntity.status(HttpStatus.OK).body(toDoService.check(id));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
