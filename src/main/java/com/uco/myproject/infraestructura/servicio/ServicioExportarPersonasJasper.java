package com.uco.myproject.infraestructura.servicio;

import com.uco.myproject.dominio.modelo.Persona;
import com.uco.myproject.dominio.servicio.ServicioExportarPdfPersonas;
import com.uco.myproject.infraestructura.error.ExceptionTecnica;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

@Component
public class ServicioExportarPersonasJasper implements ServicioExportarPdfPersonas {

    private static final String ERROR_BORRANDO_EL_ARCHIVO = "Error borrando el archivo";
    private static final String ERROR_CREANDO_EL_ARCHIVO_TEMPORAL = "Error creando el archivo temporal";
    private static final String ERROR_EXPORTANDO_PERSONAS = "Error exportando personas";

    @Override
    public byte[] ejecutar(List<Persona> personas) {

        byte[] resultado;
        File archivoDestino = crearTemporal();
        try(InputStream jasper = Thread.currentThread().getContextClassLoader().getResourceAsStream("personas.jasper")){

            Map<String, Object> parametros = new HashMap<>();

            parametros.put("titulo", "Reporte de personas");

            ArrayList<Persona> datasource = new ArrayList<>(personas);

            if(datasource.isEmpty()) {
                datasource.add(Persona.of("nombre", "apellido"));
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper,parametros,new JRBeanCollectionDataSource(datasource));

            JasperExportManager.exportReportToPdfFile(jasperPrint, archivoDestino.getPath());

            resultado = Files.readAllBytes(archivoDestino.toPath());

            //Files.delete(archivoDestino.toPath());

            System.out.println(archivoDestino.getAbsolutePath());
        } catch(Exception exception) {
            throw new ExceptionTecnica(ERROR_EXPORTANDO_PERSONAS, exception);
        }
        return resultado;
    }

    private File crearTemporal() {
        File temporal;
        try {
            temporal = File.createTempFile(UUID.randomUUID().toString(), ".pdf");
        } catch (IOException e) {
            throw new ExceptionTecnica(ERROR_CREANDO_EL_ARCHIVO_TEMPORAL, e);
        }
        return temporal;
    }
}
