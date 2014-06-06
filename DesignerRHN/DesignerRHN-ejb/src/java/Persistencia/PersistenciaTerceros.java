/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Terceros;
import InterfacePersistencia.PersistenciaTercerosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Terceros' de la base
 * de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTerceros implements PersistenciaTercerosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;
     */
    @Override
    public void crear(EntityManager em, Terceros terceros) {
        try {
            em.persist(terceros);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTerceros");
        }
    }

    @Override
    public void editar(EntityManager em, Terceros terceros) {
        try {
            em.merge(terceros);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTerceros");
        }
    }

    @Override
    public void borrar(EntityManager em, Terceros terceros) {
        try {
            em.remove(em.merge(terceros));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTerceros");
        }
    }

    @Override
    public List<Terceros> todosTerceros(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT t FROM Terceros t ORDER BY t.nombre");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Terceros> terceros = (List<Terceros>) query.getResultList();
            return terceros;
        } catch (Exception e) {
            System.out.println("Error buscarTerceros");
            return null;
        }
    }

    @Override
    public List<Terceros> buscarTerceros(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT t FROM Terceros t, Empresas e, TercerosSucursales ts  WHERE t.secuencia = ts.tercero.secuencia AND t.empresa.secuencia = e.secuencia");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Terceros> terceros = (List<Terceros>) query.getResultList();
            return terceros;
        } catch (Exception e) {
            System.out.println("Error buscarTerceros");
            return null;
        }
    }

    @Override
    public Terceros buscarTercerosSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT t FROM Terceros t WHERE t.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Terceros terceros = (Terceros) query.getSingleResult();
            return terceros;
        } catch (Exception e) {
            System.out.println("Error buscarTercerosSecuencia");
            Terceros terceros = null;
            return terceros;
        }
    }

    @Override
    public boolean verificarTerceroPorNit(EntityManager em, BigInteger nit) {
        try {
            Query query = em.createQuery("SELECT COUNT(t) FROM Terceros t WHERE t.nit = :nit");
            query.setParameter("nit", nit);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }

    @Override
    public boolean verificarTerceroParaEmpresaEmpleado(EntityManager em, BigInteger nit, BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT COUNT(t) FROM Terceros t "
                    + "WHERE t.nit = :nit AND t.empresa.secuencia = :secEmpresa");
            query.setParameter("nit", nit);
            query.setParameter("secEmpresa", secEmpresa);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }

    @Override
    public List<Terceros> lovTerceros(EntityManager em, BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT t FROM Terceros t "
                    + "WHERE t.empresa.secuencia = :secEmpresa ORDER BY t.nombre");
            query.setParameter("secEmpresa", secEmpresa);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Terceros> listaTerceros = query.getResultList();
            return listaTerceros;
        } catch (Exception e) {
            System.out.println("Exepcion lovTerceros: " + e);
            return null;
        }
    }

    @Override
    public List<Terceros> tercerosEmbargos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT t.nit, t.nombre, e.nombre FROM Terceros t, Empresas e Where e.secuencia=t.empresa ORDER BY t.nombre");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Terceros> listaTerceros = query.getResultList();
            return listaTerceros;
        } catch (Exception e) {
            System.out.println("Error PersistenciaEmpleados.tercerosEmbargos" + e);
            return null;
        }
    }

    @Override
    public String buscarCodigoSSPorSecuenciaTercero(EntityManager em, BigInteger secuencia) {
        try {
            String retorno = null;
            Query query = em.createQuery("SELECT t FROM Terceros t WHERE t.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Terceros tercero = (Terceros) query.getSingleResult();
            if (tercero != null) {
                retorno = tercero.getCodigoss();
            }
            return retorno;
        } catch (Exception e) {
            System.out.println("Error buscarCodigoSSPorSecuenciaTercero PersistenciaTerceros : " + e.toString());
            return null;
        }
    }

    @Override
    public String buscarCodigoSPPorSecuenciaTercero(EntityManager em, BigInteger secuencia) {
        try {
            String retorno = null;
            Query query = em.createQuery("SELECT t FROM Terceros t WHERE t.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Terceros tercero = (Terceros) query.getSingleResult();
            if (tercero != null) {
                retorno = tercero.getCodigosp();
            }
            return retorno;
        } catch (Exception e) {
            System.out.println("Error buscarCodigoSPPorSecuenciaTercero PersistenciaTerceros : " + e.toString());
            return null;
        }
    }

    @Override
    public String buscarCodigoSCPorSecuenciaTercero(EntityManager em, BigInteger secuencia) {
        try {
            String retorno = null;
            Query query = em.createQuery("SELECT t FROM Terceros t WHERE t.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Terceros tercero = (Terceros) query.getSingleResult();
            if (tercero != null) {
                retorno = tercero.getCodigosc();
            }
            return retorno;
        } catch (Exception e) {
            System.out.println("Error buscarCodigoSCPorSecuenciaTercero PersistenciaTerceros : " + e.toString());
            return null;
        }
    }
}
