package utilidades;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import javax.swing.table.DefaultTableModel;

public class GeneradorPDF {

    public static String generarFacturaPDF(DefaultTableModel modeloFactura, String nombreCliente, int total, int iva, int subtotal) {
        String rutaArchivo = "factura_" + System.currentTimeMillis() + ".pdf";

        try {
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();

            // Fuente y estilos
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font negrita = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font normal = new Font(Font.FontFamily.HELVETICA, 12);

            // Título
            Paragraph titulo = new Paragraph("Ferretería El Tornillo Feliz 🔩", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            documento.add(new Paragraph(" ", normal)); // Espacio para el logo

            // Info del cliente y fecha
            Paragraph datosCliente = new Paragraph("Cliente: " + nombreCliente, normal);
            Paragraph fecha = new Paragraph("Fecha: " + java.time.LocalDate.now(), normal);
            datosCliente.setSpacingBefore(10f);
            documento.add(datosCliente);
            documento.add(fecha);
            documento.add(new Paragraph(" ")); // Espacio extra

            // Tabla de productos
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);
            tabla.setWidths(new int[]{1, 3, 2, 1, 2});

            String[] encabezados = {"ID", "Producto", "Precio", "Cantidad", "Subtotal"};
            for (String encabezado : encabezados) {
                PdfPCell celda = new PdfPCell(new Phrase(encabezado, negrita));
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
                tabla.addCell(celda);
            }

            for (int i = 0; i < modeloFactura.getRowCount(); i++) {
                for (int j = 0; j < modeloFactura.getColumnCount(); j++) {
                    PdfPCell celdaDato = new PdfPCell(new Phrase(modeloFactura.getValueAt(i, j).toString(), normal));
                    celdaDato.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabla.addCell(celdaDato);
                }
            }

            documento.add(tabla);

            // Totales
            Paragraph subtotalP = new Paragraph("Subtotal: $" + subtotal, normal);
            Paragraph ivaP = new Paragraph("IVA (19%): $" + iva, normal);
            Paragraph totalP = new Paragraph("Total: $" + total, negrita);
            totalP.setSpacingBefore(5f);

            documento.add(subtotalP);
            documento.add(ivaP);
            documento.add(totalP);

            documento.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rutaArchivo;
    }
}
