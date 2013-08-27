package Persistencia;

import Entidades.InformacionesAdicionales;
import InterfacePersistencia.PersistenciaInformacionesAdicionalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaInformacionesAdicionales implements PersistenciaInformacionesAdicionalesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Informacion Adicional.
     */
    @Override
    public void crear(InformacionesAdicionales informacionesAdicionales) {
        try {
            em.merge(informacionesAdicionales);
        } catch (Exception e) {
            System.out.println("Error en PersistenciaInformacionesAdicionales.crear: " + e);
        }
    }
    /*
     *Editar Informacion Adicional. 
     */

    @Override
    public void editar(InformacionesAdicionales informacionesAdicionales) {
        em.merge(informacionesAdicionales);
    }

    /*
     *Borrar Informacion Adicional.
     */
    @Override
    public void borrar(InformacionesAdicionales informacionesAdicionales) {
        em.remove(em.merge(informacionesAdicionales));
    }

    /*
     *Encontrar una Informacion Adicional.
     */
    @Override
    public InformacionesAdicionales buscarinformacionAdicional(BigInteger id) {
        return em.find(InformacionesAdicionales.class, id);
    }

    /*
     *Encontrar todas las Informaciones Adicionales.
     */
    @Override
    public List<InformacionesAdicionales> buscarinformacionesAdicionales() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(InformacionesAdicionales.class));
        return em.createQuery(cq).getResultList();
    }

    /*
     * Encontrar Informacion Adicional de una Persona.
     */
    @Override
    public List<InformacionesAdicionales> informacionAdicionalPersona(BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT COUNT(ia) FROM InformacionesAdicionales ia WHERE ia.empleado.secuencia = :secuenciaEmpleado");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT ia FROM InformacionesAdicionales ia WHERE ia.empleado.secuencia = :secuenciaEmpleado and ia.fechainicial = (SELECT MAX(iad.fechainicial) FROM InformacionesAdicionales iad WHERE iad.empleado.secuencia = :secuenciaEmpleado)");
                queryFinal.setParameter("secuenciaEmpleado", secuenciaEmpleado);
                List<InformacionesAdicionales> listaInformacionesAdicionales = queryFinal.getResultList();
                return listaInformacionesAdicionales;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaInformacionesAdicionales.informacionAdicionalPersona" + e);
            return null;
        }
    }
}
