package Persistencia;

import Entidades.Encargaturas;
import InterfacePersistencia.PersistenciaEncargaturasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaEncargaturas implements PersistenciaEncargaturasInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<Encargaturas> reemplazoPersona(BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT COUNT(e) FROM Encargaturas e WHERE e.empleado.secuencia = :secuenciaEmpleado");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT e FROM Encargaturas e WHERE e.empleado.secuencia = :secuenciaEmpleado and e.fechainicial = (SELECT MAX(en.fechainicial) FROM Encargaturas en WHERE en.empleado.secuencia = :secuenciaEmpleado)");
                queryFinal.setParameter("secuenciaEmpleado", secuenciaEmpleado);
                List<Encargaturas> listaEncargaturas = queryFinal.getResultList();
                return listaEncargaturas;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaEncargaturas.reemplazoPersona" + e);
            return null;
        }
    }
}
