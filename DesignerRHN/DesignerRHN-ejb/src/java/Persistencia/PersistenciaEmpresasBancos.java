/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.EmpresasBancos;
import InterfacePersistencia.PersistenciaEmpresasBancosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaEmpresasBancos implements PersistenciaEmpresasBancosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(EmpresasBancos empresasBancos) {
        try {
            System.out.println("PERSISTENCIAEMPRESASBANCOS  EMPRESA " + empresasBancos.getEmpresa().getNombre());
            System.out.println("PERSISTENCIAEMPRESASBANCOS  BANCO " + empresasBancos.getBanco().getNombre());
            System.out.println("PERSISTENCIAEMPRESASBANCOS  CIUDAD " + empresasBancos.getCiudad().getNombre());
            System.out.println("PERSISTENCIAEMPRESASBANCOS  NUMERO CUENTA " + empresasBancos.getNumerocuenta());
            System.out.println("PERSISTENCIAEMPRESASBANCOS  TIPOCUENCITA " + empresasBancos.getTipocuenta());
            if (empresasBancos.getTipocuenta() == null) {
                System.out.println("PERSISTENCIA TIPO CUENTA ES NULO");
            } else {
                if (empresasBancos.getTipocuenta().isEmpty() || empresasBancos.getTipocuenta().equals("") || empresasBancos.getTipocuenta().equals(" ")) {
                    empresasBancos.setTipocuenta(null);
                }
            }
            em.persist(empresasBancos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaEmpresasBancos : " + e.toString());
        }
    }

    public void editar(EmpresasBancos empresasBancos) {
        try {
            if (empresasBancos.getTipocuenta() == null) {
                System.out.println("PERSISTENCIA TIPO CUENTA ES NULO");
            } else {
                if (empresasBancos.getTipocuenta().isEmpty() || empresasBancos.getTipocuenta().equals("") || empresasBancos.getTipocuenta().equals(" ")) {
                    empresasBancos.setTipocuenta(null);
                }
            }
            em.merge(empresasBancos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaEmpresasBancos : " + e.toString());
        }
    }

    public void borrar(EmpresasBancos empresasBancos) {
        try {
            em.remove(em.merge(empresasBancos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaEmpresasBancos : " + e.toString());
        }
    }

    public List<EmpresasBancos> consultarEmpresasBancos() {
        try {
            Query query = em.createQuery("SELECT g FROM EmpresasBancos g ");
            List< EmpresasBancos> listMotivosDemandas = query.getResultList();
            return listMotivosDemandas;

        } catch (Exception e) {
            System.out.println("Error consultarEmpresasBancos PersistenciaEmpresasBancos : " + e.toString());
            return null;
        }
    }

    public EmpresasBancos consultarFirmaReporte(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT te FROM EmpresasBancos te WHERE te.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            EmpresasBancos empresasBancos = (EmpresasBancos) query.getSingleResult();
            return empresasBancos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaEmpresasBancos consultarTipoCurso : " + e.toString());
            EmpresasBancos tiposEntidades = null;
            return tiposEntidades;
        }
    }
}
