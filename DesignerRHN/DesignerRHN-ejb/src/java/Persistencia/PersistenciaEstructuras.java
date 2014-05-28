/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Estructuras;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import InterfacePersistencia.PersistenciaEstructurasInterface;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Estructuras' de la
 * base de datos.
 *
 * @author Hugo David Sin Gutiérrez
 * @author Felipe Triviño
 */
@Stateless
public class PersistenciaEstructuras implements PersistenciaEstructurasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /* @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    @Override
    public void crear(EntityManager em, Estructuras estructuras) {
        try {
            em.persist(estructuras);
        } catch (Exception e) {
            System.out.println("No es posible crear la estructura");
        }
    }

    @Override
    public void editar(EntityManager em, Estructuras estructuras) {
        try {
            em.merge(estructuras);
        } catch (Exception e) {
            System.out.println("La estructura no exite o esta reservada por lo cual no puede ser modificada");
        }
    }

    @Override
    public void borrar(EntityManager em, Estructuras estructuras) {
        em.remove(em.merge(estructuras));
    }

    @Override
    public Estructuras buscarEstructura(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(Estructuras.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Estructuras> estructuras(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT e FROM Estructuras e ORDER BY e.nombre");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Estructuras> estructuras = query.getResultList();
            return estructuras;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Estructuras> buscarEstructurasPorSecuenciaOrganigrama(EntityManager em, BigInteger secOrganigrama) {
        try {
            Query query = em.createQuery("SELECT e FROM Estructuras e WHERE e.organigrama.secuencia=:secOrganigrama ORDER BY e.nombre");
            query.setParameter("secOrganigrama", secOrganigrama);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Estructuras> estructuras = query.getResultList();
            return estructuras;
        } catch (Exception e) {
            System.out.println("Error buscarEstructurasPorSecuenciaOrganigrama PersistenciaEstructuras");
            return null;
        }
    }

    @Override
    public List<Estructuras> buscarEstructurasPorOrganigrama(EntityManager em, BigInteger secOrganigrama) {
        try {
            Query query = em.createQuery("SELECT e FROM Estructuras e WHERE e.organigrama.secuencia=:secOrganigrama ORDER BY e.codigo ASC");
            query.setParameter("secOrganigrama", secOrganigrama);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Estructuras> estructuras = query.getResultList();
            return estructuras;
        } catch (Exception e) {
            System.out.println("Error buscarEstructurasPorOrganigrama PersistenciaEstructuras : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Estructuras> buscarlistaValores(EntityManager em, String fechaVigencia) {
        List<Estructuras> estructuras;
        System.out.println("Fecha: " + fechaVigencia);
        try {
            String sqlQuery = "SELECT  es.* FROM ESTRUCTURAS es, centroscostos cc, empresas emp, organigramas org WHERE es.centrocosto = cc.secuencia and es.organigrama = org.secuencia and org.empresa=emp.secuencia and emp.secuencia=cc.empresa and nvl(cc.obsoleto,'N')='N' and es.organigrama IN (select o.secuencia from organigramas o, empresas em where fecha = (select max(fecha) from organigramas, empresas e where e.secuencia =  organigramas.empresa and  o.secuencia=organigramas.secuencia and organigramas.secuencia = es.organigrama and fecha <= To_date(?, 'dd/mm/yyyy') and o.empresa=em.secuencia))";
            if (fechaVigencia != null) {
                Query query = em.createNativeQuery(sqlQuery, Estructuras.class).setParameter(1, fechaVigencia);
                estructuras = (List<Estructuras>) query.getResultList();
            } else {
                Date fecha = new Date();
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                String forFecha = formatoFecha.format(fecha);
                Query query = em.createNativeQuery(sqlQuery, Estructuras.class).setParameter(1, forFecha);
                estructuras = query.getResultList();
            }
            return estructuras;
        } catch (Exception ex) {
            System.out.println("PersistenciaEstructuras: Fallo el nativeQuery");
            estructuras = null;
            return estructuras;
        }
    }

    @Override
    public List<Estructuras> estructuraPadre(EntityManager em, BigInteger secOrg) {
        try {
            Query query = em.createQuery("SELECT e FROM Estructuras e WHERE e.organigrama.secuencia = :secOrg AND e.estructurapadre IS NULL");
            query.setParameter("secOrg", secOrg);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Estructuras> listEstructuras = query.getResultList();
            return listEstructuras;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaVWActualesTiposTrabajadores.estructuraPadre" + e);
            return null;
        }
    }

    @Override
    public List<Estructuras> estructurasHijas(EntityManager em, BigInteger secEstructuraPadre, Short codigoEmpresa) {
        try {
            Query query = em.createQuery("SELECT e FROM Estructuras e WHERE e.organigrama.empresa.codigo = :codigoEmpresa AND e.estructurapadre.secuencia = :secEstructuraPadre");
            query.setParameter("secEstructuraPadre", secEstructuraPadre);
            query.setParameter("codigoEmpresa", codigoEmpresa);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Estructuras> listEstructuras = query.getResultList();
            return listEstructuras;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaVWActualesTiposTrabajadores.estructurasHijas" + e);
            return null;
        }
    }

    @Override
    public List<Estructuras> buscarEstructuras(EntityManager em) {
        try {
            Query query = em.createNamedQuery("Estructuras.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Estructuras> estructuras = (List<Estructuras>) query.getResultList();

            return estructuras;
        } catch (Exception e) {
            System.out.println("Error buscarEstructuras PersistenciaEstructuras");
            return null;
        }
    }

    @Override
    public Estructuras buscarEstructuraSecuencia(EntityManager em, BigInteger secuencia) {
        Estructuras estructura;
        try {
            Query query = em.createQuery("SELECT e FROM Estructuras e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            estructura = (Estructuras) query.getSingleResult();
            return estructura;
        } catch (Exception e) {
            System.out.println("Error buscarEstructuraSecuencia PersistenciaEstructuras");
            estructura = null;
        }
        return estructura;
    }

    @Override
    public List<Estructuras> buscarEstructurasPadres(EntityManager em, BigInteger secOrganigrama, BigInteger secEstructura) {
        try {
            String strQuery = "SELECT * FROM Estructuras WHERE organigrama =? AND secuencia != NVL(?,0) ORDER BY nombre ASC";
            Query query = em.createNativeQuery(strQuery, Estructuras.class);
            query.setParameter(1, secOrganigrama);
            query.setParameter(2, secEstructura);
            List<Estructuras> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error buscarEstructurasPadres PersistenciaEstructuras : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Estructuras> buscarEstructurasPorEmpresaFechaIngreso(EntityManager em, BigInteger secEmpresa, Date fechaIngreso) {
        List<Estructuras> estructura;
        try {
            String queryStr = "SELECT  est.* FROM ESTRUCTURAS est, organigramas org, centroscostos cc WHERE org.secuencia = est.organigrama and est.centrocosto=cc.secuencia and nvl(cc.obsoleto,'N')='N' and org.empresa = ? and exists (select secuencia from organigramas o where fecha = (select max(fecha) from organigramas , empresas e where e.secuencia = organigramas.empresa and fecha <= ? and organigramas.secuencia = est.organigrama)) ORDER BY est.codigo";
            Query query = em.createNativeQuery(queryStr, Estructuras.class);
            query.setParameter(1, secEmpresa);
            query.setParameter(2, fechaIngreso);
            estructura = query.getResultList();
            return estructura;
        } catch (Exception e) {
            System.out.println("Error buscarEstructurasPorEmpresaFechaIngreso PersistenciaEstructuras : " + e.toString());
            estructura = null;
        }
        return estructura;
    }

    @Override
    public List<Estructuras> buscarEstructurasPorEmpresa(EntityManager em, BigInteger secEmpresa) {
        List<Estructuras> estructura;
        try {
            String queryStr = "SELECT V.* FROM ESTRUCTURAS V, CENTROSCOSTOS CC,empresas e WHERE V.CENTROCOSTO = CC.SECUENCIA and cc.empresa = e.secuencia and e.secuencia = ? and nvl(cc.obsoleto,'N')='N' ORDER BY V.codigo";
            Query query = em.createNativeQuery(queryStr, Estructuras.class);
            query.setParameter(1, secEmpresa);
            estructura = query.getResultList();
            return estructura;
        } catch (Exception e) {
            System.out.println("Error buscarEstructurasPorEmpresa PersistenciaEstructuras : " + e.toString());
            estructura = null;
        }
        return estructura;
    }

}
