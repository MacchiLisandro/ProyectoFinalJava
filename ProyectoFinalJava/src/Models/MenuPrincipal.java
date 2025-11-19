package Models;

import Exceptions.DuplicadoException;
import Exceptions.UsuarioNoEncontradoException;
import Exceptions.UsuarioYaRegistradoException;
import Gestoras.Taller;
import Enums.MetodoDePago;
import Models.Cliente;
import Models.Ticket;

import java.util.Scanner;

public class MenuPrincipal {

    private Scanner sc;
    private Taller taller;

    public MenuPrincipal(Taller taller) {
        this.sc = new Scanner(System.in);
        this.taller = taller;
    }

    // =======================================================
    //             Metodo Inicio de Menu
    // =======================================================

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
    //                    Iniciar Sesion
    // =======================================================

    private void iniciarSesion() {
        System.out.println("\n--- INICIAR SESIÓN ---");

        System.out.print("Usuario: ");
        String usuario = sc.nextLine();

        System.out.print("Contraseña: ");
        String contrasenia = sc.nextLine();

        try {
            boolean ok = taller.iniciarSesion(usuario, contrasenia);
            if (ok) {
                System.out.println("Sesión iniciada correctamente.");
                menuTaller(); // <<<<<< entra al menú secundario
            }
        } catch (UsuarioNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    // =======================================================
    //                     Registrarse
    // =======================================================

    private void registrarse() {
        System.out.println("\n--- REGISTRARSE ---");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Apellido: ");
        String apellido = sc.nextLine();

        System.out.print("DNI: ");
        int dni = sc.nextInt();
        sc.nextLine();

        System.out.print("Teléfono: ");
        int telefono = sc.nextInt();
        sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Usuario: ");
        String usuario = sc.nextLine();

        System.out.print("Contraseña: ");
        String contrasenia = sc.nextLine();

        try {
            taller.registrarse(nombre, apellido, dni, telefono, email, usuario, contrasenia);
            System.out.println("Registro exitoso. Usuario logueado automáticamente.");

            menuTaller(); // <<<<<< va al menú del taller
        } catch (UsuarioYaRegistradoException | DuplicadoException e) {
            System.out.println(e.getMessage());
        }
    }

    // =======================================================
    //                  Menu del Mecanico
    // =======================================================

    private void menuTaller() {
        int opcion;

        do {
            System.out.println("\n===== MENÚ TALLER =====");
            System.out.println("1) Crear ticket");
            System.out.println("2) Ver clientes");
            System.out.println("3) Calcular ganancia mensual");
            System.out.println("4) Cerrar sesión");
            System.out.println("==========================");
            System.out.print("Elija una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    crearTicket();
                    break;

                case 2:
                    mostrarClientes();
                    break;

                case 3:
                    calcularGanancias();
                    break;

                case 4:
                    System.out.println("Sesión cerrada.");
                    return; // vuelve al menú principal

                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (true);
    }

    // =======================================================
    //                Menu taller
    // =======================================================

    private void crearTicket() {
        try {
            System.out.print("DNI del cliente: ");
            int dni = sc.nextInt();
            sc.nextLine();

            Cliente cliente = taller.buscarCliente(dni);

            System.out.println("Método de pago:");
            System.out.println("1) Efectivo");
            System.out.println("2) Débito");
            System.out.println("3) Crédito");
            System.out.print("Opción: ");
            int op = sc.nextInt();
            sc.nextLine();

            MetodoDePago metodo = MetodoDePago.values()[op - 1];

            Ticket nuevo = taller.crearTicket(cliente, metodo);

            System.out.println("Ticket creado. ID: " + nuevo.getId());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarClientes() {
        System.out.println("\n--- LISTADO DE CLIENTES ---");
        System.out.println(taller.mostrarClientes());

    }

    private void calcularGanancias() {
        System.out.print("Mes (1-12): ");
        int mes = sc.nextInt();

        System.out.print("Año: ");
        int anio = sc.nextInt();
        sc.nextLine();

        double total = taller.calcularGananciaMensual(mes, anio);
        System.out.println("Ganancia del período: $" + total);
    }
}