import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class App {

	
    static String nomeArquivoDados;
    static Scanner teclado;
    static Produto[] produtosCadastrados;
    static int quantosProdutos = 0;

    
    static Pilha<Pedido> pilhaPedidos = new Pilha<>();
        
    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    
    static void finalizarPedido(Pedido novoPedido) {
        if (novoPedido != null) {
            pilhaPedidos.empilhar(novoPedido);
            System.out.println("Pedido #" + novoPedido.getIdPedido() + " finalizado e adicionado à pilha.");
        } else {
            System.out.println("Pedido inválido. Nada foi adicionado.");
        }
    }

    
    static void listarProdutosPedidosRecentes(int numPedidos) {
        if (pilhaPedidos.vazia()) {
            System.out.println("Nenhum pedido foi realizado ainda.");
            return;
        }

        try {
            Pilha<Pedido> recentes = pilhaPedidos.subPilha(numPedidos);
            System.out.println("\n--- Produtos dos " + numPedidos + " pedidos mais recentes ---\n");

            while (!recentes.vazia()) {
                Pedido pedido = recentes.desempilhar();
                System.out.println("Pedido #" + pedido.getIdPedido() + " - Data: " + pedido.getDataPedido());

                for (Produto p : pedido.getProdutos()) {
                    if (p != null) {
                        System.out.println(" - " + p.getNome() + ": R$ " + String.format("%.2f", p.valorDeVenda()));
                    }
                }
                System.out.println();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
