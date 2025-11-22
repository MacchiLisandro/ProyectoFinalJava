package Models;

import Enums.Marca;
import Exceptions.*;
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
        int opcion = 0;
        do {
            System.out.println("\n===== MENÚ TALLER =====");
            System.out.println("1) Crear ticket");
            System.out.println("2) Ver clientes");
            System.out.println("3) Calcular ganancia mensual");
            System.out.println("4) Imprimir lista de clientes");
            System.out.println("5) Cerrar sesión");
            System.out.println("==========================");
            System.out.print("Elija una opción: ");

            try{
                opcion = sc.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Ingrese un numero");
            }
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

    /// clientes ///////////////////////

    private void menuClientes(){
        int opcion;

        do{
            System.out.println("\n===== MENÚ CLIENTES =====");
            System.out.println("1) Ver clientes");
            System.out.println("2) Imprimir lista de clientes");
            System.out.println("3) Volver");
            System.out.println("===========================");
            System.out.print("Elija una opción: ");

            try{
                opcion = sc.nextInt();
                sc.nextLine();
            }
            catch(InputMismatchException e){
                System.out.println("Ingrese un numero");
                opcion = 0;
            }

            sc.nextLine();

            switch(opcion){
                case 1:
                    mostrarClientes();
                    break;
                case 2:
                    taller.imprimirClientes();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }while(opcion!=3);
    }

    /// REPUESTO SERVICIO ///////////////////////////

    private void menuItemTaller(){
        int opcion;

        do{
            System.out.println("\n===== MENÚ ITEMTALLER =====");
            System.out.println("1) Cargar nuevo repuesto");
            System.out.println("2) Cargar nuevo servicio");
            System.out.println("3) Eliminar repuesto o servicio");
            System.out.println("4) Modificar stock producto");
            System.out.println("5) Volver");
            System.out.println("=============================");
            System.out.print("Elija una opción: ");

            try{
                opcion = sc.nextInt();
            }
            catch(InputMismatchException e){
                System.out.println("Dato incorrecto");
                opcion = 0;
            }

            sc.nextLine();

            switch(opcion){
                case 1:
                    cargarNuevoRepuesto();
                    break;
                case 2:
                    cargarNuevoServicio();
                    break;
                case 3:
                    bajaItemTaller();
                    break;
                case 4:
                    cambiarStock();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }while(opcion != 5);
    }


    private void cargarNuevoRepuesto(){
        String nombre;
        double precio;
        int stock;
        String marcaIngresada;
        Marca marca;
        double costo;

        System.out.println("Nombre: ");
        nombre = sc.nextLine();

        System.out.println("Precio: ");
        precio = sc.nextDouble();

        sc.nextLine();

        System.out.println("Cantidad de stock: ");
        stock = sc.nextInt();

        sc.nextLine();

        System.out.println("Marca: ");
        marcaIngresada = sc.nextLine();
        marca = Marca.valueOf(marcaIngresada);

        System.out.println("Costo: ");
        costo = sc.nextDouble();

        sc.nextLine();

        try {
            taller.agregarRepuesto(nombre, precio, stock, marca, costo);
        }
        catch(DuplicadoException e){
            System.out.println(e.getMessage());
        }
    }


    private void cargarNuevoServicio(){
        String nombre;
        double precio;
        int tiempoEstimado;
        String descripcion;

        System.out.println("Nombre: ");
        nombre = sc.nextLine();

        System.out.println("Precio: ");
        precio = sc.nextDouble();

        sc.nextLine();

        System.out.println("Tiempo estimado: ");
        tiempoEstimado = sc.nextInt();

        sc.nextLine();

        System.out.println("Descripcion: ");
        descripcion = sc.nextLine();

        try {
            taller.agregarServicio(nombre, precio, tiempoEstimado, descripcion);
        }
        catch(DuplicadoException e){
            System.out.println(e.getMessage());
        }
    }

    private void bajaItemTaller(){
        System.out.println("Ingrese el nombre del producto o servicio que desea eliminar: ");
        String nombre = sc.nextLine();

        try {
            taller.eliminarItemTaller(nombre);
        } catch (NoSeEncuentraEnRegistroException e) {
            System.out.println(e.getMessage());
        }
    }

    private void cambiarStock(){
        String nombreProducto;
        int cantidad;
        int opcion;

        System.out.println("Ingrese el nombre del producto: ");
        nombreProducto = sc.nextLine();

        System.out.println("Ingrese cantidad de stock a sumar o disminuir: ");
        cantidad = sc.nextInt();

        sc.nextLine();

        do{
            System.out.println("=============================");
            System.out.println("1) Agregar stock");
            System.out.println("2) Disminuir stock");
            System.out.println("3) Volver");
            System.out.println("=============================");
            System.out.print("Elija una opción: ");

            try{
                opcion = sc.nextInt();
            }
            catch(InputMismatchException e){
                opcion = 0;
            }

            sc.nextLine();

            switch(opcion){
                case 1:
                    try {
                        taller.modificarStockRepuesto(nombreProducto,cantidad);
                    } catch (NoSeEncuentraEnRegistroException | StockInsuficienteException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        taller.modificarStockRepuesto(nombreProducto,-cantidad);
                    } catch (NoSeEncuentraEnRegistroException | StockInsuficienteException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }while(opcion!=3);
    }

    ///  MANEJO DE TICKETS //////////////////////////////////////////////////////////////////////////////

    private void crearTicket() {
        try {
            System.out.print("DNI del cliente: ");
            long dni = sc.nextLong();
            sc.nextLine();

            Cliente cliente = taller.buscarCliente(dni);

            if(cliente==null){
                cliente = cargarClienteNuevo(dni);
            }

            System.out.println("Método de pago:");
            System.out.println("1) Efectivo");
            System.out.println("2) Débito");
            System.out.println("3) Crédito");
            System.out.print("Opción: ");
            MetodoDePago metodo = MetodoDePago.EFECTIVO;
            int op = 0;
            do {
                try {
                    op = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Ingrese un numero");
                } sc.nextLine();
                switch(op){
                    case 1:
                        metodo = MetodoDePago.EFECTIVO;
                        break;
                    case 2:
                        metodo = MetodoDePago.TARJETA_DEBITO;
                        break;
                    case 3:
                        metodo = MetodoDePago.TRANSFERENCIA;
                        break;
                    default:
                        System.out.println("Opcion incorrecta.");
                }

            } while (op!=1 && op!=2 && op!=3);

            Ticket nuevo = taller.crearTicket(cliente, metodo);
            cargarEnCarrito(nuevo);
            nuevo.calculaPrecio();
            Impresora.imprimirTicket(nuevo);
            System.out.println("Ticket creado. ID: " + nuevo.getId());

        } catch (Exception e) {
            System.out.println("Ingrese un numero");
        }
    }


    /// CARRITO ///////////////////////////////////////////////////////////////////////

    private void cargarEnCarrito(Ticket nuevo) throws NoSeEncuentraEnRegistroException, NoSeEncuentraEnCarritoException {
        int opcion = 0;
        do{
            System.out.println("Menu carrito:");
            System.out.println("1) Agregar item al carrito");
            System.out.println("2) Eliminar item del carrito");
            System.out.println("3) Listar carrito");
            System.out.println("0) Salir al menu anterior");
            try{
                opcion = sc.nextInt();
            } catch(InputMismatchException e){
                System.out.println("Ingrese un numero.");
            }
            sc.nextLine();
            switch(opcion){
                case 1:
                    System.out.println("Ingrese nombre del item");
                    System.out.println(taller.getGestorItemTaller().listar());
                    String nombre = sc.nextLine();
                    ItemTaller item = null;
                    try{
                        item = taller.buscarItemTaller(nombre);
                    } catch (NoSeEncuentraEnRegistroException e){
                        System.out.println(e.getMessage());
                    }

                    nuevo.agregarCarrito(item);
                    break;
                case 2:
                    System.out.println("Ingrese nombre del item a eliminar");
                    nombre = sc.nextLine();
                    item = null;
                    try{
                        item = taller.buscarItemTaller(nombre);

                    } catch (NoSeEncuentraEnRegistroException e) {
                        System.out.println(e.getMessage());
                    }
                    nuevo.eliminarCarrito(item);
                    break;
                case 3:
                    System.out.println(nuevo.listarCarrito());
                    break;
                case 0:
                    System.out.println("Saliendo de menu carrito");
                default:
                    System.out.println("Opcion invalida");
            }

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

        Cliente cliente = taller.agregarCliente(nombre, apellido, dni, telefono, email);

        cargarAutoEnCliente(cliente);

        return cliente;
    }

    private void cargarAutoEnCliente(Cliente cliente){
        Scanner sc = new Scanner(System.in);
        int op = 0;
        String patente;
        Marca marca = Marca.FIAT;
        String modelo;

        System.out.println("Ingresar patente: ");
        patente = sc.nextLine();

        do {
            System.out.println("Seleccione marca:");
            System.out.println("1) FORD");
            System.out.println("2) VOLKSWAGEN");
            System.out.println("3) CHEVROLET");
            System.out.println("4) FIAT");
            System.out.println("5) HONDA");
            System.out.println("6) CITROEN");
            System.out.println("7) PEUGEOT");
            System.out.println("8) MERCEDES_BENZ");
            System.out.println("9) BMW");
            System.out.println("10) ALFA_ROMEO");
            System.out.println("11) RENAULT");

            try {
                System.out.print("Opción: ");
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ingrese un número.");
            } sc.nextLine();


            switch (op) {
                case 1: marca = Marca.FORD; break;
                case 2: marca = Marca.VOLKSWAGEN; break;
                case 3: marca = Marca.CHEVROLET; break;
                case 4: marca = Marca.FIAT; break;
                case 5: marca = Marca.HONDA; break;
                case 6: marca = Marca.CITROEN; break;
                case 7: marca = Marca.PEUGEOT; break;
                case 8: marca = Marca.MERCEDES_BENZ; break;
                case 9: marca = Marca.BMW; break;
                case 10: marca = Marca.ALFA_ROMEO; break;
                case 11: marca = Marca.RENAULT; break;
                default:
                    System.out.println("Opción incorrecta.");
            }

        } while (op < 1 || op > 11);
        System.out.println("Ingrese el modelo:");
        modelo = sc.nextLine();
        Vehiculo vehiculo = new Vehiculo(patente, marca, modelo);
        cliente.agregarAutoCliente(vehiculo);
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