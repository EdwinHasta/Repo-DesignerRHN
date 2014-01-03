/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaMotivosPrestamosInterface;
import Entidades.MotivosPrestamos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'MotivosPrestamos'
 * de la base de datos.
 * @author John Pineda.
 */
@Stateless
public class PersistenciaMotivosPrestamos implements PersistenciaMotivosPrestamosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
    public void crear(MotivosPrestamos motivosPrestamos) {
        em.persist(motivosPrestamos);
    }

    @Override
    public void editar(MotivosPrestamos motivosPrestamos) {
        em.merge(motivosPrestamos);
    }

    @Override
    public void borrar(MotivosPrestamos motivosPrestamos) {
        try {
            em.remove(em.merge(motivosPrestamos));
        } catch (Exception e) {
            System.err.println("Error borrando TiposDias");
            System.out.println(e);
        }
    }

    @Override
    public MotivosPrestamos buscarMotivoPrestamo(BigInteger secuencia) {
        try {
            return em.find(MotivosPrestamos.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<MotivosPrestamos> buscarMotivosPrestamos() {
        Query query = em.createQuery("SELECT m FROM MotivosPrestamos m ORDER BY m.codigo ASC");
        List<MotivosPrestamos> listaMotivosPrestamos = query.getResultList();
        return listaMotivosPrestamos;
    }

    @Override
    public BigInteger contadorEersPrestamos(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*)FROM eersprestamos eer WHERE eer.motivoprestamo = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAMOTIVOSPRESTAMOS CONTADOREERSPRESTAMOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAMOTIVOSPRESTAMOS CONTADOREERSPRESTAMOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
