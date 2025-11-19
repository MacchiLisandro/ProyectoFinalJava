package Models;

import Exceptions.DuplicadoException;
import Gestoras.Taller;
import Exceptions.UsuarioNoEncontradoException;
import Exceptions.UsuarioYaRegistradoException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPrincipal {

    private Scanner sc = new Scanner(System.in);
    private Taller taller = new Taller();

    public void mostrarMenu() {
        int opcion;

        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1) Iniciar sesión");
            System.out.println("2) Registrarse");
            System.out.println("3) Salir");
            System.out.println("==========================");
            System.out.print("Elija una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    iniciarSesion();
                    break;

                case 2:
                    registrarse();
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

    // =======================================================
                         /// LOGEO///
    // =======================================================

    private void iniciarSesion() {
        System.out.println("\n--- INICIAR SESIÓN ---");

            System.out.print("Usuario: ");
            String usuario = sc.nextLine();

            System.out.print("Contraseña: ");
            String contrasenia = sc.nextLine();

        try {
            boolean checkeado = taller.iniciarSesion(usuario, contrasenia);
            if (checkeado) {
                System.out.println("Sesión iniciada correctamente.");
            }
        } catch (UsuarioNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void registrarse() {
        System.out.println("\n--- REGISTRARSE ---");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Apellido: ");
        String apellido = sc.nextLine();
        long dni = 0;
        long telefono = 0;
        try{
            System.out.print("DNI: ");
            dni = sc.nextLong();
            sc.nextLine();


            System.out.print("Teléfono: ");
            telefono = sc.nextLong();
            sc.nextLine();

        } catch(InputMismatchException e){
            System.out.println("Ingrese un numero");
        }

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Usuario: ");
        String usuario = sc.nextLine();

        System.out.print("Contraseña: ");
        String contrasenia = sc.nextLine();

        try {
            taller.registrarse(nombre, apellido, dni, telefono, email, usuario, contrasenia);
            System.out.println("Registro exitoso. Usuario logueado automáticamente.");
        } catch (UsuarioYaRegistradoException e) {
            System.out.println(e.getMessage());
        } catch (DuplicadoException e) {
            throw new RuntimeException(e);
        }
    }
}




