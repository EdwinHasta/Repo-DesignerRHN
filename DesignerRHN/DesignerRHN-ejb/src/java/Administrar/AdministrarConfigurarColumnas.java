/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.ColumnasEscenarios;
import InterfaceAdministrar.AdministrarConfigurarColumnasInterface;
import InterfacePersistencia.PersistenciaColumnasEscenariosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class AdministrarConfigurarColumnas implements AdministrarConfigurarColumnasInterface{

    @EJB
    PersistenciaColumnasEscenariosInterface persistenciaColumnasEscenarios;

    @Override 
    public List<ColumnasEscenarios> listaColumnasEscenarios() {
        try {
            List<ColumnasEscenarios> lista = persistenciaColumnasEscenarios.buscarColumnasEscenarios();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listaColumnasEscenarios Admi : " + e.toString());
            return null;
        }
    }

}
