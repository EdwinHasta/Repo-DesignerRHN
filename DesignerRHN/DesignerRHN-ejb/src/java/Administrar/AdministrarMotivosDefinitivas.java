/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.MotivosDefinitivas;
import InterfaceAdministrar.AdministrarMotivosDefinitivasInterface;
import InterfacePersistencia.PersistenciaMotivosDefinitivasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarMotivosDefinitivas implements AdministrarMotivosDefinitivasInterface {

    @EJB
    PersistenciaMotivosDefinitivasInterface persistenciaMotivosDefinitivas;

    public void modificarMotivosDefinitivas(List<MotivosDefinitivas> listaMotivosDefinitivas) {
        for (int i = 0; i < listaMotivosDefinitivas.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaMotivosDefinitivas.editar(listaMotivosDefinitivas.get(i));
        }
    }

    public void borrarMotivosDefinitivas(List<MotivosDefinitivas> listaMotivosDefinitivas) {
        for (int i = 0; i < listaMotivosDefinitivas.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaMotivosDefinitivas.borrar(listaMotivosDefinitivas.get(i));
        }
    }

    public void crearMotivosDefinitivas(List<MotivosDefinitivas> listaMotivosDefinitivas) {
        for (int i = 0; i < listaMotivosDefinitivas.size(); i++) {
            System.out.println("Administrar Crenando...");
            persistenciaMotivosDefinitivas.crear(listaMotivosDefinitivas.get(i));
        }
    }

    public List<MotivosDefinitivas> mostrarMotivosDefinitivas() {
        List<MotivosDefinitivas> listMotivosDefinitivas;
        listMotivosDefinitivas = persistenciaMotivosDefinitivas.buscarMotivosDefinitivas();
        return listMotivosDefinitivas;
    }

    public MotivosDefinitivas mostrarMotivoDefinitiva(BigInteger secMotivoPrestamo) {
        MotivosDefinitivas motivosDefinitivas;
        motivosDefinitivas = persistenciaMotivosDefinitivas.buscarMotivoDefinitiva(secMotivoPrestamo);
        return motivosDefinitivas;
    }

    public BigInteger contarNovedadesSistemasMotivoDefinitiva(BigInteger secuenciaMotivosCesantias) {
        BigInteger verificarNovedadesSistema = null;
        try {
            verificarNovedadesSistema = persistenciaMotivosDefinitivas.contadorNovedadesSistema(secuenciaMotivosCesantias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARMOTIVOSDEFINITIVAS VERIFICARNOVEDADESSISTEMA ERROR :" + e);
        } finally {
            return verificarNovedadesSistema;
        }
    }

    public BigInteger contarParametrosCambiosMasivosMotivoDefinitiva(BigInteger secuenciaMotivosCesantias) {
        BigInteger verificarParametrosCambiosMasivos = null;
        try {
            verificarParametrosCambiosMasivos = persistenciaMotivosDefinitivas.contadorParametrosCambiosMasivos(secuenciaMotivosCesantias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARMOTIVOSDEFINITIVAS VERIFICARPARAMETROSCAMBIOSMASIVOS ERROR :" + e);
        } finally {
            return verificarParametrosCambiosMasivos;
        }
    }

}
