package com.conninf.desafio_to_do_list.services;

import com.conninf.desafio_to_do_list.entity.ToDo;
import com.conninf.desafio_to_do_list.repository.ToDoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }


    public List<ToDo> listAll(){
        Sort sort = Sort.by("realizado").ascending().and(
                    Sort.by("prioridade").descending().and(
                    Sort.by("dataCriacao").ascending()));
        return toDoRepository.findAll(sort);
    }

    public ToDo find(Long id){
        return toDoRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Tarefa não encontrada"));
            }

      public ToDo create(ToDo toDo){
        return toDoRepository.save(toDo);
    }

    public ToDo update(Long id, ToDo toDoModify){
        ToDo toDoUpdate = this.find(id);
        toDoUpdate.setNome(toDoModify.getNome());
        toDoUpdate.setDescricao(toDoModify.getDescricao());
        toDoUpdate.setPrioridade(toDoModify.getPrioridade());
        toDoUpdate.setUltimaModificacao(LocalDateTime.now());
        return toDoRepository.save(toDoUpdate);
    }

    public void delete(Long id){
        ToDo toDoFind = this.find(id);
        toDoRepository.delete(toDoFind);
    }
    //Inverte o estado da tarefa
    public ToDo check(Long id){
        ToDo toDoFind = this.find(id);
        toDoFind.setRealizado(!toDoFind.isRealizado());
        toDoFind.setDataRealizado(toDoFind.isRealizado() ? LocalDateTime.now() : null);
        toDoFind.setUltimaModificacao(LocalDateTime.now());
       return toDoRepository.save(toDoFind);
    }

   }
