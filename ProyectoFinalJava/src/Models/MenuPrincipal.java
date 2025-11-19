package Models;

import Exceptions.DuplicadoException;
import Exceptions.UsuarioNoEncontradoException;
import Exceptions.UsuarioYaRegistradoException;
import Gestoras.GestoraGenerica;
import Gestoras.Taller;
import Enums.MetodoDePago;
import Models.Cliente;
import Models.Ticket;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPrincipal {

    private Scanner sc;
    private Taller taller;

    public MenuPrincipal(Taller taller) {
        this.sc = new Scanner(System.in);
        this.taller = taller;
    }


    ///  MENU PRINCIPAL ////////////////////////////////////////////////////////////////////////////


    public void mostrarMenu() {
        int opcion;

        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1) Iniciar sesión");
            System.out.println("2) Registrarse");
            System.out.println("3) Salir");
            System.out.println("==========================");
            System.out.print("Elija una opción: ");

            try{
                opcion = sc.nextInt();
            }
            catch(InputMismatchException e){
                opcion = 0;
            }

            sc.nextLine();

            switch (opcion) {

                case 1:
                    iniciarSesion();
                    break;

                case 2:
                    System.out.print("DNI: ");
                    long dni = sc.nextLong();
                    sc.nextLine();

                    if(taller.existeMecanicoDni(dni)){
                        System.out.println("El dni ingresado ya esta registrado");
                    }
                    else{
                        registrarse(dni);
                    }
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

    ///  INICIAR SESION MECANICO ///////////////////////////////////////////////////////////////////////////

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

    ///  REGISTRAR MECANICO /////////////////////////////////////////////////////////////////////////////

    private void registrarse(long dni) {
        System.out.println("\n--- REGISTRARSE ---");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Apellido: ");
        String apellido = sc.nextLine();

        System.out.print("Teléfono: ");
        long telefono = sc.nextLong();
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

    /// MENU TALLER //////////////////////////////////////////////////////////////////////////////////////////

    private void menuTaller() {
        int opcion;

        do {
            System.out.println("\n===== MENÚ TALLER =====");
            System.out.println("1) Crear ticket");
            System.out.println("2) Ver clientes");
            System.out.println("3) Calcular ganancia mensual");
            System.out.println("4) Imprimir lista de clientes");
            System.out.println("5) Cerrar sesión");
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
                    taller.imprimirClientes();
                case 5:
                    taller.guardarTodo();
                    System.out.println("Sesión cerrada.");
                    return; // vuelve al menú principal

                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (true);
    }

    ///  MANEJO DE TICKETS //////////////////////////////////////////////////////////////////////////////

    private void crearTicket() {
        try {
            System.out.print("DNI del cliente: ");
            long dni = sc.nextInt();
            sc.nextLine();

            Cliente cliente = taller.buscarCliente(dni);

            if(cliente==null){ //si no existe, lo crea
                cliente = cargarClienteNuevo(dni);
            }

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

    private void cargarEnCarrito(){
        taller.getGestorItemTaller().listar();
        int opcion = 0;

        do{
            System.out.println("Ingrese codigo de prodcuto/servicio: ");
            opcion = sc.nextInt();

        }while(opcion!=0);
    }

    // MANEJO DE CLIENTES ////////////////////////////////////////////////////////////////////////////////

    /**
     * pide datos al usuario para cargar un cliente nuevo
     * @return
     */
    private Cliente cargarClienteNuevo(long dni) { //no vuelve a pedir dni, usa el del ticket si no existe
        String nombre;
        String apellido;
        long telefono;
        String email;

        System.out.println("Nombre: ");
        nombre = sc.nextLine();

        System.out.println("Apellido: ");
        apellido = sc.nextLine();

        System.out.println("Telefono: ");
        telefono = sc.nextLong();

        sc.nextLine(); //para limpiar buffer

        System.out.println("Email: ");
        email = sc.nextLine();

        return taller.agregarCliente(nombre, apellido, dni, telefono, email);
    }

    private void mostrarClientes() {
        System.out.println("\n--- LISTADO DE CLIENTES ---");
        System.out.println(taller.mostrarClientes());

    }

    /// OTROS METODOS ////////////////////////////////////////////////////////////////////////////////////////

    private void calcularGanancias() {
        int mes;
        int anio;

        do{
            System.out.print("Mes (1-12): ");
            mes = sc.nextInt();
            if(mes<1 || mes>12){
                System.out.println("El mes ingresado no existe");
            }
        }while(mes<1 || mes>12);

        sc.nextLine();

        do{
            System.out.print("Año: ");
            anio = sc.nextInt();
            if(anio<2025 || anio > LocalDate.now().getYear()){
                System.out.println("Año invalido");
            }
        }while(anio<2025 || anio > LocalDate.now().getYear()); //2025 inicio de actividades

        sc.nextLine();

        double total = taller.calcularGananciaMensual(mes, anio);
        System.out.println("Ganancia del período: $" + total);
    }
}