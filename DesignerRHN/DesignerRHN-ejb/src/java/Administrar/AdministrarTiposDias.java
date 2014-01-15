/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarTiposDiasInterface;
import Entidades.TiposDias;
import InterfacePersistencia.PersistenciaTiposDiasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposDias implements AdministrarTiposDiasInterface {

    @EJB
    PersistenciaTiposDiasInterface persistenciaTiposDias;

    @Override
    public void modificarTiposDias(List<TiposDias> listaTiposDias) {
        for (int i = 0; i < listaTiposDias.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposDias.editar(listaTiposDias.get(i));
        }
    }

    @Override
    public void borrarTiposDias(List<TiposDias> listaTiposDias) {
        for (int i = 0; i < listaTiposDias.size(); i++) {
            System.out.println("Administrar Borrar...");
            persistenciaTiposDias.borrar(listaTiposDias.get(i));
        }
    }

    @Override
    public void crearTiposDias(List<TiposDias> listaTiposDias) {
        for (int i = 0; i < listaTiposDias.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposDias.crear(listaTiposDias.get(i));
        }
    }

    @Override
    public List<TiposDias> mostrarTiposDias() {
        List<TiposDias> listTiposDias;
        listTiposDias = persistenciaTiposDias.buscarTiposDias();
        return listTiposDias;
    }

    @Override
    public TiposDias mostrarTipoDia(BigInteger secTipoDia) {
        TiposDias tiposDias;
        tiposDias = persistenciaTiposDias.buscarTipoDia(secTipoDia);
        return tiposDias;
    }

    @Override
    public BigInteger verificarDiasLaborales(BigInteger secuenciaTiposDias) {
        BigInteger verificarBorradoDiasLaborales;
        try {
            return verificarBorradoDiasLaborales = persistenciaTiposDias.contadorDiasLaborales(secuenciaTiposDias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSDIAS VERIFICARDIASLABORALES ERROR :" + e);
            return null;
        }
    }

    @Override
    public BigInteger verificarExtrasRecargos(BigInteger secuenciaTiposDias) {
        BigInteger verificarBorradoExtrasRecargos;
        try {
            return verificarBorradoExtrasRecargos = persistenciaTiposDias.contadorExtrasRecargos(secuenciaTiposDias);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSDIAS VERIFICAREXTRASRECARGOS ERROR :" + e);
            return null;
        }
    }
}
