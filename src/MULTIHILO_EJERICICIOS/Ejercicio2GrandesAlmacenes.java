package MULTIHILO_EJERICICIOS;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Ejercicio2GrandesAlmacenes {

    static Semaphore puerta = new Semaphore(1);
    //usamos semaphore para controlar la entrada de la puerta
    static int productos = 100;

    static class Cliente implements Runnable {
        private int numero;

        public Cliente(int numero) {
            this.numero = numero;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {

                if (puerta.tryAcquire()) {
                    try {
                        System.out.println(
                                "Cliente " + numero + " entra por la puerta"
                        );

                        synchronized (Ejercicio2GrandesAlmacenes.class) {
                            if (productos > 0) {
                                productos--;
                                System.out.println(
                                        "Cliente " + numero
                                                + " consigue un producto. Quedan: "
                                                + productos
                                );
                            } else {
                                System.out.println(
                                        "Cliente " + numero
                                                + " entra, pero no quedan productos"
                                );
                            }
                        }

                        return;

                    } finally {
                        puerta.release();
                    }
                    //uso syncrhornized para controlar las existencias
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            System.out.println(
                    "Cliente " + numero + " se marcha tras 10 intentos"
            );
        }
    }

    public static void main(String[] args) {
        ExecutorService clientes = Executors.newFixedThreadPool(30);

        for (int i = 1; i <= 300; i++) {
            clientes.execute(new Cliente(i));
        }

        clientes.shutdown();
    }
}

// explicacion: se crean 300 clientes, pero solo puede entrar uno por la
// puerta cada vez. cada cliente intenta entrar un maximo de 10 veces.
// si entra, coge un producto si quedan unidades y termina su ejecucion.
