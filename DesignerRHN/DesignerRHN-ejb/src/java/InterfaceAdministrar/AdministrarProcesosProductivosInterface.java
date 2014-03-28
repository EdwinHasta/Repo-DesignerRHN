/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.CentrosCostos;
import Entidades.ProcesosProductivos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarProcesosProductivosInterface {

    public void modificarProcesosProductivos(List<ProcesosProductivos> listaProcesosProductivos);

    public void borrarProcesosProductivos(List<ProcesosProductivos> listaProcesosProductivos);

    public void crearProcesosProductivos(List<ProcesosProductivos> listaProcesosProductivos);

    public List<ProcesosProductivos> consultarProcesosProductivos();

    public List<CentrosCostos> consultarLOVCentrosCostos();

    public BigInteger contarCargosProcesoProductivo(BigInteger secuenciaSucursal);

    public BigInteger contarTarifasProductosProcesoProductivo(BigInteger secuenciaSucursal);

    public BigInteger contarUnidadesProducidasProcesoProductivo(BigInteger secuenciaSucursal);
}
