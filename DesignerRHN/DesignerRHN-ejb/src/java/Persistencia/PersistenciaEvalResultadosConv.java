package Persistencia;

import Entidades.EvalResultadosConv;
import InterfacePersistencia.PersistenciaEvalResultadosConvInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaEvalResultadosConv implements PersistenciaEvalResultadosConvInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<EvalResultadosConv> pruebasAplicadasPersona(BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT COUNT(er) FROM EvalResultadosConv er WHERE er.empleado.secuencia = :secuenciaEmpleado");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT er FROM EvalResultadosConv er WHERE er.empleado.secuencia = :secuenciaEmpleado and er.fechaperiododesde = (SELECT MAX(ere.fechaperiododesde) FROM EvalResultadosConv ere WHERE ere.empleado.secuencia = :secuenciaEmpleado)");
                queryFinal.setParameter("secuenciaEmpleado", secuenciaEmpleado);
                List<EvalResultadosConv> listaPruebasAplicadas = queryFinal.getResultList();
                return listaPruebasAplicadas;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaEvalResultadosConv.pruebasAplicadasPersona" + e);
            return null;
        }
    }
}
