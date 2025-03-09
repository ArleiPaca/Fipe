package com.arlei.fipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

// Interface para converter dados que recebe uma classe generica e um json e retorna um objeto da classe generica
public interface IConverteDados {

    <T> T  obterDados(String json, Class<T> classe);

    <T> List<T> obterlista(String json, Class<T> classe) throws JsonProcessingException;
}