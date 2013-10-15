/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.TiposReemplazos;
import InterfaceAdministrar.AdministrarTiposReemplazosInterface;
import InterfacePersistencia.PersistenciaTiposReemplazosInterface;
import Persistencia.PersistenciaTiposReemaplazos;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposReemplazos implements AdministrarTiposReemplazosInterface{

    @EJB
    PersistenciaTiposReemplazosInterface persistenciaTiposReemaplazos;

    @Override
    public List<TiposReemplazos> TiposReemplazos() {
        List<TiposReemplazos> listaTiposReemplazos;
        listaTiposReemplazos = persistenciaTiposReemaplazos.tiposReemplazos();
        return listaTiposReemplazos;
    }

    @Override
    public List<TiposReemplazos> lovTiposReemplazos() {
        return persistenciaTiposReemaplazos.tiposReemplazos();
    }
}
