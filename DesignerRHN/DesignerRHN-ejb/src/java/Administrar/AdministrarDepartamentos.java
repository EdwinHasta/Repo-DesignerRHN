/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Departamentos;
import InterfaceAdministrar.AdministrarDepartamentosInterface;
import InterfacePersistencia.PersistenciaDepartamentosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class AdministrarDepartamentos implements AdministrarDepartamentosInterface{

    @EJB
    PersistenciaDepartamentosInterface persistenciaDepartamentos;
    
    public List<Departamentos> Departamentos(){
        List<Departamentos> listaDepartamentos;
        listaDepartamentos = persistenciaDepartamentos.departamentos();
        return listaDepartamentos;
    }

}
