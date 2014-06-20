/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import Entidades.TiposUnidades;
import Entidades.Unidades;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfaceAdministrar.AdministrarUnidadesInterface;
import InterfacePersistencia.PersistenciaTiposUnidadesInterface;
import InterfacePersistencia.PersistenciaUnidadesInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarUnidades implements AdministrarUnidadesInterface{

    @EJB
    PersistenciaUnidadesInterface persistenciaUnidades;
    @EJB
    PersistenciaTiposUnidadesInterface persistenciaTiposUnidades;
    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;
    
    // Metodos
    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }
    
    @Override
    public List<Unidades> consultarUnidades() {
        List<Unidades> listaUnidades;
        listaUnidades = persistenciaUnidades.consultarUnidades(em);
        return listaUnidades;
    }

    @Override
    public List<TiposUnidades> consultarTiposUnidades() {
        List<TiposUnidades> listaTiposUnidades;
        listaTiposUnidades = persistenciaTiposUnidades.consultarTiposUnidades(em);
        return listaTiposUnidades;
    }
    
}
