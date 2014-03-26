/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.CentrosCostos;
import Entidades.ProcesosProductivos;
import InterfaceAdministrar.AdministrarProcesosProductivosInterface;
import InterfacePersistencia.PersistenciaCentrosCostosInterface;
import InterfacePersistencia.PersistenciaProcesosProductivosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarProcesosProductivos implements AdministrarProcesosProductivosInterface {

    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaProcesosProductivos'.
     */
    @EJB
    PersistenciaProcesosProductivosInterface persistenciaProcesosProductivos;
    @EJB
    PersistenciaCentrosCostosInterface persistenciaCentrosCostos;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------

    public void modificarProcesosProductivos(List<ProcesosProductivos> listaProcesosProductivos) {
        for (int i = 0; i < listaProcesosProductivos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaProcesosProductivos.editar(listaProcesosProductivos.get(i));
        }
    }

    public void borrarProcesosProductivos(List<ProcesosProductivos> listaProcesosProductivos) {
        for (int i = 0; i < listaProcesosProductivos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaProcesosProductivos.borrar(listaProcesosProductivos.get(i));
        }
    }

    public void crearProcesosProductivos(List<ProcesosProductivos> listaProcesosProductivos) {
        for (int i = 0; i < listaProcesosProductivos.size(); i++) {
            persistenciaProcesosProductivos.crear(listaProcesosProductivos.get(i));
        }
    }

    @Override
    public List<ProcesosProductivos> consultarProcesosProductivos() {
        List<ProcesosProductivos> listaProcesosProductivos;
        listaProcesosProductivos = persistenciaProcesosProductivos.consultarProcesosProductivos();
        return listaProcesosProductivos;
    }

    public List<CentrosCostos> consultarLOVCentrosCostos() {
        List<CentrosCostos> listLOVCentrosCostos;
        listLOVCentrosCostos = persistenciaCentrosCostos.buscarCentrosCostos();
        return listLOVCentrosCostos;
    }

    public BigInteger contarCargosProcesoProductivo(BigInteger secuenciaSucursal) {
        BigInteger contarVigenciasFormasPagosSucursal;
        contarVigenciasFormasPagosSucursal = persistenciaProcesosProductivos.contarCargosProcesoProductivo(secuenciaSucursal);
        return contarVigenciasFormasPagosSucursal;
    }

    public BigInteger contarTarifasProductosProcesoProductivo(BigInteger secuenciaSucursal) {
        BigInteger contarVigenciasFormasPagosSucursal;
        contarVigenciasFormasPagosSucursal = persistenciaProcesosProductivos.contarTarifasProductosProcesoProductivo(secuenciaSucursal);
        return contarVigenciasFormasPagosSucursal;
    }

    public BigInteger contarUnidadesProducidasProcesoProductivo(BigInteger secuenciaSucursal) {
        BigInteger contarVigenciasFormasPagosSucursal;
        contarVigenciasFormasPagosSucursal = persistenciaProcesosProductivos.contarUnidadesProducidasProcesoProductivo(secuenciaSucursal);
        return contarVigenciasFormasPagosSucursal;
    }
}
