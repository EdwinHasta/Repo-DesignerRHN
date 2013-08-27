package Persistencia;

import Entidades.Organigramas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import InterfacePersistencia.PersistenciaOrganigramasInterface;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Query;

@Stateless
public class PersistenciaOrganigramas implements PersistenciaOrganigramasInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear organigrama.
     */
    @Override
    public void crear(Organigramas organigramas) {
        try {
            em.persist(organigramas);
        } catch (Exception e) {
            System.out.println("No es posible crear el organigrama");
        }
    }

    /*
     *Editar organigrama. 
     */
    @Override
    public void editar(Organigramas organigramas) {
        try {
            em.merge(organigramas);
        } catch (Exception e) {
            System.out.println("El organigrama no exite o esta reservada por lo cual no puede ser modificada");
        }
    }

    /*
     *Borrar organigrama.
     */
    @Override
    public void borrar(Organigramas organigramas) {
        em.remove(em.merge(organigramas));
    }

    /*
     *Encontrar un organigrama. 
     */
    @Override
    public Organigramas buscarOrganigrama(Object id) {
        try {
            return em.find(Organigramas.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     *Encontrar todos los organigramas. 
     */
    @Override
    public List<Organigramas> buscarOrganigramas() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Organigramas.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Organigramas buscarOrganigramaToLOV(BigInteger secEmpresa, Date fechaVigencia) {
        try {
            Date fecha = (Date) em.createNamedQuery("Organigramas.findToLOV").setParameter("fecha", fechaVigencia).setParameter("secuenciaEmpresa", secEmpresa).getSingleResult();
            System.out.println("PersistenciaOrganigramas: Se recupera la maxima fecha de los organigramas");
            Organigramas organigrama = (Organigramas) em.createNamedQuery("Organigramas.findByFecha").setParameter("fecha", fecha).setParameter("secuenciaEmpresa", secEmpresa).getSingleResult();
            System.out.println("PersistenciaOrganigramas: Se recupera los organigramas segun la fecha y la empresa");
            return organigrama;
        } catch (Exception e) {
            System.out.println("PersistenciaOrganigramas: Fallo en la busqueda de los organigramas por fecha y por empresa ");
            return null;
        }
    }

    @Override
    public Organigramas buscarOrganigramaToLOVRapido(BigInteger secEmpresaCentroCostoEstructura, Date fechaVigencia) {
        try {
            Date fechaPrimeraConsulta = (Date) em.createNamedQuery("Organigramas.findFechaMaxima").setParameter("fechaVigencia", fechaVigencia).setParameter("secEmpresaCentroCostoEstructura", secEmpresaCentroCostoEstructura).getSingleResult();
            System.out.println("PersistenciaOrganigramas rapida: Se recupera la maxima fecha de los organigramas");
            Organigramas organigrama = (Organigramas) em.createNamedQuery("Organigramas.findByFechaYEmpresa").setParameter("fechaPrimeraConsulta", fechaPrimeraConsulta).setParameter("secEmpresaCentroCostoEstructura", secEmpresaCentroCostoEstructura).getSingleResult();
            System.out.println("PersistenciaOrganigramas rapida: Se recupera los organigramas segun la fecha y la empresa");
            return organigrama;
        } catch (Exception e) {
            System.out.println("PersistenciaOrganigramas rapida: Fallo en la busqueda de los organigramas por fecha y por empresa ");
            return null;
        }
    }

    @Override
    public Organigramas organigramaBaseArbol(short codigoOrg) {
        try {
            Organigramas organigrama = null;
            Query query = em.createQuery("SELECT COUNT(o) FROM Organigramas o WHERE o.codigo = :codigoOrg");
            query.setParameter("codigoOrg", codigoOrg);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query query2 = em.createQuery("SELECT o FROM Organigramas o WHERE o.codigo = :codigoOrg");
                query2.setParameter("codigoOrg", codigoOrg);
                organigrama = (Organigramas) query2.getSingleResult();
            }
            return organigrama;
        } catch (Exception e) {
            System.out.println("Exepcion en organigramaBaseArbol " + e);
            return null;
        }
    }
}
