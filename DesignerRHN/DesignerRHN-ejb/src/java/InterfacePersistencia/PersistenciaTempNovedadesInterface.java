/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TempNovedades;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaTempNovedadesInterface {
    
    public void crear(TempNovedades tempNovedades);
    public void editar(TempNovedades tempNovedades);
    public void borrarRegistrosTempNovedades(String usuarioBD);
    public List<TempNovedades> obtenerTempNovedades(String usuarioBD);
    public List<String> obtenerDocumentosSoporteCargados(String usuarioBD);
    public void cargarTempNovedades(String fechaReporte, String nombreCortoFormula, String usarFormula);
    public void reversarTempNovedades(String usuarioBD, String documentoSoporte);
}
