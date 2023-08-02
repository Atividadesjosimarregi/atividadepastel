package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class SistemaVendaPastel {
    private List<Cliente> clientes;
    private List<Pedido> pedidos;
    private List<Pastel> pasteisDisponiveis;

    public SistemaVendaPastel() {
        clientes = new ArrayList<>();
        pedidos = new ArrayList<>();
        pasteisDisponiveis = new ArrayList<>();

        pasteisDisponiveis.add(new Pastel("pastel de queijo", 5.0));
        pasteisDisponiveis.add(new Pastel("pastel de carne", 6.0));
        pasteisDisponiveis.add(new Pastel("pastel de frango", 5.5));
    }

    public void mostrarPasteisDisponiveis() {
        System.out.println("Pasteis Disponíveis:");
        for (int i = 0; i < pasteisDisponiveis.size(); i++) {
            System.out.println((i + 1) + " - " + pasteisDisponiveis.get(i).getNome() + " - R$ " + pasteisDisponiveis.get(i).getPreco());
        }
    }

    public ItemPedido criarItemPedido() {
        Scanner scanner = new Scanner(System.in);
        mostrarPasteisDisponiveis();
        System.out.print("Digite o número do pastel desejado: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao < 1 || opcao > pasteisDisponiveis.size()) {
            System.out.println("Opção inválida.");
            return null;
        }

        Pastel pastelSelecionado = pasteisDisponiveis.get(opcao - 1);

        System.out.print("Digite a quantidade desejada: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        if (quantidade <= 0) {
            System.out.println("Quantidade inválida.");
            return null;
        }

        return new ItemPedido(pastelSelecionado, quantidade);
    }

    public void cadastrarCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();
        System.out.println("Digite o endereço do cliente:");
        String endereco = scanner.nextLine();

        Cliente cliente = new Cliente(nome, endereco);
        clientes.add(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public Cliente buscarClientePorNome(String nome) {
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equalsIgnoreCase(nome)) {
                return cliente;
            }
        }
        return null;
    }

    public void realizarPedido() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do cliente que está realizando o pedido:");
        String nomeCliente = scanner.nextLine();
        Cliente cliente = buscarClientePorNome(nomeCliente);

        if (cliente == null) {
            System.out.println("Cliente não encontrado. Cadastre o cliente antes de realizar o pedido.");
            return;
        }

        Pedido pedido = new Pedido(cliente);
        ItemPedido itemPedido;
        do {
            itemPedido = criarItemPedido();
            if (itemPedido != null) {
                pedido.adicionarItem(itemPedido);
                System.out.println("Item adicionado ao pedido.");
                System.out.println();
            }
            System.out.print("Deseja adicionar mais itens ao pedido? (S/N): ");
        } while (scanner.nextLine().equalsIgnoreCase("S"));

        pedidos.add(pedido);
        System.out.println("Pedido realizado com sucesso!");
    }

    public void imprimirPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
        } else {
            for (Pedido pedido : pedidos) {
                System.out.println("Cliente: " + pedido.getCliente().getNome());
                System.out.println("Endereço: " + pedido.getCliente().getEndereco());
                if (pedido.isEncerrado()) {
                    System.out.println("Status: Encerrado");
                } else {
                    System.out.println("Status: Em atendimento");
                }
                System.out.println("Itens do pedido:");
                for (ItemPedido item : pedido.getItens()) {
                    System.out.println(" - " + item.getQuantidade() + "x " + item.getPastel().getNome() + " - R$ " + item.getPastel().getPreco());
                }
                System.out.println("Total do pedido: R$ " + calcularTotalPedido(pedido));
                System.out.println();
            }
        }
    }

    public void imprimirPedidosEncerrados() {
        boolean encontrouPedidosEncerrados = false;
        for (Pedido pedido : pedidos) {
            if (pedido.isEncerrado()) {
                System.out.println("Cliente: " + pedido.getCliente().getNome());
                System.out.println("Endereço: " + pedido.getCliente().getEndereco());
                System.out.println("Itens do pedido:");
                for (ItemPedido item : pedido.getItens()) {
                    System.out.println(" - " + item.getQuantidade() + "x " + item.getPastel().getNome() + " - R$ " + item.getPastel().getPreco());
                }
                System.out.println("Total do pedido: R$ " + calcularTotalPedido(pedido));
                System.out.println();
                encontrouPedidosEncerrados = true;
            }
        }
        if (!encontrouPedidosEncerrados) {
            System.out.println("Nenhum pedido encerrado encontrado.");
        }
    }

    public void imprimirPedidosEmAtendimento() {
        boolean encontrouPedidosEmAtendimento = false;
        for (Pedido pedido : pedidos) {
            if (!pedido.isEncerrado()) {
                System.out.println("Cliente: " + pedido.getCliente().getNome());
                System.out.println("Endereço: " + pedido.getCliente().getEndereco());
                System.out.println("Itens do pedido:");
                for (ItemPedido item : pedido.getItens()) {
                    System.out.println(" - " + item.getQuantidade() + "x " + item.getPastel().getNome() + " - R$ " + item.getPastel().getPreco());
                }
                System.out.println("Total do pedido: R$ " + calcularTotalPedido(pedido));
                System.out.println();
                encontrouPedidosEmAtendimento = true;
            }
        }
        if (!encontrouPedidosEmAtendimento) {
            System.out.println("Nenhum pedido em atendimento encontrado.");
        }
    }

    public void encerrarPedido() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do cliente do pedido que deseja encerrar:");
        String nomeCliente = scanner.nextLine();
        Cliente cliente = buscarClientePorNome(nomeCliente);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        Pedido pedidoEncerrar = null;
        for (Pedido pedido : pedidos) {
            if (!pedido.isEncerrado() && pedido.getCliente().equals(cliente)) {
                pedidoEncerrar = pedido;
                break;
            }
        }

        if (pedidoEncerrar == null) {
            System.out.println("Nenhum pedido em atendimento encontrado para esse cliente.");
            return;
        }

        pedidoEncerrar.encerrarPedido();
        System.out.println("Pedido encerrado com sucesso!");

        salvarPedidoEncerrado(pedidoEncerrar);
    }

    public double calcularTotalPedido(Pedido pedido) {
        double total = 0.0;
        for (ItemPedido item : pedido.getItens()) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public void editarCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do cliente que deseja editar:");
        String nomeCliente = scanner.nextLine();
        Cliente cliente = buscarClientePorNome(nomeCliente);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("Digite o novo nome do cliente:");
        String novoNome = scanner.nextLine();
        System.out.println("Digite o novo endereço do cliente:");
        String novoEndereco = scanner.nextLine();

        cliente.setNome(novoNome);
        cliente.setEndereco(novoEndereco);

        System.out.println("Perfil do cliente editado com sucesso!");
    }

    public void salvarPedidoEncerrado(Pedido pedido) {
        String caminhoPasta = "D:/Java/pastel/pedidos/";
        String nomeArquivo = caminhoPasta + "pedido_" + pedido.getCliente().getNome() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            writer.write("Cliente: " + pedido.getCliente().getNome());
            writer.newLine();
            writer.write("Endereço: " + pedido.getCliente().getEndereco());
            writer.newLine();
            writer.write("Itens do pedido:");
            writer.newLine();
            for (ItemPedido item : pedido.getItens()) {
                writer.write(" - " + item.getQuantidade() + "x " + item.getPastel().getNome() + " - R$ " + item.getPastel().getPreco());
                writer.newLine();
            }
            writer.write("Total do pedido: R$ " + calcularTotalPedido(pedido));
            writer.newLine();
            System.out.println("Arquivo gerado com sucesso: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }


}

