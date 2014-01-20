/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarMotivosEmbargosInterface;
import Entidades.MotivosEmbargos;
import InterfacePersistencia.PersistenciaMotivosEmbargosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarMotivosEmbargos implements AdministrarMotivosEmbargosInterface {

    @EJB
    PersistenciaMotivosEmbargosInterface persistenciaMotivosEmbargos;

    public void modificarMotivosEmbargos(List<MotivosEmbargos> listaMotivosEmbargos) {
        for (int i = 0; i < listaMotivosEmbargos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaMotivosEmbargos.editar(listaMotivosEmbargos.get(i));
        }
    }

    public void borrarMotivosEmbargos(List<MotivosEmbargos> listaMotivosEmbargos) {
        for (int i = 0; i < listaMotivosEmbargos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaMotivosEmbargos.borrar(listaMotivosEmbargos.get(i));
        }
    }

    public void crearMotivosEmbargos(List<MotivosEmbargos> listaMotivosEmbargos) {
        for (int i = 0; i < listaMotivosEmbargos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaMotivosEmbargos.crear(listaMotivosEmbargos.get(i));
        }
    }

    public List<MotivosEmbargos> mostrarMotivosEmbargos() {
        List<MotivosEmbargos> listMotivosEmbargos;
        listMotivosEmbargos = persistenciaMotivosEmbargos.buscarMotivosEmbargos();
        return listMotivosEmbargos;
    }

    public MotivosEmbargos mostrarMotivoEmbargo(BigInteger secMotivoPrestamo) {
        MotivosEmbargos motivosEmbargos;
        motivosEmbargos = persistenciaMotivosEmbargos.buscarMotivoEmbargo(secMotivoPrestamo);
        return motivosEmbargos;
    }

    public BigInteger contarEersPrestamosMotivoEmbargo(BigInteger secuenciaTiposDias) {
        BigInteger verificarEersPrestamos = null;
        try {
            verificarEersPrestamos = persistenciaMotivosEmbargos.contadorEersPrestamos(secuenciaTiposDias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARMOTIVOSEMBARGOS VERIFICAREERSPRESTAMOS ERROR :" + e);
        } finally {
            return verificarEersPrestamos;
        }
    }

    public BigInteger contarEmbargosMotivoEmbargo(BigInteger secuenciaTiposDias) {
        BigInteger verificarEmbargos = null;
        try {
            verificarEmbargos = persistenciaMotivosEmbargos.contadorEmbargos(secuenciaTiposDias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARMOTIVOSEMBARGOS VERIFICAREMBARGOS ERROR :" + e);
        } finally {
            return verificarEmbargos;
        }
    }
}
