/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.AdiestramientosNF;
import InterfacePersistencia.PersistenciaAdiestramientosNFInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. 
 * Clase encargada de realizar operaciones sobre la tabla 'AdiestramientosNF' de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaAdiestramientosNF implements PersistenciaAdiestramientosNFInterface{
     /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
        public List<AdiestramientosNF> adiestramientosNF() {
        try {
            Query query = em.createQuery("SELECT aNF FROM AdiestramientosNF aNF ORDER BY aNF.desccripcion");
            List<AdiestramientosNF> adiestramientosNF = query.getResultList();
            return adiestramientosNF;
        } catch (Exception e) {
            return null;
        }
    }

    
}
