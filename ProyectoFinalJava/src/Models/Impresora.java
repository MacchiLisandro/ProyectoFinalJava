package Models;

import Gestoras.GestoraGenerica;
import com.itextpdf.text.*;
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
}
