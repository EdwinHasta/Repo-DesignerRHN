/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.ColumnasEscenarios;
import InterfacePersistencia.PersistenciaColumnasEscenariosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author PROYECTO01
 */
@Stateless
public class PersistenciaColumnasEscenarios implements PersistenciaColumnasEscenariosInterface{

   @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
   
    @Override 
    public List<ColumnasEscenarios> buscarColumnasEscenarios() {
        try {
            Query query = em.createNativeQuery("SELECT * FROM ColumnasEscenarios cc WHERE ESCENARIO = (select SECUENCIA from escenarios where QVWNOMBRE= 'QVWEMPLEADOSCORTE') ORDER BY cc.nombrecolumna ASC",ColumnasEscenarios.class);
            List<ColumnasEscenarios> competenciascargos = query.getResultList();
            return competenciascargos;
        } catch (Exception e) {
            System.out.println("Error buscarColumnasEscenarios PersistenciaColumnasEscenarios : " + e.toString());
            return null;
        }
    }
}
