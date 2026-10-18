package MULTIHILO_EJERICICIOS;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ejercicio1Cafeteria {

    static class Pedido implements Runnable {
        private int numero;

        public Pedido(int numero) {
            this.numero = numero;
        }

        @Override
        public void run() {
            System.out.println(
                    Thread.currentThread().getName()
                            + " prepara el pedido " + numero
            );

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Pedido " + numero + " terminado");
        }
    }

    public static void main(String[] args) {
        ExecutorService trabajadores = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++) {
            trabajadores.execute(new Pedido(i));
        }

        trabajadores.shutdown();
    }
}

// explicacion: se crean 10 pedidos y se reparten entre 3 trabajadores.
// cada trabajador prepara los pedidos que tiene disponibles.
// shutdown impide aceptar nuevos pedidos y permite terminar los pendientes.
