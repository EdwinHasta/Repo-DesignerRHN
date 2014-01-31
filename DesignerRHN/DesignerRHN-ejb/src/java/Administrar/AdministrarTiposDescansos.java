/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.TiposDescansos;
import InterfaceAdministrar.AdministrarTiposDescansosInterface;
import InterfacePersistencia.PersistenciaTiposDescansosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposDescansos implements AdministrarTiposDescansosInterface {

    @EJB
    PersistenciaTiposDescansosInterface persistenciaTiposDescansos;

    @Override
    public void modificarTiposDescansos(List<TiposDescansos> listaTiposDescansos) {
        for (int i = 0; i < listaTiposDescansos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposDescansos.editar(listaTiposDescansos.get(i));
        }
    }

    @Override
    public void borrarTiposDescansos(List<TiposDescansos> listaTiposDescansos) {
        for (int i = 0; i < listaTiposDescansos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposDescansos.borrar(listaTiposDescansos.get(i));
        }
    }

    @Override
    public void crearTiposDescansos(List<TiposDescansos> listaTiposDescansos) {
        for (int i = 0; i < listaTiposDescansos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposDescansos.crear(listaTiposDescansos.get(i));
        }
    }

    @Override
    public List<TiposDescansos> consultarTiposDescansos() {
        List<TiposDescansos> listTiposTallas;
        listTiposTallas = persistenciaTiposDescansos.consultarTiposDescansos();
        return listTiposTallas;
    }

    @Override
    public TiposDescansos consultarTipoDescanso(BigInteger secTipoDescanso) {
        TiposDescansos tiposTallas;
        tiposTallas = persistenciaTiposDescansos.consultarTipoDescanso(secTipoDescanso);
        return tiposTallas;
    }

    public BigInteger contarVigenciasJornadasTipoDescanso(BigInteger secuenciaTiposDescansos) {
        BigInteger contarVigenciasJornadasTipoDescanso;
        try {
            System.out.println("Secuencia Tipos Jornadas" + secuenciaTiposDescansos);
            return contarVigenciasJornadasTipoDescanso = persistenciaTiposDescansos.contarVigenciasJornadasTipoDescanso(secuenciaTiposDescansos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposDescansos contarVigenciasJornadasTipoDescanso ERROR :" + e);
            return null;
        }
    }

}
