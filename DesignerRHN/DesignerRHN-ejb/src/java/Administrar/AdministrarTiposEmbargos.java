/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarTiposEmbargosInterface;
import Entidades.TiposEmbargos;
import InterfacePersistencia.PersistenciaTiposEmbargosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposEmbargos implements AdministrarTiposEmbargosInterface {

    @EJB
    PersistenciaTiposEmbargosInterface persistenciaTiposEmbargos;

    public void modificarTiposPrestamos(List<TiposEmbargos> listaTiposEmbargos) {
        for (int i = 0; i < listaTiposEmbargos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposEmbargos.editar(listaTiposEmbargos.get(i));
        }
    }

    public void borrarTiposPrestamos(List<TiposEmbargos> listaTiposEmbargos) {
        for (int i = 0; i < listaTiposEmbargos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposEmbargos.borrar(listaTiposEmbargos.get(i));
        }
    }

    public void crearTiposPrestamos(List<TiposEmbargos> listaTiposEmbargos) {
        for (int i = 0; i < listaTiposEmbargos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposEmbargos.crear(listaTiposEmbargos.get(i));
        }
    }

    public List<TiposEmbargos> consultarTiposPrestamos() {
        List<TiposEmbargos> listTiposEmbargos;
        listTiposEmbargos = persistenciaTiposEmbargos.buscarTiposEmbargos();
        return listTiposEmbargos;
    }

    public TiposEmbargos consultarTipoPrestamo(BigInteger secMotivoPrestamo) {
        TiposEmbargos tiposEmbargos;
        tiposEmbargos = persistenciaTiposEmbargos.buscarTipoEmbargo(secMotivoPrestamo);
        return tiposEmbargos;
    }

    public BigInteger contarDiasLaboralesTipoEmbargo(BigInteger secuenciaTiposDias) {
        BigInteger verificarBorradoEerPrestamos = null;
        try {
            verificarBorradoEerPrestamos = persistenciaTiposEmbargos.contadorEerPrestamos(secuenciaTiposDias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSEMBARGOS VERIFICARDIASLABORALES ERROR :" + e);
        } finally {
            return verificarBorradoEerPrestamos;
        }
    }

    public BigInteger contarExtrasRecargosTipoEmbargo(BigInteger secuenciaTiposDias) {
        BigInteger verificarBorradoFormasDtos = null;
        try {
            verificarBorradoFormasDtos = persistenciaTiposEmbargos.contadorFormasDtos(secuenciaTiposDias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSEMBARGOS VERIFICAREXTRASRECARGOS ERROR :" + e);
        } finally {
            return verificarBorradoFormasDtos;
        }
    }
}
