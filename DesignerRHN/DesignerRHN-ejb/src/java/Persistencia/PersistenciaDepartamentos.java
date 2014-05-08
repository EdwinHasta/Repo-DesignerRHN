/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Departamentos;
import InterfacePersistencia.PersistenciaDepartamentosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Departamentos' de la
 * base de datos
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaDepartamentos implements PersistenciaDepartamentosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    public void crear(EntityManager em,Departamentos departamentos) {
        try {
            em.persist(departamentos);
        } catch (Exception e) {
            System.out.println("Error creando Departamentos PersistenciaDepartamentos " + e);
        }
    }

    public void editar(EntityManager em,Departamentos departamentos) {
        try {
            em.merge(departamentos);
        } catch (Exception e) {
            System.out.println("Error editando Departamentos PersistenciaDepartamentos " + e);
        }
    }

    public void borrar(EntityManager em,Departamentos departamentos) {
        try {
            em.remove(em.merge(departamentos));
        } catch (Exception e) {
            System.out.println("Error borrando Departamentos PersistenciaDepartamentos " + e);
        }
    }

    public Departamentos consultarDepartamento(EntityManager em,BigInteger secuencia) {
        try {
            return em.find(Departamentos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarDeporte PersistenciaDepartamentos : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Departamentos> consultarDepartamentos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT d FROM Departamentos d ORDER BY d.nombre");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Departamentos> departamentos = query.getResultList();
            return departamentos;
        } catch (Exception e) {
            return null;
        }
    }

    public BigInteger contarSoAccidentesMedicosDepartamento(EntityManager em,BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "select COUNT(*)from soaccidentesmedicos WHERE departamento = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PersistenciaDepartamentos contarSoAccidentesMedicosDepartamento = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PersistenciaDepartamentos contarSoAccidentesMedicosDepartamento  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

    public BigInteger contarCiudadesDepartamento(EntityManager em,BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*)FROM ciudades WHERE departamento = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PersistenciaDepartamentos contarCiudadesDepartamento = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PersistenciaDepartamentos contarCiudadesDepartamento  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

    public BigInteger contarCapModulosDepartamento(EntityManager em,BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*)FROM capmodulos WHERE departamento = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PersistenciaDepartamentos contarCapModulosDepartamento = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PersistenciaDepartamentos contarCapModulosDepartamento  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }

    }

    public BigInteger contarBienProgramacionesDepartamento(EntityManager em,BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*)FROM bienprogramaciones WHERE departamento = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PersistenciaDepartamentos contarBienProgramacionesDepartamento = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PersistenciaDepartamentos contarBienProgramacionesDepartamento  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
