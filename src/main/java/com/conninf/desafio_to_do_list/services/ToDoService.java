package com.conninf.desafio_to_do_list.services;

import com.conninf.desafio_to_do_list.entity.ToDo;
import com.conninf.desafio_to_do_list.repository.ToDoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    //listar
    public List<ToDo> listAll(){
        Sort sort = Sort.by("realizado").ascending().and(
                    Sort.by("prioridade").descending().and(
                    Sort.by("dataCriacao").ascending()));
        return toDoRepository.findAll(sort);
    }
    //buscarPorId
    public ToDo find(Long id){
        return toDoRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Tarefa não encontrada"));
            }


    public ToDo save(ToDo todo){
        return toDoRepository.save(todo);
    }

    public void delete(Long id){
        ToDo toDoFind = this.find(id);
        toDoRepository.delete(toDoFind);
    }

    public void check(Long id){
        ToDo toDoFind = this.find(id);
        if (toDoFind.isRealizado()){
            return;
        }
        toDoFind.setRealizado(true);
        toDoFind.setDataRealizado(LocalDate.now());
        toDoFind.setUltimaModificacao(LocalDate.now());
        toDoRepository.save(toDoFind);
    }

    public void uncheck(Long id){
        ToDo toDoFind = this.find(id);
        if (!toDoFind.isRealizado()){
            return;
        }
        toDoFind.setRealizado(false);
        toDoFind.setDataRealizado(null);
        toDoFind.setUltimaModificacao(LocalDate.now());
        toDoRepository.save(toDoFind);
    }

}
