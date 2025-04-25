package utilidades;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import javax.swing.table.DefaultTableModel;

/**
 * Clase encargada de generar un archivo PDF de factura a partir de los datos de una tabla de productos.
 * Utiliza la librería iText para crear el PDF y agregar los detalles de la factura.
 */
public class GeneradorPDF {

    /**
     * Genera un archivo PDF con los detalles de la factura.
     *
     * @param modeloFactura El modelo de la tabla que contiene los productos y su información.
     * @param nombreCliente El nombre del cliente a quien se le emite la factura.
     * @param total El total de la factura, incluyendo impuestos.
     * @param iva El monto del IVA (Impuesto al Valor Agregado) aplicado a la factura.
     * @param subtotal El subtotal de la factura, sin impuestos.
     * @return La ruta completa del archivo PDF generado.
     */
    public static String generarFacturaPDF(DefaultTableModel modeloFactura, String nombreCliente, int total, int iva, int subtotal) {
        // Generar nombre y ruta del archivo PDF
        String rutaArchivo = "factura_" + System.currentTimeMillis() + ".pdf";

        try {
            // Crear el documento PDF
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();

            // Definir fuentes y estilos
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font negrita = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font normal = new Font(Font.FontFamily.HELVETICA, 12);

            // Agregar el título de la factura
            Paragraph titulo = new Paragraph("Ferretería El Charco ", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            documento.add(new Paragraph(" ", normal)); // Espacio para el logo

            // Agregar datos del cliente y fecha
            Paragraph datosCliente = new Paragraph("Cliente: " + nombreCliente, normal);
            Paragraph fecha = new Paragraph("Fecha: " + java.time.LocalDate.now(), normal);
            datosCliente.setSpacingBefore(10f);
            documento.add(datosCliente);
            documento.add(fecha);
            documento.add(new Paragraph(" ")); // Espacio extra

            // Crear la tabla de productos
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);
            tabla.setWidths(new int[]{1, 3, 2, 1, 2});

            // Agregar encabezados de la tabla
            String[] encabezados = {"ID", "Producto", "Precio", "Cantidad", "Subtotal"};
            for (String encabezado : encabezados) {
                PdfPCell celda = new PdfPCell(new Phrase(encabezado, negrita));
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
                tabla.addCell(celda);
            }

            // Agregar datos de los productos
            for (int i = 0; i < modeloFactura.getRowCount(); i++) {
                for (int j = 0; j < modeloFactura.getColumnCount(); j++) {
                    PdfPCell celdaDato = new PdfPCell(new Phrase(modeloFactura.getValueAt(i, j).toString(), normal));
                    celdaDato.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabla.addCell(celdaDato);
                }
            }

            // Agregar la tabla al documento
            documento.add(tabla);

            // Agregar totales
            Paragraph subtotalP = new Paragraph("Subtotal: $" + subtotal, normal);
            Paragraph ivaP = new Paragraph("IVA (19%): $" + iva, normal);
            Paragraph totalP = new Paragraph("Total: $" + total, negrita);
            totalP.setSpacingBefore(5f);

            documento.add(subtotalP);
            documento.add(ivaP);
            documento.add(totalP);

            // Cerrar el documento PDF
            documento.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rutaArchivo;
    }
}
