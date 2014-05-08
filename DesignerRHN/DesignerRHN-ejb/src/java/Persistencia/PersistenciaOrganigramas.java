/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Organigramas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import InterfacePersistencia.PersistenciaOrganigramasInterface;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Query;
/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Organigramas'
 * de la base de datos.
 * @author Hugo David Sin Gutiérrez.
 */
@Stateless
public class PersistenciaOrganigramas implements PersistenciaOrganigramasInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, Organigramas organigramas) {
        try {
            em.persist(organigramas);
        } catch (Exception e) {
            System.out.println("No es posible crear el organigrama");
        }
    }

    @Override
    public void editar(EntityManager em, Organigramas organigramas) {
        try {
            em.merge(organigramas);
        } catch (Exception e) {
            System.out.println("El organigrama no exite o esta reservada por lo cual no puede ser modificada");
        }
    }

    @Override
    public void borrar(EntityManager em, Organigramas organigramas) {
        em.remove(em.merge(organigramas));
    }

    @Override
    public Organigramas buscarOrganigrama(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(Organigramas.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Organigramas> buscarOrganigramas(EntityManager em) {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Organigramas.class));
        return em.createQuery(cq).getResultList();
    }
    
    @Override
    public List<Organigramas> buscarOrganigramasVigentes(EntityManager em, BigInteger secEmpresa, Date fechaVigencia) {
        try {
            SimpleDateFormat formatoFecha=new SimpleDateFormat("dd/MM/yyyy");
            String fecha = formatoFecha.format(fechaVigencia);
            Query query = em.createQuery("SELECT o FROM Organigramas o WHERE o.empresa.secuencia = :secEmpresa AND o.fecha >= TO_DATE(:fechaVigencia,'dd/MM/yyyy')");
            query.setParameter("secEmpresa", secEmpresa);
            query.setParameter("fechaVigencia", fecha);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Organigramas> listOrganigramas = query.getResultList();
            return listOrganigramas;
        } catch (Exception e) {
            System.out.println("PersistenciaOrganigramas: Fallo en la busqueda de los organigramas por fecha y por empresa ");
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Organigramas organigramaBaseArbol(EntityManager em, short codigoOrg) {
        try {
            Organigramas organigrama = null;
            Query query = em.createQuery("SELECT COUNT(o) FROM Organigramas o WHERE o.codigo = :codigoOrg");
            query.setParameter("codigoOrg", codigoOrg);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query query2 = em.createQuery("SELECT o FROM Organigramas o WHERE o.codigo = :codigoOrg");
                query2.setParameter("codigoOrg", codigoOrg);
                query2.setHint("javax.persistence.cache.storeMode", "REFRESH");
                organigrama = (Organigramas) query2.getSingleResult();
            }
            return organigrama;
        } catch (Exception e) {
            System.out.println("Exepcion en organigramaBaseArbol " + e);
            return null;
        }
    }
}
