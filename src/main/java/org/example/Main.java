package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaVendaPastel sistema = new SistemaVendaPastel();
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Realizar pedido");
            System.out.println("3 - Imprimir pedidos");
            System.out.println("4 - Imprimir pedidos encerrados");
            System.out.println("5 - Imprimir pedidos em atendimento");
            System.out.println("6 - Encerrar pedido");
            System.out.println("7 - Editar cliente");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    sistema.cadastrarCliente();
                    break;
                case 2:
                    sistema.realizarPedido();
                    break;
                case 3:
                    sistema.imprimirPedidos();
                    break;
                case 4:
                    sistema.imprimirPedidosEncerrados();
                    break;
                case 5:
                    sistema.imprimirPedidosEmAtendimento();
                    break;
                case 6:
                    sistema.encerrarPedido();
                    break;

                case 7:
                    sistema.editarCliente();
                    break;
                case 0:
                    System.out.println("encerrando o programa...");
                    return;
                default:
                    System.out.println("opçao invalida.");
                    break;
            }
        }
    }
}
