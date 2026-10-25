package MULTIHILO_EJERICICIOS;



public class Ejercicio5Barberos {

    static final int NUM_BARBEROS = 3;
    static final int NUM_SILLAS = 5;
    static final int NUM_CLIENTES = 40;

    static class Peluqueria {
        private int[] sillas = new int[NUM_SILLAS];
        private int cantidad = 0;

        public synchronized boolean sentarCliente(int cliente) {
            if (cantidad == NUM_SILLAS) {
                return false;
            }

            sillas[cantidad] = cliente;
            cantidad++;

            System.out.println("Cliente " + cliente + " espera su turno");

            notifyAll();
            return true;
        }

        public synchronized int atenderCliente()
                throws InterruptedException {

            while (cantidad == 0) {
                wait();
            }

            int cliente = sillas[0];

            for (int i = 0; i < cantidad - 1; i++) {
                sillas[i] = sillas[i + 1];
            }

            cantidad--;

            notifyAll();
            return cliente;
        }
    }

    static class Barbero extends Thread {
        private Peluqueria peluqueria;

        public Barbero(Peluqueria peluqueria) {
            this.peluqueria = peluqueria;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int cliente = peluqueria.atenderCliente();

                    if (cliente == -1) {
                        break;
                    }

                    System.out.println(
                            getName() + " atiende al cliente " + cliente
                    );

                    Thread.sleep((int) (Math.random() * 1000) + 500);

                    System.out.println(
                            getName() + " termina con el cliente " + cliente
                    );
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Peluqueria peluqueria = new Peluqueria();

        Barbero[] barberos = new Barbero[NUM_BARBEROS];

        for (int i = 0; i < NUM_BARBEROS; i++) {
            barberos[i] = new Barbero(peluqueria);
            barberos[i].setName("Barbero " + (i + 1));
            barberos[i].start();
        }

        for (int i = 1; i <= NUM_CLIENTES; i++) {

            boolean sentado = peluqueria.sentarCliente(i);

            if (!sentado) {
                System.out.println(
                        "Cliente " + i + " se marcha porque no hay sillas"
                );
            }

            Thread.sleep((int) (Math.random() * 200) + 100);
        }

        // se espera a que todos los clientes que han conseguido silla
        // terminen de ser atendidos antes de cerrar la peluqueria
        for (int i = 0; i < NUM_BARBEROS; i++) {
            peluqueria.sentarCliente(-1);
        }

        for (int i = 0; i < NUM_BARBEROS; i++) {
            barberos[i].join();
        }

        System.out.println("La peluqueria ha terminado de atender");
    }
}


// explicacion: se crean 3 barberos y 5 sillas para que los clientes
// puedan esperar su turno. el metodo sentarCliente comprueba si hay
// sillas libres y, si las hay, guarda al cliente en el array. el metodo
// atenderCliente utiliza wait() para que el barbero espere cuando no
// hay clientes y no tenga que estar comprobando continuamente si llega
// alguno. cuando un cliente se sienta o un barbero termina de recoger
// un cliente de la cola, notifyAll() avisa a los hilos que estan esperando
// para que puedan comprobar si ya pueden continuar. synchronized evita
// que varios hilos modifiquen las sillas al mismo tiempo y cantidad
// indica cuantos clientes estan esperando. si todas las sillas estan
// ocupadas, el nuevo cliente se marcha. finalmente, join() permite que
// el programa principal espere a que terminen los barberos antes de
// finalizar la ejecucion.

