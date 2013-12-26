/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarSoCondicionesTrabajosInterface;
import Entidades.SoCondicionesTrabajos;
import InterfacePersistencia.PersistenciaSoCondicionesTrabajosInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class AdministrarSoCondicionesTrabajos implements AdministrarSoCondicionesTrabajosInterface {

    @EJB
    PersistenciaSoCondicionesTrabajosInterface persistenciaSoCondicionesTrabajos;
    private SoCondicionesTrabajos soCondicionesTrabajosSeleccionada;
    private SoCondicionesTrabajos soCondicionesTrabajos;
    private List<SoCondicionesTrabajos> listSoCondicionesTrabajos;
    private BigDecimal verificarSoAccidtenesMedicos;
    private BigDecimal verificarInspecciones;
    private BigDecimal verificarSoDetallesPanoramas;
    private BigDecimal verificarSoExposicionesFr;

    public void modificarSoCondicionesTrabajos(List<SoCondicionesTrabajos> listTiposEntidadesModificadas) {
        for (int i = 0; i < listTiposEntidadesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            soCondicionesTrabajosSeleccionada = listTiposEntidadesModificadas.get(i);
            persistenciaSoCondicionesTrabajos.editar(soCondicionesTrabajosSeleccionada);
        }
    }

    public void borrarSoCondicionesTrabajos(SoCondicionesTrabajos tipoCentroCosto) {
        persistenciaSoCondicionesTrabajos.borrar(tipoCentroCosto);
    }

    public void crearSoCondicionesTrabajos(SoCondicionesTrabajos tiposCentrosCostos) {
        persistenciaSoCondicionesTrabajos.crear(tiposCentrosCostos);
    }

    public void buscarSoCondicionesTrabajos(SoCondicionesTrabajos tiposCentrosCostos) {
        persistenciaSoCondicionesTrabajos.crear(tiposCentrosCostos);
    }

    public List<SoCondicionesTrabajos> mostrarSoCondicionesTrabajos() {
        listSoCondicionesTrabajos = persistenciaSoCondicionesTrabajos.buscarSoCondicionesTrabajos();
        return listSoCondicionesTrabajos;
    }

    public SoCondicionesTrabajos mostrarSoCondicionTrabajo(BigInteger secSoCondicionesTrabajos) {
        soCondicionesTrabajos = persistenciaSoCondicionesTrabajos.buscarSoCondicionTrabajo(secSoCondicionesTrabajos);
        return soCondicionesTrabajos;
    }

    public BigDecimal verificarInspecciones(BigInteger secuenciaElementos) {
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            verificarInspecciones = persistenciaSoCondicionesTrabajos.contadorInspecciones(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarSoCondicionesTrabajos verificarInspecciones ERROR :" + e);
        } finally {
            return verificarInspecciones;
        }
    }

    public BigDecimal verificarSoAccidentesMedicos(BigInteger secuenciaElementos) {
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            verificarSoAccidtenesMedicos = persistenciaSoCondicionesTrabajos.contadorSoAccidentesMedicos(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarSoCondicionesTrabajos verificarSoAccidtenesMedicos ERROR :" + e);
        } finally {
            return verificarSoAccidtenesMedicos;
        }
    }

    public BigDecimal verificarSoDetallesPanoramas(BigInteger secuenciaElementos) {
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            verificarSoDetallesPanoramas = persistenciaSoCondicionesTrabajos.contadorSoDetallesPanoramas(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarSoCondicionesTrabajos verificarSoDetallesPanoramas ERROR :" + e);
        } finally {
            return verificarSoDetallesPanoramas;
        }
    }

    public BigDecimal verificarSoExposicionesFr(BigInteger secuenciaElementos) {
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            verificarSoExposicionesFr = persistenciaSoCondicionesTrabajos.contadorSoExposicionesFr(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarClasesAccidentes verificarSoExposicionesFr ERROR :" + e);
        } finally {
            return verificarSoExposicionesFr;
        }
    }

}
