package com.uco.myproject.infraestructura.servicio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.uco.myproject.dominio.servicio.ServicioExportarExcel;
import com.uco.myproject.infraestructura.error.ExceptionTecnica;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.sf.jxls.transformer.XLSTransformer;

@Component
public class ServicioExportarExcelPoi implements ServicioExportarExcel {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicioExportarExcelPoi.class);

    private static final String ERROR_BORRANDO_EL_ARCHIVO = "Error borrando el archivo";
    private static final String ERROR_CREANDO_EL_ARCHIVO_TEMPORAL = "Error creando el archivo temporal";
    private static final String ERROR_EXPORTANDO_EL_DOCUMENTO = "Error exportando el documento";
    
    private static final String FILAS = "filas";
    private static final String XLS = ".xls";
    
    @Override
	public byte[] ejecutar(List<? extends Object> registros,String template) {

        LOGGER.info("En clase de excel nombre del hilo : " + Thread.currentThread().getName());

        byte[] resultado;
        Map<String, Object> parametros = new HashMap<>();
        parametros.put(FILAS, registros);
        
        File archivoGenerado = crearTemporal();
        XLSTransformer transformer = new XLSTransformer();  
        
		try(InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(template);
            FileOutputStream fileOutputStream = new FileOutputStream(archivoGenerado);
            HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(inputStream, parametros)) { 
            
            workbook.write(fileOutputStream);  
            resultado = Files.readAllBytes(archivoGenerado.toPath());
            
        } catch(Exception exception) {
            throw new ExceptionTecnica(ERROR_EXPORTANDO_EL_DOCUMENTO,exception);
        } finally {
            borrarArchivo(archivoGenerado);
        }
        
        return resultado;
    }

    private void borrarArchivo(File archivoGenerado) {
        try {
            Files.delete(archivoGenerado.toPath());
        } catch (IOException e) {
            throw new ExceptionTecnica(ERROR_BORRANDO_EL_ARCHIVO, e);
        }
    }
    
    private File crearTemporal() {
        File temporal;
        try {
            temporal = File.createTempFile(UUID.randomUUID().toString(), XLS);
        } catch (IOException e) {
            throw new ExceptionTecnica(ERROR_CREANDO_EL_ARCHIVO_TEMPORAL, e);
        }
        return temporal;
    }
}
