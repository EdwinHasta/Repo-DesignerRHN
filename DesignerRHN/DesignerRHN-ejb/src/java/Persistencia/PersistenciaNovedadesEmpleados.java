/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PersistenciaNovedadesEmpleados {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
}
