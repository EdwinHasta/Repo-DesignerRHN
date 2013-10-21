/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.ConsultasLiquidaciones;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaConsultasLiquidacionesInterface {
    public List<ConsultasLiquidaciones> liquidacionesCerradas(String fechaInicial, String fechaFinal);
    public List<ConsultasLiquidaciones> preNomina();
}
