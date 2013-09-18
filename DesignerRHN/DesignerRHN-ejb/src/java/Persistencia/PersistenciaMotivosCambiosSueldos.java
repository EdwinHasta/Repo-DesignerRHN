package Persistencia;

import Entidades.MotivosCambiosSueldos;
import InterfacePersistencia.PersistenciaMotivosCambiosSueldosInterface;
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
public class PersistenciaMotivosCambiosSueldos implements PersistenciaMotivosCambiosSueldosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(MotivosCambiosSueldos motivosCambiosSueldos) {
        try {
            em.persist(motivosCambiosSueldos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaMotivosCambiosSueldos");
        }
    }

    @Override
    public void editar(MotivosCambiosSueldos motivosCambiosSueldos) {
        try {
            em.merge(motivosCambiosSueldos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaMotivosCambiosSueldos");
        }
    }

    @Override
    public void borrar(MotivosCambiosSueldos motivosCambiosSueldos) {
        try {
            em.remove(em.merge(motivosCambiosSueldos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaMotivosCambiosSueldos");
        }
    }

    @Override
    public MotivosCambiosSueldos buscarMotivoCambioSueldo(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(MotivosCambiosSueldos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarMotivoCambioSueldo PersistenciaMotivosCambiosSueldos");
            return null;
        }

    }

    @Override
    public List<MotivosCambiosSueldos> buscarMotivosCambiosSueldos() {
        try {
            List<MotivosCambiosSueldos> motivosCambiosSueldos = (List<MotivosCambiosSueldos>) em.createNamedQuery("MotivosCambiosSueldos.findAll").getResultList();
            return motivosCambiosSueldos;
        } catch (Exception e) {
            System.out.println("Error buscarMotivosCambiosSueldos PersistenciaMotivoCambioSueldo : "+e.toString());
            return null;
        }
    }

    @Override
    public MotivosCambiosSueldos buscarMotivoCambioSueldoSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT mcs FROM MotivosCambiosSueldos mcs WHERE mcs.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            MotivosCambiosSueldos motivosCambiosSueldos = (MotivosCambiosSueldos) query.getSingleResult();
            return motivosCambiosSueldos;
        } catch (Exception e) {
            System.out.println("Error buscarMotivoCambioSueldoSecuencia");
            MotivosCambiosSueldos motivosCambiosSueldos = null;
            return motivosCambiosSueldos;
        }

    }
}
