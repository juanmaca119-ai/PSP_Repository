package MULTIHILO_EJERICICIOS;


public class Ejercicio4ProductoresConsumidores {

    static class Cola {
        private int[] numeros = new int[5];
        private int cantidad = 0;

        public synchronized void producir(int numero)
                throws InterruptedException {

            while (cantidad == numeros.length) {
                wait();
            }

            numeros[cantidad] = numero;
            cantidad++;

            System.out.println("Producido: " + numero);

            notifyAll();
        }

        public synchronized int consumir()
                throws InterruptedException {

            while (cantidad == 0) {
                wait();
            }

            int numero = numeros[0];

            for (int i = 0; i < cantidad - 1; i++) {
                numeros[i] = numeros[i + 1];
            }

            cantidad--;

            notifyAll();

            return numero;
        }
    }

    static class Productor extends Thread {
        private Cola cola;

        public Productor(Cola cola) {
            this.cola = cola;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 20; i++) {
                    cola.producir(i);
                    Thread.sleep((int) (Math.random() * 500) + 100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Consumidor extends Thread {
        private Cola cola;

        public Consumidor(Cola cola) {
            this.cola = cola;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {
                    int numero = cola.consumir();

                    System.out.println(
                            getName() + " consume: " + numero
                    );

                    Thread.sleep((int) (Math.random() * 500) + 100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Cola cola = new Cola();

        Productor productor = new Productor(cola);
        Consumidor consumidor1 = new Consumidor(cola);
        Consumidor consumidor2 = new Consumidor(cola);

        consumidor1.setName("consumidor 1");
        consumidor2.setName("consumidor 2");

        productor.start();
        consumidor1.start();
        consumidor2.start();

        productor.join();
        consumidor1.join();
        consumidor2.join();

        System.out.println("Todos los numeros han sido procesados");
    }
}

// explicacion: el productor introduce 20 numeros en un array de
// capacidad 5. los dos consumidores retiran los numeros por orden.
// synchronized evita que varios hilos modifiquen la cola a la vez.
// wait hace esperar si la cola esta llena o vacia y notifyAll avisa
// a los hilos que estan esperando cuando cambia la cola.
