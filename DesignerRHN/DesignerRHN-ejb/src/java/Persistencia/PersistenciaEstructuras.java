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
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Estructuras estructuras) {
        try {
            em.persist(estructuras);
        } catch (Exception e) {
            System.out.println("No es posible crear la estructura");
        }
    }

    @Override
    public void editar(Estructuras estructuras) {
        try {
            em.merge(estructuras);
        } catch (Exception e) {
            System.out.println("La estructura no exite o esta reservada por lo cual no puede ser modificada");
        }
    }

    @Override
    public void borrar(Estructuras estructuras) {
        em.remove(em.merge(estructuras));
    }

    @Override
    public Estructuras buscarEstructura(BigInteger secuencia) {
        try {
            return em.find(Estructuras.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Estructuras> estructuras() {
        try {
            Query query = em.createQuery("SELECT e FROM Estructuras e ORDER BY e.nombre");
            List<Estructuras> estructuras = query.getResultList();
            return estructuras;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Estructuras> buscarEstructurasPorSecuenciaOrganigrama(BigInteger secOrganigrama) {
        try {
            Query query = em.createQuery("SELECT e FROM Estructuras e WHERE e.organigrama.secuencia=:secOrganigrama ORDER BY e.nombre");
            query.setParameter("secOrganigrama", secOrganigrama);
            List<Estructuras> estructuras = query.getResultList();
            return estructuras;
        } catch (Exception e) {
            System.out.println("Error buscarEstructurasPorSecuenciaOrganigrama PersistenciaEstructuras");
            return null;
        }
    }

    @Override
    public List<Estructuras> buscarEstructurasPorOrganigrama(BigInteger secOrganigrama) {
        try {
            Query query = em.createQuery("SELECT e FROM Estructuras e WHERE e.organigrama.secuencia=:secOrganigrama ORDER BY e.codigo ASC");
            query.setParameter("secOrganigrama", secOrganigrama);
            List<Estructuras> estructuras = query.getResultList();
            return estructuras;
        } catch (Exception e) {
            System.out.println("Error buscarEstructurasPorOrganigrama PersistenciaEstructuras : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Estructuras> buscarlistaValores(String fechaVigencia) {
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
    public List<Estructuras> estructuraPadre(BigInteger secOrg) {
        try {
            Query query = em.createQuery("SELECT e FROM Estructuras e WHERE e.organigrama.secuencia = :secOrg AND e.estructurapadre IS NULL");
            query.setParameter("secOrg", secOrg);
            List<Estructuras> listEstructuras = query.getResultList();
            return listEstructuras;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaVWActualesTiposTrabajadores.estructuraPadre" + e);
            return null;
        }
    }

    @Override
    public List<Estructuras> estructurasHijas(BigInteger secEstructuraPadre, Short codigoEmpresa) {
        try {
            Query query = em.createQuery("SELECT e FROM Estructuras e WHERE e.organigrama.empresa.codigo = :codigoEmpresa AND e.estructurapadre.secuencia = :secEstructuraPadre");
            query.setParameter("secEstructuraPadre", secEstructuraPadre);
            query.setParameter("codigoEmpresa", codigoEmpresa);
            List<Estructuras> listEstructuras = query.getResultList();
            return listEstructuras;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaVWActualesTiposTrabajadores.estructurasHijas" + e);
            return null;
        }
    }

    @Override
    public List<Estructuras> buscarEstructuras() {
        try {
            List<Estructuras> estructuras = (List<Estructuras>) em.createNamedQuery("Estructuras.findAll").getResultList();
            return estructuras;
        } catch (Exception e) {
            System.out.println("Error buscarEstructuras PersistenciaEstructuras");
            return null;
        }
    }

    @Override
    public Estructuras buscarEstructuraSecuencia(BigInteger secuencia) {
        Estructuras estructura;
        try {
            Query query = em.createQuery("SELECT e FROM Estructuras e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            estructura = (Estructuras) query.getSingleResult();
            return estructura;
        } catch (Exception e) {
            System.out.println("Error buscarEstructuraSecuencia PersistenciaEstructuras");
            estructura = null;
        }
        return estructura;
    }

    @Override
    public List<Estructuras> buscarEstructurasPadres(BigInteger secOrganigrama, BigInteger secEstructura) {
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

}
