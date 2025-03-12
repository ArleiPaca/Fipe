package com.arlei.fipe.principal;

import com.arlei.fipe.model.Dados;
import com.arlei.fipe.model.Modelos;
import com.arlei.fipe.model.Veiculo;
import com.arlei.fipe.service.ConsumoAPI;
import com.arlei.fipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1";
    private final String CARRO = "/carros/marcas";
    private final String CAMINHOES = "/caminhoes/marcas";
    private final String MOTOS = "/motos/marcas";

    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu(){



        var menu = """
                Escolha uma opção:
                1 - Carros
                2 - Caminhões
                3 - Motos
                4 - Sair
                Opção: 
                """;

        System.out.println(menu);

        int opcao = leitura.nextInt();

        switch (opcao){
            case 1:
                exibeMarcas(CARRO);
                break;
            case 2:
                exibeMarcas(CAMINHOES);
                break;
            case 3:
                exibeMarcas(MOTOS);
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida");
                exibeMenu();
        }



    }



    private void exibeMarcas(String tipo) {

        var json = consumo.obterDados(URL_BASE + tipo);

        var marcas = conversor.obterlista(json, Dados.class);

        marcas.stream()
                .sorted (Comparator.comparing (Dados::codigo))
        .forEach(System.out::println);


        System.out.println("Digite o código da marca para ver os modelos: ");
        int codigo = leitura.nextInt();


        json = consumo.obterDados(URL_BASE + tipo + "/" + codigo + "/modelos");

        var modeloLista = conversor.obterDados(json, Modelos.class);

        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Digite uma parte do nome do veiculo para ser buscado:");


        var nomeVeiculo = leitura.next();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.name().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("Modelos filtrados");
        modelosFiltrados.forEach(System.out::println);

       System.out.println("Digite por favor o código do modelo");

        var codigoModelo = leitura.next();

        var endereco = URL_BASE + tipo + "/" + codigo + "/modelos" + "/" + codigoModelo + "/anos";
        System.out.println(endereco);
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterlista(json, Dados.class);
        System.out.println(anos);

        //https://parallelum.com.br/fipe/api/v1/carros/marcas/21/modelos/7097/anos/2015-1

        List<Veiculo> veiculos = new ArrayList<>();



        for ( int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            System.out.println(enderecoAnos);
            json = consumo.obterDados(enderecoAnos);
            System.out.println(json);
            Veiculo veiculo = conversor.obterDados (json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veiculos filtrados com avaliações por ano: "); veiculos.forEach(System.out::println);



    }

}
