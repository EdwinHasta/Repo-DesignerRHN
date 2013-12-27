/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.PryRoles;
import InterfaceAdministrar.AdministrarPryRolesInterface;
import InterfacePersistencia.PersistenciaPryRolesInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful

public class AdministrarPryRoles implements AdministrarPryRolesInterface {
    @EJB
    PersistenciaPryRolesInterface persistenciaPryRoles;
    
    @Override
    public List<PryRoles> PryRoles(){
        List<PryRoles> listaPryRoles;
        listaPryRoles = persistenciaPryRoles.pryroles();
        return listaPryRoles;
    }

    @Override
    public List<PryRoles>  lovPryRoles(){
        return persistenciaPryRoles.pryroles();
    }


}

