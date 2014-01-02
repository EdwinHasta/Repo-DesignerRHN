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
    private MotivosEmbargos motivosEmbargosSeleccionado;
    private MotivosEmbargos motivosEmbargos;
    private List<MotivosEmbargos> listMotivosEmbargos;

    public void modificarMotivosEmbargos(List<MotivosEmbargos> listaMotivosPrestamosModificados) {
        for (int i = 0; i < listaMotivosPrestamosModificados.size(); i++) {
            System.out.println("Administrar Modificando...");
            motivosEmbargosSeleccionado = listaMotivosPrestamosModificados.get(i);
            persistenciaMotivosEmbargos.editar(motivosEmbargosSeleccionado);
        }
    }

    public void borrarMotivosEmbargos(MotivosEmbargos tiposDias) {
        persistenciaMotivosEmbargos.borrar(tiposDias);
    }

    public void crearMotivosEmbargos(MotivosEmbargos tiposDias) {
        persistenciaMotivosEmbargos.crear(tiposDias);
    }

    public List<MotivosEmbargos> mostrarMotivosEmbargos() {
        listMotivosEmbargos = persistenciaMotivosEmbargos.buscarMotivosEmbargos();
        return listMotivosEmbargos;
    }

    public MotivosEmbargos mostrarMotivoEmbargo(BigInteger secMotivoPrestamo) {
        motivosEmbargos = persistenciaMotivosEmbargos.buscarMotivoEmbargo(secMotivoPrestamo);
        return motivosEmbargos;
    }

    public BigInteger verificarEersPrestamos(BigInteger secuenciaTiposDias) {
        BigInteger verificarEersPrestamos = null;
        try {
            verificarEersPrestamos = persistenciaMotivosEmbargos.contadorEersPrestamos(secuenciaTiposDias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARMOTIVOSEMBARGOS VERIFICAREERSPRESTAMOS ERROR :" + e);
        } finally {
            return verificarEersPrestamos;
        }
    }

    public BigInteger verificarEmbargos(BigInteger secuenciaTiposDias) {
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
