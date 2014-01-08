/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.HVHojasDeVida;
import Entidades.HvEntrevistas;
import InterfacePersistencia.PersistenciaHvEntrevistasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'HvEntrevistas'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaHvEntrevistas implements PersistenciaHvEntrevistasInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(HvEntrevistas hvEntrevistas) {
        em.persist(hvEntrevistas);
    }

    public void editar(HvEntrevistas hvEntrevistas) {
        em.merge(hvEntrevistas);
    }

    public void borrar(HvEntrevistas hvEntrevistas) {
        em.remove(em.merge(hvEntrevistas));
    }

    public HvEntrevistas buscarHvEntrevista(BigInteger secuenciaHvEntrevista) {
        try {
            return em.find(HvEntrevistas.class, secuenciaHvEntrevista);
        } catch (Exception e) {
            return null;
        }
    }

    public List<HvEntrevistas> buscarHvEntrevistas() {
        Query query = em.createQuery("SELECT te FROM HvEntrevistas te ORDER BY te.fecha ASC ");
        List<HvEntrevistas> listHvEntrevistas = query.getResultList();
        return listHvEntrevistas;

    }

    public List<HvEntrevistas> buscarHvEntrevistasPorEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT he FROM HVHojasDeVida hv , HvEntrevistas he , Empleados e WHERE hv.secuencia = he.hojadevida.secuencia AND e.persona.secuencia= hv.persona.secuencia AND e.secuencia = :secuenciaEmpl ORDER BY he.fecha ");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<HvEntrevistas> listHvEntrevistas = query.getResultList();
            return listHvEntrevistas;
        } catch (Exception e) {
            System.err.println("Error en PERSISTENCIAHVENTREVISTAS ERROR " + e);
            return null;
        }
    }

    public List<HVHojasDeVida> buscarHvHojaDeVidaPorEmpleado(BigInteger secEmpleado) {
        try {
            System.err.println("secuencia empleado hoja de vida " + secEmpleado);
            Query query = em.createQuery("SELECT hv FROM HVHojasDeVida hv , HvEntrevistas he , Empleados e WHERE hv.secuencia = he.hojadevida.secuencia AND e.persona.secuencia= hv.persona.secuencia AND e.secuencia = :secuenciaEmpl");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<HVHojasDeVida> hvHojasDeVIda = query.getResultList();
            return hvHojasDeVIda;
        } catch (Exception e) {
            System.out.println("Error en Persistencia HvEntrevistas buscarHvHojaDeVidaPorEmpleado " + e);
            return null;
        }
    }    
    
    @Override
    public List<HvEntrevistas> entrevistasPersona(BigInteger secuenciaHV) {
        try {
            Query query = em.createQuery("SELECT COUNT(hve) FROM HvEntrevistas hve WHERE hve.hojadevida.secuencia = :secuenciaHV");
            query.setParameter("secuenciaHV", secuenciaHV);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT hve FROM HvEntrevistas hve WHERE hve.hojadevida.secuencia = :secuenciaHV and hve.fecha = (SELECT MAX(hven.fecha) FROM HvEntrevistas hven WHERE hven.hojadevida.secuencia = :secuenciaHV)");
                queryFinal.setParameter("secuenciaHV", secuenciaHV);
                List<HvEntrevistas> listaHvEntrevistas = queryFinal.getResultList();
                return listaHvEntrevistas;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaHvEntrevistas.entrevistasPersona" + e);
            return null;
        }
    }
}
