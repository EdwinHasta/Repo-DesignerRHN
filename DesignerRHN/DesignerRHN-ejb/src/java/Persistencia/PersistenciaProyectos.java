/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Proyectos;
import InterfacePersistencia.PersistenciaProyectosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'Proyectos'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaProyectos implements PersistenciaProyectosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Proyectos proyectos) {
        try {
            em.persist(proyectos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaProyectos");
        }
    }

    @Override
    public void editar(Proyectos proyectos) {
        try {
            em.merge(proyectos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaProyectos");
        }
    }

    @Override
    public void borrar(Proyectos proyectos) {
        try {
            em.remove(em.merge(proyectos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaProyectos");
        }
    }    
    
    @Override
    public Proyectos buscarProyectoSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT p FROM Proyectos p WHERE p.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Proyectos proyectos = (Proyectos) query.getSingleResult();
            return proyectos;
        } catch (Exception e) {
            System.out.println("Error buscarProyectoSecuencia PersistenciaProyectos");
            Proyectos proyectos = null;
            return proyectos;
        }
    }
     
   @Override
    public List<Proyectos> proyectos() {
        try {
            Query query = em.createQuery("SELECT p FROM Proyectos p ORDER BY p.empresa.nombre");
            List<Proyectos> proyectos = query.getResultList();
            return proyectos;
        } catch (Exception e) {
            return null;
        }
    }
   
    @Override
   public Proyectos buscarProyectoNombre(String nombreP){
       try{
           Query query = em.createQuery("SELECT p FROM Proyectos p WHERE p.nombreproyecto =:nombreP");
           query.setParameter("nombreP", nombreP);
           Proyectos pry = (Proyectos) query.getSingleResult();
           return pry;
       }catch(Exception e){
           System.out.println("Error buscarProyectoNombre PersistenciaProyectos : "+e.toString());
           return null;
       }
   }
}
