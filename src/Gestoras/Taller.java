package Gestoras;

import Enums.Marca;
import Enums.MetodoDePago;
import Exceptions.*;
import Models.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class Taller {
    private Mecanico mecanicoLogeado;
    private GestoraGenerica<Cliente> gestorClientes;
    private GestoraGenerica<Mecanico> gestorMecanicos;
    private GestoraGenerica<ItemTaller> gestorItemTaller;
    /// Utilizamos un LinkedHashSet porque los tickets son unicos y requieren de un orden de insercion para
    /// leerlos y guardar el contador de id.
    private LinkedHashSet<Ticket> gestorTickets;

    public Taller() {
        gestorClientes = new GestoraGenerica<>();
        gestorMecanicos = new GestoraGenerica<>();
        gestorTickets = new LinkedHashSet();
        gestorItemTaller = new GestoraGenerica<>();
    }


    /// getter
    public GestoraGenerica<Cliente> getGestorClientes() {
        return gestorClientes;
    }

    public GestoraGenerica<ItemTaller> getGestorItemTaller() {
        return gestorItemTaller;
    }

    public LinkedHashSet<Ticket> getGestorTickets() {
        return gestorTickets;
    }

    /// Metodo para actualizar el contador. pendiente---
     /*public void actualizarContadorId() {
        int max = 0;

        for (Ticket t : gestorTickets) {
            if (t.getId() > max) {
                max = t.getId();
            }
        }

        Ticket.setContadorIds(max + 1);
    }
*/





    /// Metodos de funcionamiento-----------------------------------------------------------------------------------------

    /// Metodo para ingresar un cliente
    public Cliente agregarCliente(String nombre, String apellido, long dni, long telefono, String email){
        Cliente c = new Cliente(nombre, apellido, dni, telefono, email);
        gestorClientes.agregar(c);
        return c;
    }

    public void eliminarCliente(long dni)throws NoSeEncuentraEnRegistroException{
        Cliente cliente = buscarCliente(dni);
        gestorClientes.eliminar(cliente);
    }

    public Cliente buscarCliente (long dni){
        for (Cliente c: gestorClientes.contenedor) {
            if (c.getDni() == dni) {
                return c;
            }
        }
        return null;
    }


    public String mostrarClientes (){
        return gestorClientes.listar();
        }

    ///

    /// Metodo para crear un ticket

    public Ticket crearTicket(Cliente cliente, MetodoDePago metodo) {
        if (mecanicoLogeado == null) {
            throw new RuntimeException("No hay mecánico logueado.");
        }

        /// aca se asigna el mecanico logueado
        Ticket ticket = new Ticket(cliente, mecanicoLogeado, metodo);

        /// se agrega al registro de ticket
        gestorTickets.add(ticket);

        return ticket;
    }



    /// Metodo buscar mecanico
    public Mecanico buscarMecanico (String usuario){
        for (Mecanico m: gestorMecanicos.contenedor){
            if(m.getUsuario().equals(usuario)){
                return m;
            }
        } return null;
    }

    public boolean existeMecanicoDni (long dni){
        for (Mecanico m: gestorMecanicos.contenedor){
            if(m.getDni()==dni){
                return true;
            }
        } return false;
    }

    /// Metodo para iniciar sesion
    public boolean iniciarSesion (String usuario, String contrasenia)throws UsuarioNoEncontradoException{
        String contraseniaHasheada = Seguridad.hashearContrasenia(contrasenia);
        Mecanico m = buscarMecanico(usuario);
        if(m!=null && m.getContrasenia().equals(contraseniaHasheada)){
            this.mecanicoLogeado = m;
            return true;
        } throw new UsuarioNoEncontradoException("El usuario o la contraseña no son correctos");
    }

    public void registrarse (String nombre, String apellido, long dni, long telefono, String email, String usuario, String contrasenia) throws UsuarioYaRegistradoException, DuplicadoException {
        Mecanico m = buscarMecanico(usuario);
        if(m==null){
            Mecanico mecanico = new Mecanico(nombre, apellido, dni, telefono, email, usuario, contrasenia);
            this.mecanicoLogeado = mecanico;
            gestorMecanicos.agregar(mecanico);
        } else {
            throw new UsuarioYaRegistradoException("El usuario ya esta registrado");
        }
    }

    public void darseDeBaja () throws NoSeEncuentraEnRegistroException {
       gestorMecanicos.eliminar(mecanicoLogeado);
       this.mecanicoLogeado = null;
    }

    /// Metodo que calcula ganancias mensuales
    public double calcularGananciaMensual(int mes, int anio) {
        double total = 0;

        for (Ticket t : gestorTickets) {
            if (t.getFecha().getMonthValue() == mes &&
                    t.getFecha().getYear() == anio) {

                total += t.getPrecioTotal();
            }
        }

        return total;
    }


    /// Metodo para agregar un ItemTaller
    public void agregarServicio(String nombre, double precio, int tiempoEstimado, String descripcion) throws DuplicadoException {
        Servicio servicio = new Servicio(nombre, precio, tiempoEstimado, descripcion);
        gestorItemTaller.agregar(servicio);
    }

    public void agregarRepuesto(String nombre, double precio, int stock, Marca marca, double costo) throws DuplicadoException{
        Repuesto repuesto = new Repuesto(nombre, precio,  stock, marca, costo);
        gestorItemTaller.agregar(repuesto);
    }

  public ItemTaller buscarItemTaller(String nombre) throws NoSeEncuentraEnRegistroException {
        String nombreNormalizado = nombre.trim().toLowerCase();
        for (ItemTaller item : gestorItemTaller.contenedor) {
            String nombreItemNormalizado = item.getNombre().trim().toLowerCase();
            if (nombreItemNormalizado.equals(nombreNormalizado)) {
                return item;
            }
        }
        throw new NoSeEncuentraEnRegistroException("Item no encontrado: " + nombre);
    }


    public void eliminarItemTaller(String nombre)throws NoSeEncuentraEnRegistroException{
        ItemTaller item = buscarItemTaller(nombre);
        gestorItemTaller.eliminar(item);
    }

    public void modificarStockRepuesto(String nombre, int cantidad) throws NoSeEncuentraEnRegistroException, StockInsuficienteException {
        Repuesto repuesto = (Repuesto) buscarItemTaller(nombre);
        int nuevoStock = repuesto.getStock() + cantidad;
        if(nuevoStock<0){
            throw new StockInsuficienteException("Stock insuficiente. Hay " + repuesto.getStock() + " disponibles");
        }
        repuesto.setStock(nuevoStock);
    }
    

    /// Metodo para guardar todas las collection en JSon
    public void guardarTodo(){
        try{
            JSONArray arrayClientes = gestorClientes.toJsonArray();
            JsonUtiles.grabarUnJson(arrayClientes,"clientes.json");
            JSONArray arrayMecanicos = gestorMecanicos.toJsonArray();
            JsonUtiles.grabarUnJson(arrayMecanicos,"mecanicos.json");
            JSONArray arrayTicket = ticketToJsonArray();
            JsonUtiles.grabarUnJson(arrayTicket,"tickets.json");
            JSONArray arrayItemTaller = gestorItemTaller.toJsonArray();
            JsonUtiles.grabarUnJson(arrayItemTaller, "itemTaller.json");
        } catch (JSONException e){
            e.printStackTrace();
        }
        this.mecanicoLogeado = null;
    }

    public void cargarTodo(){
        try{
            cargarClientes();
            cargarMecanicos();
            cargarItemTaller();
            cargarTickets();
            Impresora.crearCarpeta();
        } catch (JSONException e){
            e.printStackTrace();
        } catch (DuplicadoException e){
            System.out.println(e.getMessage());
        }
    }

    private void cargarClientes()throws JSONException, DuplicadoException{
        JSONTokener tokener = JsonUtiles.leerUnJson("clientes.json");
        JSONArray arrayClientes = new JSONArray(tokener);
        gestorClientes.contenedor.clear();
        for (int i = 0; i<arrayClientes.length(); i++){
            this.gestorClientes.agregar(Cliente.fromJson(arrayClientes.getJSONObject(i)));

        }
    }

    private void cargarMecanicos()throws JSONException, DuplicadoException{
        JSONTokener tokener = JsonUtiles.leerUnJson("mecanicos.json");
        JSONArray arrayMecanicos = new JSONArray(tokener);
        gestorMecanicos.contenedor.clear();
        for(int i = 0; i<arrayMecanicos.length(); i++){
            this.gestorMecanicos.agregar(Mecanico.fromJson(arrayMecanicos.getJSONObject(i)));
        }
    }

    private void cargarItemTaller()throws JSONException, DuplicadoException{
        JSONTokener tokener = JsonUtiles.leerUnJson("itemTaller.json");
        JSONArray arrayItemTaller = new JSONArray(tokener);
        gestorItemTaller.contenedor.clear();
        for(int i = 0; i<arrayItemTaller.length(); i++) {
            JSONObject itemJson = arrayItemTaller.getJSONObject(i);
            String tipo = itemJson.getString("tipo");

            if (tipo.equals("Repuesto")) {
                this.gestorItemTaller.agregar(Repuesto.fromJson(arrayItemTaller.getJSONObject(i)));
            }
            if (tipo.equals("Servicio")) {
                this.gestorItemTaller.agregar(Servicio.fromJson(arrayItemTaller.getJSONObject(i)));
            }
        }}

    private void cargarTickets() throws JSONException {
        JSONTokener tokener = JsonUtiles.leerUnJson("tickets.json");
        JSONArray  arrayTickets = new JSONArray(tokener);
        gestorTickets.clear();
        for(int i = 0; i<arrayTickets.length(); i++){
            this.gestorTickets.add(Ticket.fromJson(arrayTickets.getJSONObject(i)));
        }
        if(gestorTickets.isEmpty()){
            Ticket.setContadorIds(0);
        }
        else {
            Ticket.setContadorIds(gestorTickets.getLast().getId() + 1);
        }
    }

    private JSONArray ticketToJsonArray () throws JSONException {
        JSONArray jsonArray = new JSONArray();
        try{
            for (Ticket t: gestorTickets){
                jsonArray.put(t.toJson());
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void imprimirClientes(){
        Impresora.imprimirListadoClientes(gestorClientes.contenedor);
    }
}
