package Persistencia;

import Entidades.VigenciasIndicadores;
import InterfacePersistencia.PersistenciaVigenciasIndicadoresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasIndicadores implements PersistenciaVigenciasIndicadoresInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     */
    @Override
    public void crear(VigenciasIndicadores vigenciasIndicadores) {
        try {
            em.persist(vigenciasIndicadores);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaVigenciasIndicadores : " + e.toString());
        }
    }

    /*
     */
    @Override
    public void editar(VigenciasIndicadores vigenciasIndicadores) {
        try {
            em.merge(vigenciasIndicadores);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaVigenciasIndicadores : " + e.toString());
        }
    }

    /*
     */
    @Override
    public void borrar(VigenciasIndicadores vigenciasIndicadores) {
        try {
            em.remove(em.merge(vigenciasIndicadores));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaVigenciasIndicadores : " + e.toString());
        }
    }

    /*
     */
    @Override
    public VigenciasIndicadores buscarVigenciaIndicador(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(VigenciasIndicadores.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaIndicador PersistenciaVigenciasIndicadores : " + e.toString());
            return null;
        }

    }

    /*
     */
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
    public List<VigenciasIndicadores> indicadoresPersona(BigInteger secuenciaEmpl) {
        try {
            Query query = em.createQuery("SELECT COUNT(vi) FROM VigenciasIndicadores vi WHERE vi.empleado.secuencia = :secuenciaEmpl");
            query.setParameter("secuenciaEmpl", secuenciaEmpl);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT vi FROM VigenciasIndicadores vi WHERE vi.empleado.secuencia = :secuenciaEmpl and vi.fechainicial = (SELECT MAX(vin.fechainicial) FROM VigenciasIndicadores vin WHERE vin.empleado.secuencia = :secuenciaEmpl)");
                queryFinal.setParameter("secuenciaEmpl", secuenciaEmpl);
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
    public List<VigenciasIndicadores> indicadoresTotalesEmpleadoSecuencia(BigInteger secuenciaEmpl) {
        try {
            Query queryFinal = em.createQuery("SELECT vi FROM VigenciasIndicadores vi WHERE vi.empleado.secuencia = :secuenciaEmpl");
                queryFinal.setParameter("secuenciaEmpl", secuenciaEmpl);
                List<VigenciasIndicadores> listaVigenciasIndicadores = queryFinal.getResultList();
                return listaVigenciasIndicadores;
        } catch (Exception e) {
            System.out.println("Error indicadoresTotalesEmpleadoSecuencia : "+e.toString());
            return null;
        }
    }
}
