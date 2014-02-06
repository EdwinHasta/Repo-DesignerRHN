/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Recordatorios;
import InterfacePersistencia.PersistenciaRecordatoriosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless. <br> 
 * Clase encargada de realizar operaciones sobre la tabla 'Recordatorios'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaRecordatorios implements PersistenciaRecordatoriosInterface {

    
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
    public Recordatorios recordatorioRandom(EntityManager entity) {
        try {
            String consulta = "SELECT *\n"
                    + "    FROM (SELECT * FROM  RECORDATORIOS R\n"
                    + "    WHERE TIPO = 'PROVERBIO' \n"
                    + "    ORDER BY dbms_random.value)ra\n"
                    + "    WHERE ROWNUM=1";
            Query query = entity.createNativeQuery(consulta, Recordatorios.class);
            Recordatorios recordatorio = (Recordatorios) query.getSingleResult();
            return recordatorio;
        } catch (Exception e) {
            System.out.println("Error: PersistenciaRecordatorios.recordatorioRandom");
            return null;
        }
    }

    @Override
    public List<String> recordatoriosInicio(EntityManager entity) {
        try {
            String consulta = "SELECT R.MENSAJE FROM RECORDATORIOS R WHERE R.TIPO='RECORDATORIO' "
                    + "AND (R.DIA=0 OR R.DIA=TO_NUMBER(TO_CHAR(SYSDATE,'DD'))) AND (R.MES=0 "
                    + "OR R.MES=TO_NUMBER(TO_CHAR(SYSDATE,'MM'))) AND (R.ANO=0 "
                    + "OR R.ANO=TO_NUMBER(TO_CHAR(SYSDATE,'YYYY'))) "
                    + "AND (R.USUARIO=(SELECT U.SECUENCIA FROM USUARIOS U "
                    + "WHERE U.ALIAS=USER) OR R.USUARIO IS NULL)";
            Query query = entity.createNativeQuery(consulta);
            List<String> listaRecordatorios = query.getResultList();
            return listaRecordatorios;
        } catch (Exception e) {
            System.out.println("Error: PersistenciaRecordatorios.recordatoriosInicio");
            return null;
        }
    }

    @Override
    public List<Recordatorios> consultasInicio(EntityManager entity) {
        try {
            String consulta = "SELECT * FROM RECORDATORIOS R WHERE R.TIPO='CONSULTA' "
                    + "AND (R.DIA=0 OR R.DIA=TO_NUMBER(TO_CHAR(SYSDATE,'DD'))) AND (R.MES=0 "
                    + "OR R.MES=TO_NUMBER(TO_CHAR(SYSDATE,'MM'))) AND (R.ANO=0 "
                    + "OR R.ANO=TO_NUMBER(TO_CHAR(SYSDATE,'YYYY'))) "
                    + "AND (R.USUARIO=(SELECT U.SECUENCIA FROM USUARIOS U "
                    + "WHERE U.ALIAS=USER) OR R.USUARIO IS NULL)";
            Query query = entity.createNativeQuery(consulta, Recordatorios.class);
            List<Recordatorios> listaConsultas = query.getResultList();
            return listaConsultas;
        } catch (Exception e) {
            System.out.println("Error: PersistenciaRecordatorios.consultasInicio");
            return null;
        }
    }
    
    @Override
    public List<Recordatorios> proverbiosRecordatorios() {
        try {
            Query query = em.createQuery("SELECT r FROM Recordatorios r WHERE r.tipo = 'PROVERBIO'");
            List<Recordatorios> recordatorios = query.getResultList();
            return recordatorios;
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<Recordatorios> mensajesRecordatorios() {
        try {
            String consulta = "SELECT * FROM RECORDATORIOS R WHERE R.TIPO='RECORDATORIO'and usuario =(SELECT U.SECUENCIA FROM USUARIOS U "
                    + "WHERE U.ALIAS=USER)";
            Query query = em.createNativeQuery(consulta, Recordatorios.class);
            List<Recordatorios> listaConsultas = query.getResultList();
            return listaConsultas;
        } catch (Exception e) {
            System.out.println("Error: PersistenciaRecordatorios.mensajesRecordatorios");
            return null;
        }
    }
    
     @Override
    public void crear(Recordatorios recordatorios) {
        try {
            em.persist(recordatorios);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaRecordatorios");
        }
    }

    @Override
    public void editar(Recordatorios recordatorios) {
        try {
            em.merge(recordatorios);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaRecordatorios");
        }
    }

    @Override
    public void borrar(Recordatorios recordatorios) {
        try {
            em.remove(em.merge(recordatorios));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaRecordatorios");
        }
    }
    
}
