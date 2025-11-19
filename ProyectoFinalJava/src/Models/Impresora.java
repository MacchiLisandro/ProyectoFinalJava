package Models;

import Gestoras.GestoraGenerica;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

public class Impresora {

    /**
     * crea la carpeta donde se guardan los documentos pdf generados
     */
    public static void crearCarpeta(){
        File carpeta = new File("documentos-pdf");

        if(!carpeta.exists()){
            Boolean creada = carpeta.mkdir();
            if(creada==true){
                System.out.println("Carpeta creada exitosamente");
            }
            else{
                System.out.println("No se pudo crear la carpeta");
            }
        }
        else{
            System.out.println("La carpeta ya existe");
        }
    }
    public static void imprimirListadoClientes(HashSet<Cliente> clientes){
        Document documento = new Document();
        Font tituloFont = new Font(Font.FontFamily.HELVETICA, 40, Font.BOLDITALIC, BaseColor.DARK_GRAY);
        Font subtituloFont = new Font(Font.FontFamily.COURIER, 25, Font.BOLD, BaseColor.GRAY);

        try{
            //crea la instancia para escribir el pdf indicando el documento que genera y donde se guarda
            PdfWriter.getInstance(documento, new FileOutputStream("documentos-pdf/clientes.pdf"));
            documento.open();

            Paragraph titulo = new Paragraph("Taller 3.1", tituloFont);
            documento.add(titulo);

            LineSeparator lineaDivisora = new LineSeparator();
            documento.add(new Chunk(lineaDivisora));

            Paragraph subtitulo =new Paragraph("===== LISTA DE CLIENTES =====\n\n", subtituloFont);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(subtitulo);

            //se crea la tabla
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);

            //se agregan los encabezados de las columnas
            tabla.addCell("Apellido");
            tabla.addCell("Nombre");
            tabla.addCell("DNI");
            tabla.addCell("Telefono");
            tabla.addCell("Email");

            //se agregan los datos de cada cliente
            for(Cliente c : clientes){
                tabla.addCell(c.getApellido());
                tabla.addCell(c.getNombre());
                tabla.addCell(String.valueOf(c.getDni()));
                tabla.addCell(String.valueOf(c.getTelefono()));
                tabla.addCell(String.valueOf(c.getEmail()));
            }

            //se agrega la tabla al documento pdf
            documento.add(tabla);

            documento.close();
        }
        catch(DocumentException | FileNotFoundException e){
            e.getMessage();
        }
    }




    /// /////////////////////Imprime Ticket///////////////////////////////////////////


    public static void imprimirTicket(Ticket ticket){
        Document documento = new Document();

        Font tituloFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLDITALIC, BaseColor.DARK_GRAY);
        Font letraNormal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.GRAY);
        Font letraNegrita = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.DARK_GRAY);

        LineSeparator lineaDivisora = new LineSeparator(letraNormal);
        lineaDivisora.setPercentage(50);

        try{
            PdfWriter.getInstance(documento, new FileOutputStream("documentos-pdf/ticket" + ticket.getId() + ".pdf"));
            documento.open();

            ///ENCABEZADO////////////////////////////////////////////////////////////

            Paragraph nombreTaller = new Paragraph("Taller 3.1", tituloFont);
            nombreTaller.setAlignment(Element.ALIGN_CENTER);
            documento.add(nombreTaller);

            Paragraph datosTaller = new Paragraph(
                    "Calle Sin Salida 123\n" +
                            "Mar del Plata - Buenos Aires\n" +
                            "CUIT: 11-123456789-1\n" +
                            "Inicio de Actividades: 11/2025\n" +
                            "IVA RESPONSABLE INSCRIPTO\n",
                    letraNormal
            );
            datosTaller.setAlignment(Element.ALIGN_CENTER);
            documento.add(datosTaller);

            documento.add(new Chunk(lineaDivisora));

            ///CLIENTE////////////////////////////////////////////////////////////////

            Paragraph tipoFactura = new Paragraph("FACTURA B\n", letraNegrita);
            tipoFactura.setAlignment(Element.ALIGN_CENTER);
            documento.add(tipoFactura);

            Paragraph datosCliente = new Paragraph(
                    "N° " + ticket.getId() + "\n" +
                            "FECHA " + ticket.getFecha() + "\n" +
                            "CONS. FINAL\n" +
                            "DNI " + ticket.getCliente().getDni() + "\n",
                    letraNormal
            );
            datosCliente.setAlignment(Element.ALIGN_CENTER);
            documento.add(datosCliente);

            documento.add(new Chunk(lineaDivisora));

            ///DETALLE///////////////////////////////////////////////////////////////

            PdfPTable tablaDetalle = generarTablaDetalle(ticket.getCarrito(), letraNormal, letraNegrita);
            documento.add(tablaDetalle);

            documento.add(new Chunk(lineaDivisora));

            ///TOTAL/////////////////////////////////////////////////////////////////

            PdfPTable tablaTotal = Impresora.generarTablaTotal(ticket.getPrecioTotal(), letraNegrita);
            documento.add(tablaTotal);

            documento.add(new Chunk(lineaDivisora));

            ///IMPUESTOS////////////////////////////////////////////////////////////

            Paragraph ley = new Paragraph(
                    "Régimen de Transparencia Fiscal\n" +
                            "al Consumidor (Ley 27.743)\n\n",
                    new Font(Font.FontFamily.HELVETICA, 10, Font.UNDERLINE, BaseColor.GRAY)
            );
            ley.setAlignment(Element.ALIGN_CENTER);
            documento.add(ley);

            PdfPTable tablaIVA = generarTablaIVA(ticket.getPrecioTotal(), letraNormal);
            documento.add(tablaIVA);

            documento.close();
        }
        catch(DocumentException | FileNotFoundException e){
            e.getMessage();
        }

    }

    /**
     * itera el carrito para generar el detalle del ticket
     * @param carrito - coleccion que se itera para generar la lista de productos/servicios consumidos
     * @param letraNormal
     * @param letraNegrita
     * @return tablaDetalle - tabla que se agrega al doc pdf para mantener el formato
     */
    private static PdfPTable generarTablaDetalle(ArrayList<ItemTaller> carrito, Font letraNormal, Font letraNegrita){
        PdfPTable tablaDetalle = new PdfPTable(2);
        tablaDetalle.setWidthPercentage(50);

        for(ItemTaller item : carrito){
            String descripcion;

            if(item instanceof Repuesto){
                Repuesto r = (Repuesto) item;
                descripcion = r.getNombre() + " " + r.getMarca();
            }
            else{ //si es servicio
                descripcion = item.getNombre();
            }

            PdfPCell detalle = new PdfPCell(new Phrase(descripcion, letraNormal));
            PdfPCell subtotal = new PdfPCell(new Phrase("$" + String.valueOf(item.getPrecio()), letraNegrita));

            ///pone los bordes en grosor 0 para que no se vean
            detalle.setBorder(0);
            subtotal.setBorder(0);

            ///alinea el texto del subtotal a la derecha
            subtotal.setHorizontalAlignment(Element.ALIGN_RIGHT);

            tablaDetalle.addCell(detalle);
            tablaDetalle.addCell(subtotal);
        }

        return tablaDetalle;
    }

    /**
     * genera una tabla pdf que muestra el total del ticket
     * @param montoTotal
     * @return tablaTotal - tabla que se agrega al doc pdf para mantener el formato
     */
    private static PdfPTable generarTablaTotal(double montoTotal, Font letraNegrita){
        ///crea la tabla donde se guarda el monto total
        PdfPTable tablaTotal = new PdfPTable(2);
        tablaTotal.setWidthPercentage(50); //mismo ancho que el ticket y la linea divisora
        tablaTotal.setHorizontalAlignment(Element.ALIGN_CENTER);

        ///crea una celda vacia que va a la izq y una con el total que va a la der
        PdfPCell celdaIzq = new PdfPCell(new Phrase(""));
        PdfPCell celdaDer = new PdfPCell(new Phrase("TOTAL: $" + montoTotal, letraNegrita));

        ///pone los bordes en grosor 0 para que no se vean
        celdaIzq.setBorder(0);
        celdaDer.setBorder(0);

        ///alinea el texto del total a la derecha
        celdaDer.setHorizontalAlignment(Element.ALIGN_RIGHT);

        ///agrega las celdas a la tabla
        tablaTotal.addCell(celdaIzq);
        tablaTotal.addCell(celdaDer);

        return tablaTotal;
    }

    /**
     * calcula el iva y genera una tabla que se agrega al doc pdf
     * @param montoTotal - sirve para calcular el iva
     * @param letraNormal
     * @return tablaIVA - tabla que se agrega al doc pdf para mantener el formato
     */
    private static PdfPTable generarTablaIVA(double montoTotal, Font letraNormal) {
        ///crea la tabla donde se guarda el iva
        PdfPTable tablaIVA = new PdfPTable(2);
        tablaIVA.setWidthPercentage(50); //mismo ancho que el ticket y la linea divisora
        tablaIVA.setHorizontalAlignment(Element.ALIGN_CENTER);

        ///calcula iva
        double iva = 21 * montoTotal / 100;

        ///crea una celda vacia que va a la izq y una con el total que va a la der
        PdfPCell celdaIzq = new PdfPCell(new Phrase("IVA Contenido:", letraNormal));
        PdfPCell celdaDer = new PdfPCell(new Phrase("$" + iva, letraNormal));

        ///pone los bordes en grosor 0 para que no se vean
        celdaIzq.setBorder(0);
        celdaDer.setBorder(0);

        ///alinea el texto del total a la derecha
        celdaDer.setHorizontalAlignment(Element.ALIGN_RIGHT);

        ///agrega las celdas a la tabla
        tablaIVA.addCell(celdaIzq);
        tablaIVA.addCell(celdaDer);

        return tablaIVA;


    }

}
