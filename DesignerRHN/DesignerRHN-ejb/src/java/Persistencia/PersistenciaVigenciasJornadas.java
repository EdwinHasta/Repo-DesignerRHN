package Persistencia;

import Entidades.VigenciasJornadas;
import InterfacePersistencia.PersistenciaVigenciasJornadasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author AndresPineda
 */

@Stateless
public class PersistenciaVigenciasJornadas implements PersistenciaVigenciasJornadasInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear PersistenciaVigenciasJornadas.
     */

    @Override
    public void crear(VigenciasJornadas vigenciasJornadas) {
        try {
            em.persist(vigenciasJornadas);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser creada (PersistenciaVigenciasJornadas)");
        }
    }

    /*
     *Editar PersistenciaVigenciasJornadas. 
     */

    @Override
    public void editar(VigenciasJornadas vigenciasJornadas) {
        try {
            em.merge(vigenciasJornadas);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la PersistenciaVigenciasJornadas");
        }
    }

    /*
     *Borrar PersistenciaVigenciasJornadas.
     */

    @Override
    public void borrar(VigenciasJornadas vigenciasJornadas) {
        try {
            em.remove(em.merge(vigenciasJornadas));
        } catch (Exception e) {
            System.out.println("Error borrar (PersistenciaVigenciasJornadas)");
        }
    }

    /*
     *Encontrar una PersistenciaVigenciasJornadas. 
     */

    @Override
    public VigenciasJornadas buscarVigenciaJornada(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            return em.find(VigenciasJornadas.class, in);
        } catch (Exception e) {
            System.out.println("Error buscar buscarVigenciaJornada");
            return null;
        }
    }

    /*
     *Encontrar todas las PersistenciaVigenciasJornadas.
     */

    @Override
    public List<VigenciasJornadas> buscarVigenciasJornadas() {
        try{
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasJornadas.class));
        return em.createQuery(cq).getResultList();
        }catch(Exception e){
            System.out.println("Error buscarVigenciasJornadas");
            return null;
        }
    }

    /*
     *Encontrar las PersistenciaVigenciasJornadas de un Empleado.
     */

    @Override
    public List<VigenciasJornadas> buscarVigenciasJornadasEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vj FROM VigenciasJornadas vj WHERE vj.empleado.secuencia = :secuenciaEmpl ORDER BY vj.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            List<VigenciasJornadas> vigenciasJornadas = query.getResultList();
            return vigenciasJornadas;
        } catch (Exception e) {
            System.out.println("Error en buscarVigenciasJornadasEmpleado " + e);
            return null;
        }
    }

    /*
     *Encontrar la PersistenciaVigenciasJornadas por Secuencia.
     */

    @Override
    public VigenciasJornadas buscarVigenciasJornadasSecuencia(BigInteger secVJ) {
        try {
            Query query = em.createNamedQuery("VigenciasJornadas.findBySecuencia").setParameter("secuencia", secVJ);
            VigenciasJornadas vigenciasJornadas = (VigenciasJornadas) query.getSingleResult();
            return vigenciasJornadas;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasJornadasSecuencia Persistencia VL");
            return null;
        }
    }

}
