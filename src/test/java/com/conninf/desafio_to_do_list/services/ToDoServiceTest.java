package com.conninf.desafio_to_do_list.services;

import com.conninf.desafio_to_do_list.entity.ToDo;
import com.conninf.desafio_to_do_list.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {
    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoService toDoService;

    private ToDo mockToDo;
    private ToDo mockToDo2;

    @BeforeEach
    void setUp() {
        mockToDo = new ToDo();
        mockToDo.setId(1L);
        mockToDo.setNome("Estudar Testes Unitários");
        mockToDo.setDescricao("Aprender sobre JUnit e Mockito");
        mockToDo.setPrioridade(1);
        mockToDo.setRealizado(false);

        mockToDo2 = new ToDo();
        mockToDo2.setId(2L);
        mockToDo2.setNome("Fazer Compras");
        mockToDo2.setDescricao("Ir ao mercado e comprar pão");
        mockToDo2.setPrioridade(2);
        mockToDo2.setRealizado(true);
    }

    //listAll()
    @Test
    void deveListarTodasAsTarefasOrdenadas() {
        List<ToDo> mockList = Arrays.asList(mockToDo, mockToDo2);
        when(toDoRepository.findAll(any(Sort.class))).thenReturn(mockList);

        List<ToDo> result = toDoService.listAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Estudar Testes Unitários", result.get(0).getNome());
        verify(toDoRepository).findAll(any(Sort.class));
    }

    //find(Long id)
    @Test
    void deveRetornarUmaTarefaQuandoExistir() {
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(mockToDo));

        ToDo foundToDo = toDoService.find(1L);
        assertNotNull(foundToDo);
        assertEquals("Estudar Testes Unitários", foundToDo.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarTarefa() {
        when(toDoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> toDoService.find(99L));
    }

    //create(ToDo todo)
    @Test
    void deveCriarETornarUmaNovaTarefa() {
        when(toDoRepository.save(mockToDo)).thenReturn(mockToDo);

        ToDo createdToDo = toDoService.create(mockToDo);
        assertNotNull(createdToDo.getId());
        assertEquals("Estudar Testes Unitários", createdToDo.getNome());
    }

    // update(Long id, ToDo toDoModify)
    @Test
    void deveAtualizarUmaTarefaExistente() {
        ToDo updatedToDo = new ToDo();
        updatedToDo.setNome("Tarefa Atualizada");
        updatedToDo.setDescricao("Descrição atualizada");
        updatedToDo.setPrioridade(3);

        when(toDoRepository.findById(1L)).thenReturn(Optional.of(mockToDo));
        when(toDoRepository.save(any(ToDo.class))).thenReturn(updatedToDo);

        ToDo result = toDoService.update(1L, updatedToDo);
        assertNotNull(result);
        assertEquals("Tarefa Atualizada", result.getNome());
        assertEquals(3, result.getPrioridade());
    }

    // delete(Long id)
    @Test
    void deveDeletarUmaTarefaExistente() {
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(mockToDo));

        toDoService.delete(1L);
        verify(toDoRepository).delete(mockToDo);
    }

    // check(Long id)
    @Test
    void deveAlternarOStatusParaConcluido() {
        mockToDo.setRealizado(false);
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(mockToDo));
        when(toDoRepository.save(any(ToDo.class))).thenReturn(mockToDo);

        ToDo result = toDoService.check(1L);
        assertTrue(result.isRealizado());
        assertNotNull(result.getDataRealizado());
    }

    @Test
    void deveAlternarOStatusParaNaoConcluido() {
        mockToDo2.setRealizado(true);
        when(toDoRepository.findById(2L)).thenReturn(Optional.of(mockToDo2));
        when(toDoRepository.save(any(ToDo.class))).thenReturn(mockToDo2);


        ToDo result = toDoService.check(2L);
        assertFalse(result.isRealizado());
        assertNull(result.getDataRealizado());
    }
}