package com.conninf.desafio_to_do_list.controllers;

import com.conninf.desafio_to_do_list.entity.ToDo;
import com.conninf.desafio_to_do_list.services.ToDoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/tarefas")
public class ToDoController {
    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }
    @GetMapping
    public ResponseEntity listAll(){
        try {
            return ResponseEntity.ok(toDoService.listAll());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("{id}")
    public ResponseEntity find(@PathVariable Long id){
        try {
            return ResponseEntity.ok(toDoService.find(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid ToDo todo) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(toDoService.save(todo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }

    @PatchMapping("/{id}")
    public ToDo update(@PathVariable Long id, @Valid ToDo newToDo){

        ToDo toDoFind = toDoService.find(id);
        toDoFind.setNome(newToDo.getNome());
        toDoFind.setDescricao(newToDo.getDescricao());
        toDoFind.setPrioridade(newToDo.getPrioridade());
        toDoFind.setUltimaModificacao(LocalDateTime.now());
        return toDoService.save(toDoFind);
    }

    @DeleteMapping("{id}")
     public void deletar (@PathVariable Long id){
     toDoService.delete(id);
        }


    @PostMapping("check/{id}")
    public void check(@PathVariable Long id){
        toDoService.check(id);
    }

     @PostMapping("uncheck/{id}")
     public void uncheck(@PathVariable Long id){
         toDoService.uncheck(id);
     }

}
