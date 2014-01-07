/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarEnfermedadesInterface;
import Entidades.Enfermedades;
import InterfacePersistencia.PersistenciaEnfermedadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarEnfermedades implements AdministrarEnfermedadesInterface {

    @EJB
    PersistenciaEnfermedadesInterface persistenciaEnfermedades;
    private Enfermedades enfermedadSeleccionado;
    private Enfermedades enfermedad;
    private List<Enfermedades> listEnfermedades;

    public void modificarEnfermedades(List<Enfermedades> listDeportesModificadas) {
        for (int i = 0; i < listDeportesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            enfermedadSeleccionado = listDeportesModificadas.get(i);
            persistenciaEnfermedades.editar(enfermedadSeleccionado);
        }
    }

    public void borrarEnfermedades(Enfermedades deportes) {
        persistenciaEnfermedades.borrar(deportes);
    }

    public void crearEnfermedades(Enfermedades deportes) {
        persistenciaEnfermedades.crear(deportes);
    }

    public List<Enfermedades> mostrarEnfermedades() {
        listEnfermedades = persistenciaEnfermedades.buscarEnfermedades();
        return listEnfermedades;
    }

    public Enfermedades mostrarEnfermedad(BigInteger secDeportes) {
        enfermedad = persistenciaEnfermedades.buscarEnfermedad(secDeportes);
        return enfermedad;
    }

    public BigInteger contadorAusentimos(BigInteger secuenciaTiposAuxilios) {
        BigInteger contadorAusentimos = null;
        try {
            contadorAusentimos = persistenciaEnfermedades.contadorAusentimos(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARENFERMEDADES contadorAusentimos ERROR :" + e);
        } finally {
            return contadorAusentimos;
        }
    }

    public BigInteger contadorDetallesLicencias(BigInteger secuenciaTiposAuxilios) {
        BigInteger contadorDetallesLicencias = null;
        try {
            contadorDetallesLicencias = persistenciaEnfermedades.contadorDetallesLicencias(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARENFERMEDADES contadorDetallesLicencias ERROR :" + e);
        } finally {
            return contadorDetallesLicencias;
        }
    }

    public BigInteger contadorEnfermedadesPadecidas(BigInteger secuenciaTiposAuxilios) {
        BigInteger contadorEnfermedadesPadecidas = null;
        try {
            contadorEnfermedadesPadecidas = persistenciaEnfermedades.contadorEnfermedadesPadecidas(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARENFERMEDADES contadorEnfermedadesPadecidas ERROR :" + e);
        } finally {
            return contadorEnfermedadesPadecidas;
        }
    }

    public BigInteger contadorSoausentismos(BigInteger secuenciaTiposAuxilios) {
        BigInteger contadorSoausentismos = null;
        try {
            contadorSoausentismos = persistenciaEnfermedades.contadorSoausentismos(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARENFERMEDADES contadorSoausentismos ERROR :" + e);
        } finally {
            return contadorSoausentismos;
        }
    }
    public BigInteger contadorSorevisionessSistemas(BigInteger secuenciaTiposAuxilios) {
        BigInteger contadorSorevisionessSistemas = null;
        try {
            contadorSorevisionessSistemas = persistenciaEnfermedades.contadorSorevisionessSistemas(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARENFERMEDADES contadorSorevisionessSistemas ERROR :" + e);
        } finally {
            return contadorSorevisionessSistemas;
        }
    }
}
