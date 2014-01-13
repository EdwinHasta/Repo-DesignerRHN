/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.PryClientes;
import InterfacePersistencia.PersistenciaPryClientesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'PryClientes' de la
 * base de datos.
 *
 * @author Viktor
 */
@Stateless
public class PersistenciaPryClientes implements PersistenciaPryClientesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(PryClientes pryClientes) {
        em.persist(pryClientes);
    }

    public void editar(PryClientes pryClientes) {
        em.merge(pryClientes);
    }

    public void borrar(PryClientes pryClientes) {
        em.remove(em.merge(pryClientes));
    }

    public PryClientes buscarPryCliente(BigInteger secuenciaPC) {
        try {
            return em.find(PryClientes.class, secuenciaPC);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PryClientes> buscarPryClientes() {
        try {
            Query query = em.createQuery("SELECT pc FROM PryClientes pc ORDER BY pc.nombre ASC");
            List<PryClientes> pryclientes = query.getResultList();
            return pryclientes;
        } catch (Exception e) {
            return null;
        }
    }

    public BigInteger contadorProyectos(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT(*)FROM  proyectos WHERE pry_cliente = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("persistenciapryclientes contadorProyectos Contador " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error  persistenciapryclientes contadorProyectos. " + e);
            return retorno;
        }
    }
}
