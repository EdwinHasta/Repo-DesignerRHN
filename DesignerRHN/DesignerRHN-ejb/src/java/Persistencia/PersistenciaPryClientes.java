/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.PryClientes;
import InterfacePersistencia.PersistenciaPryClientesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaPryClientes implements PersistenciaPryClientesInterface{
    
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
        public List<PryClientes> pryclientes() {
        try {
            Query query = em.createQuery("SELECT pc FROM PryClientes pc ORDER BY pc.nombre");
            List<PryClientes> pryclientes = query.getResultList();
            return pryclientes;
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
        public PryClientes consultaCliente(BigInteger secuencia) {
 
            try {
            Query query = em.createQuery("SELECT pc.nombre + ' / ' + pc.direccion + ' / ' + pc.telefono + ' - ' + pc.contacto FROM PryClientes pc WHERE pc.secuencia =:secuencia ");
            query.setParameter("secuencia", secuencia);
            PryClientes pryClientes = (PryClientes) query.getSingleResult();
            return pryClientes;
        } catch (Exception e) {
            System.out.println("Error buscarProyectoSecuencia PersistenciaProyectos");
            PryClientes pryclientes = null;
            return pryclientes;
        }
    }
    
    

}
