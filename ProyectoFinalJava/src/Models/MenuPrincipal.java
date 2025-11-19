package Models;

import Gestoras.Taller;
import java.util.Scanner;

public class MenuPrincipal {

    private Scanner sc = new Scanner(System.in);
    private Taller taller = new Taller();

    public void mostrarMenu() {

        int opcion = 1;

        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1) Iniciar sesión");
            System.out.println("2) Registrarse");
            System.out.println("3) Salir");
            System.out.println("==========================");
            System.out.print("Elija una opción: ");

            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {

                case 1:
                    // iniciarSesion();
                    break;

                case 2:
                    // registrarse();
                    break;

                case 3:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción inválida. Intente otra vez.");
                    break;
            }

        } while (opcion != 3);
    }


    private void iniciarSesion() {
        System.out.println("\n--- INICIAR SESIÓN ---");

        System.out.print("Usuario: ");
        String user = sc.nextLine();

        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        // Acá llamás al taller
        boolean ok = taller.iniciarSesion(user, pass);

        if (ok) {
            System.out.println("Inicio de sesión exitoso.");
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    private void registrarse() {
        System.out.println("\n--- REGISTRARSE ---");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Usuario: ");
        String user = sc.nextLine();

        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        // Acá llamás al taller
        boolean ok = taller.registrarUsuario(nombre, user, pass);

        if (ok) {
            System.out.println("Usuario registrado correctamente.");
        } else {
            System.out.println("Ese usuario ya existe.");
        }
    }











}



