/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Enfermedades;
import InterfacePersistencia.PersistenciaEnfermedadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless Clase encargada de realizar operaciones sobre la tabla
 * 'Enfermedades' de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaEnfermedades implements PersistenciaEnfermedadesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Enfermedades enfermedades) {
        em.persist(enfermedades);
    }

    @Override
    public void editar(Enfermedades enfermedades) {
        em.merge(enfermedades);
    }

    @Override
    public void borrar(Enfermedades enfermedades) {
        em.remove(em.merge(enfermedades));
    }

    @Override
    public Enfermedades buscarEnfermedad(BigInteger secuencia) {
        try {
            return em.find(Enfermedades.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la persistenciaEnfermedadesERROR : " + e);
            return null;
        }
    }

    @Override
    public List<Enfermedades> buscarEnfermedades() {
        try {
            Query query = em.createQuery("SELECT e FROM Enfermedades e ORDER BY e.codigo DESC");
            List<Enfermedades> enfermedades = query.getResultList();
            return enfermedades;
        } catch (Exception e) {
            System.out.println("Error en PersistenciaEnfermedadesProfesionales Por Empleados ERROR" + e);
            return null;
        }
    }

    public BigInteger contadorAusentimos(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*) FROM ausentismos WHERE enfermedad = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAENFERMEDADES contadorAusentimos = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAENFERMEDADES contadorAusentimos  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

    public BigInteger contadorDetallesLicencias(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*) FROM detalleslicencias WHERE enfermedad = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAENFERMEDADES contadorDetallesLicencias = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAENFERMEDADES contadorDetallesLicencias  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

    public BigInteger contadorEnfermedadesPadecidas(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*) FROM enfermedadespadecidas WHERE enfermedad= ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAENFERMEDADES contadorEnfermedadesPadecidas = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAENFERMEDADES contadorEnfermedadesPadecidas  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

    public BigInteger contadorSoausentismos(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*) FROM soausentismos e  WHERE enfermedad = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAENFERMEDADES contadorSoausentismos = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAENFERMEDADES contadorSoausentismos  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

    public BigInteger contadorSorevisionessSistemas(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*) FROM sorevisionessistemas e WHERE enfermedadactual = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAENFERMEDADES contadorSorevisionessSistemas = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAENFERMEDADES contadorSorevisionessSistemas  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
