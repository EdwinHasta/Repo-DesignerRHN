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
    private TiposAuxilios tiposAuxiliosSeleccionado;
    private TiposAuxilios tiposAuxilios;
    private List<TiposAuxilios> listTiposAuxilios;

    public void modificarTiposAuxilios(List<TiposAuxilios> listaMotivosPrestamosModificados) {
        for (int i = 0; i < listaMotivosPrestamosModificados.size(); i++) {
            System.out.println("Administrar Modificando...");
            tiposAuxiliosSeleccionado = listaMotivosPrestamosModificados.get(i);
            persistenciaTiposAuxilios.editar(tiposAuxiliosSeleccionado);
        }
    }

    public void borrarTiposAuxilios(TiposAuxilios tiposAuxilios) {
        persistenciaTiposAuxilios.borrar(tiposAuxilios);
    }

    public void crearTiposAuxilios(TiposAuxilios tiposAuxilios) {
        persistenciaTiposAuxilios.crear(tiposAuxilios);
    }

    public List<TiposAuxilios> mostrarTiposAuxilios() {
        listTiposAuxilios = persistenciaTiposAuxilios.buscarTiposAuxilios();
        return listTiposAuxilios;
    }

    public TiposAuxilios mostrarTipoAuxilio(BigInteger secTiposAuxilios) {
        tiposAuxilios = persistenciaTiposAuxilios.buscarTipoAuxilio(secTiposAuxilios);
        return tiposAuxilios;
    }

    public BigInteger verificarTablasAuxilios(BigInteger secuenciaTiposAuxilios) {
        BigInteger verificarTablasAuxilios = null;
        try {
            verificarTablasAuxilios = persistenciaTiposAuxilios.contadorTablasAuxilios(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARTIPOSAUXILIOS verificarTablasAuxilios ERROR :" + e);
        } finally {
            return verificarTablasAuxilios;
        }
    }
}
