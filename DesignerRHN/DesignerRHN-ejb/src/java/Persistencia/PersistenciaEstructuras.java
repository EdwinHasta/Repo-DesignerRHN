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

@Stateless
public class PersistenciaEstructuras implements PersistenciaEstructurasInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear estructura.
     */
    @Override
    public void crear(Estructuras estructuras) {
        try {
            em.persist(estructuras);
        } catch (Exception e) {
            System.out.println("No es posible crear la estructura");
        }
    }

    /*
     *Editar estructura. 
     */
    @Override
    public void editar(Estructuras estructuras) {
        try {
            em.merge(estructuras);
        } catch (Exception e) {
            System.out.println("La estructura no exite o esta reservada por lo cual no puede ser modificada");
        }
    }

    /*
     *Borrar estructura.
     */
    @Override
    public void borrar(Estructuras estructuras) {
        em.remove(em.merge(estructuras));
    }

    /*
     *Encontrar una estructura. 
     */
    @Override
    public Estructuras buscarEstructura(Object id) {
        try {
            return em.find(Estructuras.class, id);
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
    public List<Estructuras> buscarEstructurasPorOrganigrama(BigInteger secOrganigrama) {
        try {
            System.out.println("PersistenciaEstructuras: Empieza busqueda Estructuras por organigrama");
            List<Estructuras> estructuras = (List<Estructuras>) em.createNamedQuery("Estructuras.findBySecOrganigrama").setParameter("secOrganigrama", secOrganigrama).getResultList();
            System.out.println("PersistenciaEstructuras: Finaliza busqueda Estructuras por organigrama");
            return estructuras;
        } catch (Exception e) {
            System.out.println("PersistenciaEstructuras: Fallo en la busqueda de las estructuras por organigrama");
            return null;
        }
    }

    @Override
    public List<Estructuras> buscarlistaValores(String fechaVigencia) {


        List<Estructuras> estructuras; //= new ArrayList<Estructuras>();
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

    public List<Estructuras> estructurasHijas(BigInteger secEstructuraPadre) {
        try {
            Query query = em.createQuery("SELECT e FROM Estructuras e WHERE e.organigrama.empresa.codigo = 1 AND e.estructurapadre.secuencia = :secEstructuraPadre");
            query.setParameter("secEstructuraPadre", secEstructuraPadre);
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
    
   
}