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

    public void modificarEnfermedades(List<Enfermedades> listDeportesModificadas) {
        for (int i = 0; i < listDeportesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaEnfermedades.editar(listDeportesModificadas.get(i));
        }
    }

    public void borrarEnfermedades(List<Enfermedades> listDeportesModificadas) {
        for (int i = 0; i < listDeportesModificadas.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaEnfermedades.borrar(listDeportesModificadas.get(i));
        }
    }

    public void crearEnfermedades(List<Enfermedades> listDeportesModificadas) {
        for (int i = 0; i < listDeportesModificadas.size(); i++) {
            System.out.println("Administrar Crear...");
            persistenciaEnfermedades.crear(listDeportesModificadas.get(i));
        }
    }

    public List<Enfermedades> consultarEnfermedades() {
        List<Enfermedades> listEnfermedades;
        listEnfermedades = persistenciaEnfermedades.buscarEnfermedades();
        return listEnfermedades;
    }

    public Enfermedades consultarEnfermedad(BigInteger secDeportes) {
        Enfermedades enfermedad;
        enfermedad = persistenciaEnfermedades.buscarEnfermedad(secDeportes);
        return enfermedad;
    }

    public BigInteger verificarAusentimos(BigInteger secuenciaTiposAuxilios) {
        BigInteger contadorAusentimos = null;
        try {
            contadorAusentimos = persistenciaEnfermedades.contadorAusentimos(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARENFERMEDADES contadorAusentimos ERROR :" + e);
        } finally {
            return contadorAusentimos;
        }
    }

    public BigInteger verificarDetallesLicencias(BigInteger secuenciaTiposAuxilios) {
        BigInteger contadorDetallesLicencias = null;
        try {
            contadorDetallesLicencias = persistenciaEnfermedades.contadorDetallesLicencias(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARENFERMEDADES contadorDetallesLicencias ERROR :" + e);
        } finally {
            return contadorDetallesLicencias;
        }
    }

    public BigInteger verificarEnfermedadesPadecidas(BigInteger secuenciaTiposAuxilios) {
        BigInteger contadorEnfermedadesPadecidas = null;
        try {
            contadorEnfermedadesPadecidas = persistenciaEnfermedades.contadorEnfermedadesPadecidas(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARENFERMEDADES contadorEnfermedadesPadecidas ERROR :" + e);
        } finally {
            return contadorEnfermedadesPadecidas;
        }
    }

    public BigInteger verificarSoAusentismos(BigInteger secuenciaTiposAuxilios) {
        BigInteger contadorSoausentismos = null;
        try {
            contadorSoausentismos = persistenciaEnfermedades.contadorSoausentismos(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARENFERMEDADES contadorSoausentismos ERROR :" + e);
        } finally {
            return contadorSoausentismos;
        }
    }

    public BigInteger verificarSoRevisionesSistemas(BigInteger secuenciaTiposAuxilios) {
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
