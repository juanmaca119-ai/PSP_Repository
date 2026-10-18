package MULTIHILO_EJERICICIOS;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ejercicio3CuentaBancaria {

    static class Cuenta {
        private int saldo = 100;

        public synchronized void ingresar(int cantidad) {
            saldo += cantidad;
        }

        public synchronized void retirar(int cantidad) {
            saldo -= cantidad;
        }

        public synchronized int getSaldo() {
            return saldo;
        }
    }

    static class Operacion implements Runnable {
        private Cuenta cuenta;
        private int cantidad;
        private boolean ingreso;

        public Operacion(Cuenta cuenta, int cantidad, boolean ingreso) {
            this.cuenta = cuenta;
            this.cantidad = cantidad;
            this.ingreso = ingreso;
        }

        @Override
        public void run() {
            if (ingreso) {
                cuenta.ingresar(cantidad);
            } else {
                cuenta.retirar(cantidad);
            }
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        Cuenta cuenta = new Cuenta();

        ExecutorService procesos = Executors.newFixedThreadPool(12);

        for (int i = 0; i < 40; i++) {
            procesos.execute(new Operacion(cuenta, 100, true));
            procesos.execute(new Operacion(cuenta, 100, false));
        }

        for (int i = 0; i < 20; i++) {
            procesos.execute(new Operacion(cuenta, 50, true));
            procesos.execute(new Operacion(cuenta, 50, false));
        }

        for (int i = 0; i < 60; i++) {
            procesos.execute(new Operacion(cuenta, 20, true));
            procesos.execute(new Operacion(cuenta, 20, false));
        }

        procesos.shutdown();

        while (!procesos.isTerminated()) {
            Thread.sleep(100);
        }

        System.out.println("Saldo final: " + cuenta.getSaldo() + " euros");
    }
}

// explicacion: se realizan todos los ingresos y retiradas indicados.
// los metodos synchronized evitan que varias operaciones modifiquen
// el saldo a la vez. como se ingresa y se retira la misma cantidad,
// el saldo final debe ser de 100 euros.

