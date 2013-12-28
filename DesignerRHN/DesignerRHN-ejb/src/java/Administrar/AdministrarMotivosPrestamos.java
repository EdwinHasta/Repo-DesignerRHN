/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import InterfaceAdministrar.AdministrarMotivosPrestamosInterface;
import Entidades.MotivosPrestamos;
import InterfacePersistencia.PersistenciaMotivosPrestamosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarMotivosPrestamos implements AdministrarMotivosPrestamosInterface {

     @EJB
    PersistenciaMotivosPrestamosInterface persistenciaMotivosPrestamos;
    private MotivosPrestamos motivoPrestamoSeleccionado;
    private MotivosPrestamos motivosPrestamos;
    private List<MotivosPrestamos> listMotivosPrestamos;

    public void modificarMotivosPrestamos(List<MotivosPrestamos> listaMotivosPrestamosModificados) {
        for (int i = 0; i < listaMotivosPrestamosModificados.size(); i++) {
            System.out.println("Administrar Modificando...");
            motivoPrestamoSeleccionado = listaMotivosPrestamosModificados.get(i);
            persistenciaMotivosPrestamos.editar(motivoPrestamoSeleccionado);
        }
    }

    public void borrarMotivosPrestamos(MotivosPrestamos tiposDias) {
        persistenciaMotivosPrestamos.borrar(tiposDias);
    }

    public void crearMotivosPrestamos(MotivosPrestamos tiposDias) {
        persistenciaMotivosPrestamos.crear(tiposDias);
    }

    public List<MotivosPrestamos> mostrarMotivosPrestamos() {
        listMotivosPrestamos = persistenciaMotivosPrestamos.buscarMotivosPrestamos();
        return listMotivosPrestamos;
    }

    public MotivosPrestamos mostrarMotivoPrestamo(BigInteger secMotivoPrestamo) {
        motivosPrestamos = persistenciaMotivosPrestamos.buscarMotivoPrestamo(secMotivoPrestamo);
        return motivosPrestamos;
    }

    public BigInteger verificarEersPrestamos(BigInteger secuenciaMotivosPrestamos) {
        BigInteger verificarBorradoEersPrestamos = null;
        try {
            verificarBorradoEersPrestamos = persistenciaMotivosPrestamos.contadorEersPrestamos(secuenciaMotivosPrestamos);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARMOTIVOSPRESTAMOS VERIFICARDIASLABORALES ERROR :" + e);
        } finally {
            return verificarBorradoEersPrestamos;
        }
    }
}
