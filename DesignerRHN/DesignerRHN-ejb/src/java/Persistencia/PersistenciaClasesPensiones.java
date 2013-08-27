package Persistencia;

import Entidades.ClasesPensiones;
import InterfacePersistencia.PersistenciaClasesPensionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AndresPineda
 */

@Stateless
public class PersistenciaClasesPensiones implements PersistenciaClasesPensionesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Clase Pension.
     */
    @Override
    public void crear(ClasesPensiones clasesPensiones) {
        try{
        em.persist(clasesPensiones);
        }catch(Exception e){
            System.out.println("Error crear PersistenciaClasesPensiones");
        }
    }

    /*
     *Editar Clase Pension. 
     */
    @Override
    public void editar(ClasesPensiones clasesPensiones) {
        try{
        em.merge(clasesPensiones);
        }catch(Exception e){
            System.out.println("Error editar PersistenciaClasesPensiones");
        }
    }

    /*
     *Borrar Clase Pension.
     */
    @Override
    public void borrar(ClasesPensiones clasesPensiones) {
        try{
        em.remove(em.merge(clasesPensiones));
        }catch(Exception e){
            System.out.println("Error borrar PersistenciaClasesPensiones");
        }
    }

    /*
     *Encontrar una Clase Pension. 
     */
    @Override
    public ClasesPensiones buscarClasePension(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(ClasesPensiones.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarClasePension PersistenciaClasesPensiones");
            return null;
        }

    }

    /*
     *Encontrar todas las ClasesPensiones. 
     */
    @Override
    public List<ClasesPensiones> buscarClasesPensiones() {
        try{
        List<ClasesPensiones> clasesPensionesLista = (List<ClasesPensiones>) em.createNamedQuery("ClasesPensiones.findAll").getResultList();
        return clasesPensionesLista;
        }catch(Exception e){
            System.out.println("Error buscarClasesPensiones PersistenciaClasesPensiones");
            return null;
        }
    }
    

    @Override
    public ClasesPensiones buscarClasePensionSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT cp FROM ClasesPensiones cp WHERE cp.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            ClasesPensiones claseP = (ClasesPensiones) query.getSingleResult();
            return claseP;
        } catch (Exception e) {
            System.out.println("Error buscarClasePennsionSecuencia PersistenciaClasesPensiones");
            ClasesPensiones claseP = null;
            return claseP;
        }
        
    }
}
