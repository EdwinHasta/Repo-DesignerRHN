/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarTiposAuxiliosInterface;
import Entidades.TiposAuxilios;
import InterfacePersistencia.PersistenciaTiposAuxiliosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposAuxilios implements AdministrarTiposAuxiliosInterface {

    @EJB
    PersistenciaTiposAuxiliosInterface persistenciaTiposAuxilios;

    @Override
    public void modificarTiposAuxilios(List<TiposAuxilios> listaTiposAuxilios) {
        for (int i = 0; i < listaTiposAuxilios.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposAuxilios.editar(listaTiposAuxilios.get(i));
        }
    }

    @Override
    public void borrarTiposAuxilios(List<TiposAuxilios> listaTiposAuxilios) {
        for (int i = 0; i < listaTiposAuxilios.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposAuxilios.borrar(listaTiposAuxilios.get(i));
        }
    }

    @Override
    public void crearTiposAuxilios(List<TiposAuxilios> listaTiposAuxilios) {
        for (int i = 0; i < listaTiposAuxilios.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposAuxilios.crear(listaTiposAuxilios.get(i));
        }
    }

    @Override
    public List<TiposAuxilios> consultarTiposAuxilios() {
        List<TiposAuxilios> listTiposAuxilios;
        listTiposAuxilios = persistenciaTiposAuxilios.buscarTiposAuxilios();
        return listTiposAuxilios;
    }

    @Override
    public TiposAuxilios consultarTipoAuxilio(BigInteger secTiposAuxilios) {
        TiposAuxilios tiposAuxilios;
        tiposAuxilios = persistenciaTiposAuxilios.buscarTipoAuxilio(secTiposAuxilios);
        return tiposAuxilios;
    }

    @Override
    public BigInteger verificarTablasAuxilios(BigInteger secuenciaTiposAuxilios) {
        BigInteger verificarTablasAuxilios = null;
        try {
            return verificarTablasAuxilios = persistenciaTiposAuxilios.contadorTablasAuxilios(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSAUXILIOS verificarTablasAuxilios ERROR :" + e);
            return null;
        }
    }
}
