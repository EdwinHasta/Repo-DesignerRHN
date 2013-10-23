package Persistencia;

import Entidades.Periodicidades;
import InterfacePersistencia.PersistenciaPeriodicidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

@Stateless
public class PersistenciaPeriodicidades implements PersistenciaPeriodicidadesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Periodicidades periodicidades) {
        em.persist(periodicidades);
    }

    @Override
    public void editar(Periodicidades periodicidades) {
        em.merge(periodicidades);
    }

    @Override
    public void borrar(Periodicidades periodicidades) {
        em.remove(em.merge(periodicidades));
    }

    @Override
    public boolean verificarCodigoPeriodicidad(BigInteger codigoPeriodicidad) {
        try {
            Query query = em.createQuery("SELECT COUNT(p) FROM Periodicidades p WHERE p.codigo = :codigo");
            query.setParameter("codigo", codigoPeriodicidad);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }

    @Override
    public Periodicidades buscarPeriodicidades(BigInteger secuencia) {
        try {
            //BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(Periodicidades.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la persistencia vigencias formas pagos ERROR : " + e);
            return null;
        }
    }

    @Override
    public List<Periodicidades> buscarPeriodicidades() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Periodicidades.class));
        return em.createQuery(cq).getResultList();
    }
}
