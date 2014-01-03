/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasIndicadores;
import InterfacePersistencia.PersistenciaVigenciasIndicadoresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasIndicadores'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVigenciasIndicadores implements PersistenciaVigenciasIndicadoresInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasIndicadores vigenciasIndicadores) {
        try {
            em.persist(vigenciasIndicadores);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaVigenciasIndicadores : " + e.toString());
        }
    }

    @Override
    public void editar(VigenciasIndicadores vigenciasIndicadores) {
        try {
            em.merge(vigenciasIndicadores);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaVigenciasIndicadores : " + e.toString());
        }
    }

    @Override
    public void borrar(VigenciasIndicadores vigenciasIndicadores) {
        try {
            em.remove(em.merge(vigenciasIndicadores));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaVigenciasIndicadores : " + e.toString());
        }
    }

    @Override
    public List<VigenciasIndicadores> buscarVigenciasIndicadores() {
        try {
            Query query = em.createQuery("SELECT vi FROM VigenciasIndicadores vi ORDER BY vi.secuencia");
            List<VigenciasIndicadores> vigenciasIndicadores = query.getResultList();
            return vigenciasIndicadores;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasIndicadores PersistenciaVigenciasIndicadores : " + e.toString());
            return null;
        }
    }

    @Override
    public VigenciasIndicadores buscarVigenciaIndicadorSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vi FROM VigenciasIndicadores vi WHERE vi.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            VigenciasIndicadores vigenciasIndicadores = (VigenciasIndicadores) query.getSingleResult();
            return vigenciasIndicadores;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaIndicadorSecuencia PersistenciaVigenciasIndicadores : " + e.toString());
            VigenciasIndicadores vigenciasIndicadores = null;
            return vigenciasIndicadores;
        }
    }

    @Override
    public List<VigenciasIndicadores> ultimosIndicadoresEmpleado(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT COUNT(vi) FROM VigenciasIndicadores vi WHERE vi.empleado.secuencia = :secuenciaEmpl");
            query.setParameter("secuenciaEmpl", secuencia);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT vi FROM VigenciasIndicadores vi WHERE vi.empleado.secuencia = :secuenciaEmpl and vi.fechainicial = (SELECT MAX(vin.fechainicial) FROM VigenciasIndicadores vin WHERE vin.empleado.secuencia = :secuenciaEmpl)");
                queryFinal.setParameter("secuenciaEmpl", secuencia);
                List<VigenciasIndicadores> listaVigenciasIndicadores = queryFinal.getResultList();
                return listaVigenciasIndicadores;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasIndicadores.indicadoresPersona" + e);
            return null;
        }
    }

    @Override
    public List<VigenciasIndicadores> indicadoresTotalesEmpleadoSecuencia(BigInteger secuencia) {
        try {
            Query queryFinal = em.createQuery("SELECT vi FROM VigenciasIndicadores vi WHERE vi.empleado.secuencia = :secuenciaEmpl");
                queryFinal.setParameter("secuenciaEmpl", secuencia);
                List<VigenciasIndicadores> listaVigenciasIndicadores = queryFinal.getResultList();
                return listaVigenciasIndicadores;
        } catch (Exception e) {
            System.out.println("Error indicadoresTotalesEmpleadoSecuencia : "+e.toString());
            return null;
        }
    }
}
