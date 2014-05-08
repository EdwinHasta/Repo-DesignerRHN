/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Periodicidades;
import Entidades.Unidades;
import InterfaceAdministrar.AdministrarPeriodicidadesInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaPeriodicidadesInterface;
import InterfacePersistencia.PersistenciaUnidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarPeriodicidades implements AdministrarPeriodicidadesInterface {

    @EJB
    PersistenciaPeriodicidadesInterface persistenciaPeriodicidades;
    @EJB
    PersistenciaUnidadesInterface persistenciaUnidades;
    /**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que
     * está usando el aplicativo.
     */
    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;

    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }
    
    @Override
    public void modificarPeriodicidades(List<Periodicidades> listaPeriodicidades) {
        try {
            for (int i = 0; i < listaPeriodicidades.size(); i++) {
                System.out.println("Administrar Modificando...");
                persistenciaPeriodicidades.editar(em, listaPeriodicidades.get(i));
            }
        } catch (Exception e) {
            System.err.println("SE JODIDO ESTO ADMINISTRARPERIODICIDADES MODIFICARPERIODICIDADES ERROR : " + e);
        }
    }

    @Override
    public void borrarPeriodicidades(List<Periodicidades> listaPeriodicidades) {
        for (int i = 0; i < listaPeriodicidades.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaPeriodicidades.borrar(em, listaPeriodicidades.get(i));
        }
    }

    @Override
    public void crearPeriodicidades(List<Periodicidades> listaPeriodicidades) {
        for (int i = 0; i < listaPeriodicidades.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaPeriodicidades.crear(em, listaPeriodicidades.get(i));
        }
    }

    @Override
    public List<Periodicidades> consultarPeriodicidades() {
        List<Periodicidades> listTiposTallas;
        listTiposTallas = persistenciaPeriodicidades.consultarPeriodicidades(em);
        return listTiposTallas;
    }

    @Override
    public Periodicidades consultarPeriodicidad(BigInteger secPeriodicidad) {
        Periodicidades tiposTallas;
        tiposTallas = persistenciaPeriodicidades.consultarPeriodicidad(em, secPeriodicidad);
        return tiposTallas;
    }

    public List<Unidades> consultarLOVUnidades() {
        List<Unidades> listaLOVUnidades;
        listaLOVUnidades = persistenciaUnidades.consultarUnidades(em);
        return listaLOVUnidades;
    }

    public BigInteger contarCPCompromisosPeriodicidad(BigInteger secuenciaPeriodicidades) {
        BigInteger contarCPCompromisosPeriodicidad;
        try {
            System.out.println("Secuencia Periodicidades : " + secuenciaPeriodicidades);
            return contarCPCompromisosPeriodicidad = persistenciaPeriodicidades.contarCPCompromisosPeriodicidad(em, secuenciaPeriodicidades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarPeriodicidades contarCPCompromisosPeriodicidad ERROR :" + e);
            return null;
        }
    }

    public BigInteger contarDetallesPeriodicidadesPeriodicidad(BigInteger secuenciaPeriodicidades) {
        BigInteger contarDetallesPeriodicidadesPeriodicidad;
        try {
            System.out.println("Secuencia Periodicidades : " + secuenciaPeriodicidades);
            return contarDetallesPeriodicidadesPeriodicidad = persistenciaPeriodicidades.contarDetallesPeriodicidadesPeriodicidad(em, secuenciaPeriodicidades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarPeriodicidades contarDetallesPeriodicidadesPeriodicidad ERROR :" + e);
            return null;
        }
    }

    public BigInteger contarEersPrestamosDtosPeriodicidad(BigInteger secuenciaPeriodicidades) {
        BigInteger contarEersPrestamosDtosPeriodicidad;
        try {
            System.out.println("Secuencia Periodicidades : " + secuenciaPeriodicidades);
            return contarEersPrestamosDtosPeriodicidad = persistenciaPeriodicidades.contarEersPrestamosDtosPeriodicidad(em, secuenciaPeriodicidades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarPeriodicidades contarEersPrestamosDtosPeriodicidad ERROR :" + e);
            return null;
        }
    }

    public BigInteger contarEmpresasPeriodicidad(BigInteger secuenciaPeriodicidades) {
        BigInteger contarEmpresasPeriodicidad;
        try {
            System.out.println("Secuencia Periodicidades : " + secuenciaPeriodicidades);
            return contarEmpresasPeriodicidad = persistenciaPeriodicidades.contarEmpresasPeriodicidad(em, secuenciaPeriodicidades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarPeriodicidades contarEmpresasPeriodicidad ERROR :" + e);
            return null;
        }
    }

    public BigInteger contarFormulasAseguradasPeriodicidad(BigInteger secuenciaPeriodicidades) {
        BigInteger contarFormulasAseguradasPeriodicidad;
        try {
            System.out.println("Secuencia Periodicidades : " + secuenciaPeriodicidades);
            return contarFormulasAseguradasPeriodicidad = persistenciaPeriodicidades.contarFormulasAseguradasPeriodicidad(em, secuenciaPeriodicidades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarPeriodicidades contarFormulasAseguradasPeriodicidad ERROR :" + e);
            return null;
        }
    }

    public BigInteger contarFormulasContratosPeriodicidad(BigInteger secuenciaPeriodicidades) {
        BigInteger contarFormulasContratosPeriodicidad;
        try {
            System.out.println("Secuencia Periodicidades : " + secuenciaPeriodicidades);
            return contarFormulasContratosPeriodicidad = persistenciaPeriodicidades.contarFormulasContratosPeriodicidad(em, secuenciaPeriodicidades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarPeriodicidades contarFormulasContratosPeriodicidad ERROR :" + e);
            return null;
        }
    }

    public BigInteger contarGruposProvisionesPeriodicidad(BigInteger secuenciaPeriodicidades) {
        BigInteger contarGruposProvisionesPeriodicidad;
        try {
            System.out.println("Secuencia Periodicidades : " + secuenciaPeriodicidades);
            return contarGruposProvisionesPeriodicidad = persistenciaPeriodicidades.contarGruposProvisionesPeriodicidad(em, secuenciaPeriodicidades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarPeriodicidades contarGruposProvisionesPeriodicidad ERROR :" + e);
            return null;
        }
    }

    public BigInteger contarNovedadesPeriodicidad(BigInteger secuenciaPeriodicidades) {
        BigInteger contarNovedadPeriodicidad;
        try {
            System.out.println("Secuencia Periodicidades : " + secuenciaPeriodicidades);
            return contarNovedadPeriodicidad = persistenciaPeriodicidades.contarNovedadesPeriodicidad(em, secuenciaPeriodicidades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarPeriodicidades contarNovedadPeriodicidad ERROR :" + e);
            return null;
        }
    }

    public BigInteger contarParametrosCambiosMasivosPeriodicidad(BigInteger secuenciaPeriodicidades) {
        BigInteger contarParametrosCambiosMasivosPeriodicidad;
        try {
            System.out.println("Secuencia Periodicidades : " + secuenciaPeriodicidades);
            return contarParametrosCambiosMasivosPeriodicidad = persistenciaPeriodicidades.contarParametrosCambiosMasivosPeriodicidad(em, secuenciaPeriodicidades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarPeriodicidades contarParametrosCambiosMasivosPeriodicidad ERROR :" + e);
            return null;
        }
    }

    public BigInteger contarVigenciasFormasPagosPeriodicidad(BigInteger secuenciaPeriodicidades) {
        BigInteger contarVigenciasFormasPagosPeriodicidad;
        try {
            System.out.println("Secuencia Periodicidades : " + secuenciaPeriodicidades);
            return contarVigenciasFormasPagosPeriodicidad = persistenciaPeriodicidades.contarVigenciasFormasPagosPeriodicidad(em, secuenciaPeriodicidades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarPeriodicidades contarVigenciasFormasPagosPeriodicidad ERROR :" + e);
            return null;
        }
    }
}
