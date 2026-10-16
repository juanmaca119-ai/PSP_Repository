package PSP_PRACTICA;

import java.io.IOException;
import java.util.Scanner;

public class MiniShell {
    static void main() {

        try (Scanner sc = new Scanner(System.in)) {

            //paso 1: primero hay que leer los programas
            System.out.println("Primer programa: ");
            String programa1 = sc.nextLine();

            System.out.println("Segundo programa: ");
            String programa2 = sc.nextLine();


            //AMPLIACION 1 (LEER LOS PROGRAMAS)
            System.out.println("Tercer programa: ");
            String programa3 = sc.nextLine();


            //paso 2: crear los dos procesos
            ProcessBuilder pb1 = new ProcessBuilder(programa1);
            ProcessBuilder pb2 = new ProcessBuilder(programa2);


            //AMPLIACION 2 (CREAR LOS DOS PROCESOS)
            ProcessBuilder pb3 = new ProcessBuilder(programa3);


            //paso 3: hay que iniciar a la vez los procesos
            Process proceso1 = pb1.start();
            Process proceso2 = pb2.start();

        
            //AMPLIACION 3
            Process proceso3 = pb3.start();


            //paso 4: obtener toda la info de los procesos
            ProcessHandle handle1 = proceso1.toHandle();
            ProcessHandle handle2 = proceso2.toHandle();


            //AMPLIACION 4
            ProcessHandle handle3 = proceso3.toHandle();


            System.out.println("PID 1: " + handle1.pid());
            System.out.println("Activo 1: " + handle2.isAlive());
            System.out.println("PID 2: " + handle2.pid());
            System.out.println("Activo 2: " + handle2.isAlive());


            //AMPLIACION 4.1
            System.out.println("PID 3: " + handle3.pid());
            System.out.println("Activo 3: " + handle3.isAlive());


            //paso 5: aqui esperamos a que terminen los dos códigos
            int codigo1= proceso1.waitFor();
            int codigo2= proceso2.waitFor();


            //AMPLIACION 5
            int codigo3= proceso3.waitFor();


            //paso 6: mostramos los códigos de saida
            System.out.println("Exit code 1: " + codigo1);
            System.out.println("Exit code 2: " + codigo2);


            //AMPLIACION 6
            System.out.println("Exit code 3: " + codigo3);

        } catch (IOException e) {
            System.out.println("No se ha podido iniciar alguno de los procesos");
        } catch (InterruptedException e) {
            System.out.println("La espera fue interrumpida");
        }
    }
}
