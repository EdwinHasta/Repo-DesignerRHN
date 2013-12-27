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
    private TiposEmbargos tiposEmbargosSeleccionado;
    private TiposEmbargos tiposEmbargos;
    private List<TiposEmbargos> listTiposEmbargos;

    public void modificarTiposPrestamos(List<TiposEmbargos> listaTiposEmbargosModificados) {
        for (int i = 0; i < listaTiposEmbargosModificados.size(); i++) {
            System.out.println("Administrar Modificando...");
            tiposEmbargosSeleccionado = listaTiposEmbargosModificados.get(i);
            persistenciaTiposEmbargos.editar(tiposEmbargosSeleccionado);
        }
    }

    public void borrarTiposPrestamos(TiposEmbargos tiposEmbargos) {
        persistenciaTiposEmbargos.borrar(tiposEmbargos);
    }

    public void crearTiposPrestamos(TiposEmbargos tiposEmbargos) {
        persistenciaTiposEmbargos.crear(tiposEmbargos);
    }

    public List<TiposEmbargos> mostrarTiposPrestamos() {
        listTiposEmbargos = persistenciaTiposEmbargos.buscarTiposEmbargos();
        return listTiposEmbargos;
    }

    public TiposEmbargos mostrarTipoPrestamo(BigInteger secMotivoPrestamo) {
        tiposEmbargos = persistenciaTiposEmbargos.buscarTipoEmbargo(secMotivoPrestamo);
        return tiposEmbargos;
    }

    public BigInteger verificarDiasLaborales(BigInteger secuenciaTiposDias) {
        BigInteger verificarBorradoEerPrestamos = null;
        try {
            verificarBorradoEerPrestamos = persistenciaTiposEmbargos.contadorEerPrestamos(secuenciaTiposDias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSEMBARGOS VERIFICARDIASLABORALES ERROR :" + e);
        } finally {
            return verificarBorradoEerPrestamos;
        }
    }

    public BigInteger verificarExtrasRecargos(BigInteger secuenciaTiposDias) {
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
