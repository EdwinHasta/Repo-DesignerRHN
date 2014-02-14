/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Operandos;
import InterfacePersistencia.PersistenciaOperandosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Operandos' de la base
 * de datos.
 *
 * @author Andres Pineda.
 */
@Stateless
public class PersistenciaOperandos implements PersistenciaOperandosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<Operandos> buscarOperandos() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Operandos.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarOperandos PersistenciaOperandos : " + e.toString());
            return null;
        }
    }

    @Override
    public void crear(Operandos operandos) {
        try {
            em.merge(operandos);
        } catch (Exception e) {
            System.out.println("El Operandos no exite o esta reservada por lo cual no puede ser modificada (Operandos)");
        }
    }

    @Override
    public void editar(Operandos operandos) {
        try {
            em.merge(operandos);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el Operandos");
        }
    }

    @Override
    public void borrar(Operandos operandos) {
        try {
            em.remove(em.merge(operandos));
        } catch (Exception e) {
            System.out.println("El Operandos no se ha podido eliminar");
        }
    }

    @Override
    public String valores(BigInteger secuenciaOperando) {
        try {
            String valor;
            Query query = em.createNativeQuery("SELECT DECODE(tc.tipo,'C',tc.valorstring,'N',to_char(tc.valorreal),to_char(tc.valordate)) FROM TIPOSCONSTANTES tc WHERE tc.operando=? AND tc.fechainicial=(select max(tci.fechainicial) from tiposconstantes tci WHERE tci.operando= ?)");
            query.setParameter(1, secuenciaOperando);
            query.setParameter(2, secuenciaOperando);
            valor = (String) query.getSingleResult();
            return valor;
        } catch (Exception e) {
            
            return null;
        }
    }
}
