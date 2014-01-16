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

    @Override
    public void modificarMotivosPrestamos(List<MotivosPrestamos> listaMotivosPrestamos) {
        for (int i = 0; i < listaMotivosPrestamos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaMotivosPrestamos.editar(listaMotivosPrestamos.get(i));
        }
    }

    @Override
    public void borrarMotivosPrestamos(List<MotivosPrestamos> listaMotivosPrestamos) {
        for (int i = 0; i < listaMotivosPrestamos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaMotivosPrestamos.borrar(listaMotivosPrestamos.get(i));
        }
    }

    @Override
    public void crearMotivosPrestamos(List<MotivosPrestamos> listaMotivosPrestamos) {
        for (int i = 0; i < listaMotivosPrestamos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaMotivosPrestamos.crear(listaMotivosPrestamos.get(i));
        }
    }

    @Override
    public List<MotivosPrestamos> mostrarMotivosPrestamos() {
        List<MotivosPrestamos> listMotivosPrestamos;
        listMotivosPrestamos = persistenciaMotivosPrestamos.buscarMotivosPrestamos();
        return listMotivosPrestamos;
    }

    @Override
    public MotivosPrestamos mostrarMotivoPrestamo(BigInteger secMotivoPrestamo) {
        MotivosPrestamos motivosPrestamos;
        motivosPrestamos = persistenciaMotivosPrestamos.buscarMotivoPrestamo(secMotivoPrestamo);
        return motivosPrestamos;
    }

    @Override
    public BigInteger verificarEersPrestamosMotivoPrestamo(BigInteger secuenciaMotivosPrestamos) {
        try {
            BigInteger verificarBorradoEersPrestamos = null;
            return verificarBorradoEersPrestamos = persistenciaMotivosPrestamos.contadorEersPrestamos(secuenciaMotivosPrestamos);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARMOTIVOSPRESTAMOS VERIFICARDIASLABORALES ERROR :" + e);
            return null;
        }
    }
}
