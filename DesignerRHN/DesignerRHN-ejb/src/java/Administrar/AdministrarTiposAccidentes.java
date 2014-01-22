/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.TiposAccidentes;
import InterfaceAdministrar.AdministrarTiposAccidentesInterface;
import InterfacePersistencia.PersistenciaTiposAccidentesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposAccidentes implements AdministrarTiposAccidentesInterface {

    @EJB
    PersistenciaTiposAccidentesInterface persistenciaTiposAccidentes;

    @Override
    public void modificarTiposAccidentes(List<TiposAccidentes> listaTiposAccidentes) {
        for (int i = 0; i < listaTiposAccidentes.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposAccidentes.editar(listaTiposAccidentes.get(i));
        }
    }
    @Override
    public void borrarTiposAccidentes(List<TiposAccidentes> listaTiposAccidentes) {
        for (int i = 0; i < listaTiposAccidentes.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposAccidentes.borrar(listaTiposAccidentes.get(i));
        }
    }
    @Override
    public void crearTiposAccidentes(List<TiposAccidentes> listaTiposAccidentes) {
        for (int i = 0; i < listaTiposAccidentes.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposAccidentes.crear(listaTiposAccidentes.get(i));
        }
    }
    @Override
    public List<TiposAccidentes> consultarTiposAccidentes() {
        List<TiposAccidentes> listTiposAccidentes;
        listTiposAccidentes = persistenciaTiposAccidentes.buscarTiposAccidentes();
        return listTiposAccidentes;
    }
    @Override
    public TiposAccidentes consultarTiposAccidentes(BigInteger secTiposAccidentes) {
        TiposAccidentes tiposAccidentes;
        tiposAccidentes = persistenciaTiposAccidentes.buscarTipoAccidente(secTiposAccidentes);
        return tiposAccidentes;
    }
    @Override
    public BigInteger contarSoAccidentesMedicosTipoAccidente(BigInteger secuenciaTiposAccidentes) {
        BigInteger verificarSoAccidentesMedicos;

        try {
            return verificarSoAccidentesMedicos = persistenciaTiposAccidentes.contadorSoAccidentesMedicos(secuenciaTiposAccidentes);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSACCIDENTES verificarSoAccidentesMedicos ERROR :" + e);
            return null;
        } finally {
        }
    }
    @Override
    public BigInteger contarAccidentesTipoAccidente(BigInteger secuenciaTiposAccidentes) {
        BigInteger verificarBorradoAccidentes;
        try {
            return verificarBorradoAccidentes = persistenciaTiposAccidentes.contadorAccidentes(secuenciaTiposAccidentes);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSACCIDENTES verificarBorradoAccidentes ERROR :" + e);
            return null;
        } finally {
        }
    }
}
