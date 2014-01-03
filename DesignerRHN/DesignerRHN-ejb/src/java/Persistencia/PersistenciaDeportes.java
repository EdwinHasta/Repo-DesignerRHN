/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Deportes;
import InterfacePersistencia.PersistenciaDeportesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Deportes'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaDeportes implements PersistenciaDeportesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Deportes deportes) {
        try{
        em.persist(deportes);
        } catch(Exception e){
            System.out.println("Error creando Deportes PersistenciaDeportes");
        }
    }
  
    @Override
    public void editar(Deportes deportes) {
        try {
        em.merge(deportes);
        } catch(Exception e){
            System.out.println("Error editando Deportes PersistenciaDeportes");
        }
    }
 
    @Override
    public void borrar(Deportes deportes) {
        try{
        em.remove(em.merge(deportes));
        } catch(Exception e){
            System.out.println("Error borrando Deportes PersistenciaDeportes");
        }
    }

    @Override
    public Deportes buscarDeporte(Object id) {
        try {
            BigInteger in;
            in = (BigInteger) id;
            return em.find(Deportes.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarDeporte PersistenciaDeportes : "+e.toString());
            return null;
        }
    }

    @Override
    public List<Deportes> buscarDeportes() {
        try{
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Deportes.class));
        return em.createQuery(cq).getResultList();
        } catch(Exception e){
            System.out.println("Error buscarDeportes PersistenciaDeportes");
            return null;
        }
    }

}
