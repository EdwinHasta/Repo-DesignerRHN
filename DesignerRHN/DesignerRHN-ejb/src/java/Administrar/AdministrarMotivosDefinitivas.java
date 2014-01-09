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
    private MotivosDefinitivas motivosDefinitivasSeleccionado;
    private MotivosDefinitivas motivosDefinitivas;
    private List<MotivosDefinitivas> listMotivosDefinitivas;

    public void modificarMotivosDefinitivas(List<MotivosDefinitivas> listaMotivosPrestamosModificados) {
        for (int i = 0; i < listaMotivosPrestamosModificados.size(); i++) {
            System.out.println("Administrar Modificando...");
            motivosDefinitivasSeleccionado = listaMotivosPrestamosModificados.get(i);
            persistenciaMotivosDefinitivas.editar(motivosDefinitivasSeleccionado);
        }
    }

    public void borrarMotivosDefinitivas(MotivosDefinitivas tiposDias) {
        persistenciaMotivosDefinitivas.borrar(tiposDias);
    }

    public void crearMotivosDefinitivas(MotivosDefinitivas tiposDias) {
        persistenciaMotivosDefinitivas.crear(tiposDias);
    }

    public List<MotivosDefinitivas> mostrarMotivosDefinitivas() {
        listMotivosDefinitivas = persistenciaMotivosDefinitivas.buscarMotivosDefinitivas();
        return listMotivosDefinitivas;
    }

    public MotivosDefinitivas mostrarMotivoDefinitiva(BigInteger secMotivoPrestamo) {
        motivosDefinitivas = persistenciaMotivosDefinitivas.buscarMotivoDefinitiva(secMotivoPrestamo);
        return motivosDefinitivas;
    }

    public BigInteger verificarNovedadesSistema(BigInteger secuenciaMotivosCesantias) {
        BigInteger verificarNovedadesSistema = null;
        try {
            verificarNovedadesSistema = persistenciaMotivosDefinitivas.contadorNovedadesSistema(secuenciaMotivosCesantias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARMOTIVOSDEFINITIVAS VERIFICARNOVEDADESSISTEMA ERROR :" + e);
        } finally {
            return verificarNovedadesSistema;
        }
    }

    public BigInteger verificarParametrosCambiosMasivos(BigInteger secuenciaMotivosCesantias) {
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
