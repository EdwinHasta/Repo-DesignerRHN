package Persistencia;

import Entidades.Novedades;
import InterfacePersistencia.PersistenciaNovedadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaNovedades implements PersistenciaNovedadesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public List<Novedades> novedadesParaReversar(BigInteger usuarioBD, String documentoSoporte) {
        try {
            Query query = em.createQuery("SELECT n FROM Novedades n WHERE n.usuarioreporta.secuencia = :usuarioBD AND n.documentosoporte = :documentoSoporte");
            query.setParameter("usuarioBD", usuarioBD);
            query.setParameter("documentoSoporte", documentoSoporte);
            List<Novedades> listNovedades = query.getResultList();
            return listNovedades;
        } catch (Exception e) {
            System.out.println("Error: (novedadesParaReversar)" + e);
            return null;
        }
    }

    public int reversarNovedades(BigInteger usuarioBD, String documentoSoporte) {
        try {
            Query query = em.createQuery("DELETE FROM Novedades n WHERE n.usuarioreporta.secuencia = :usuarioBD AND n.documentosoporte = :documentoSoporte");
            query.setParameter("usuarioBD", usuarioBD);
            query.setParameter("documentoSoporte", documentoSoporte);
            int rows = query.executeUpdate();
            return rows;
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro. (reversarNovedades)" + e);
            return 0;
        }
    }

    /*  @Override
     public List<Novedades> novedadesEmpleado (BigInteger secuenciaEmpleado){
     try {
     Query query = em.createQuery("SELECT n FROM Novedades n WHERE n.empleado.secuencia = :secuenciaEmpleado ORDER BY n.fechainicial");
            
     query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
     List<Novedades> listaNovedades = query.getResultList();
     return listaNovedades;
     } catch (Exception e) {
     System.out.println("Error PersistenciaNovedades.novedadesEmpleado" + e);
     return null;
     }
     }*/
    @Override
    public List<Novedades> novedadesEmpleado(BigInteger secuenciaEmpleado) {
        try {
            System.out.println("Llega: " + secuenciaEmpleado);
            List<Novedades> listaNovedades;
            String sqlQuery = "SELECT * FROM Novedades n WHERE  n.empleado = ? AND n.tipo IN ('FIJA','PAGO POR FUERA','OCASIONAL') and ((n.fechafinal IS NULL AND NVL(SALDO,99999) > 0) OR (n.saldo > 0 and n.fechainicial >= (SELECT fechadesdecausado FROM vwactualesfechas)) OR n.fechafinal > (SELECT fechadesdecausado FROM vwactualesfechas )) ORDER BY n.fechafinal";
            Query query = em.createNativeQuery(sqlQuery, Novedades.class);
            query.setParameter(1, secuenciaEmpleado);
            listaNovedades = query.getResultList();
            return listaNovedades;
        } catch (Exception e) {
            System.out.println("Error PersistenciaNovedades.novedadesEmpleado" + e);
            return null;
        }
    }
}
