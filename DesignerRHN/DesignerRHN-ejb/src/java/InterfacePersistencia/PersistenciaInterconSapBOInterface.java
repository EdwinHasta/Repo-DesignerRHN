/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.InterconSapBO;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrador
 */
public interface PersistenciaInterconSapBOInterface {

    public void crear(EntityManager em, InterconSapBO interconSapBO);

    public void editar(EntityManager em, InterconSapBO interconSapBO);

    public void borrar(EntityManager em, InterconSapBO interconSapBO);

    public InterconSapBO buscarInterconSAPBOSecuencia(EntityManager em, BigInteger secuencia);

    public List<InterconSapBO> buscarInterconSAPBOParametroContable(EntityManager em, Date fechaInicial, Date fechaFinal);

    public Date obtenerFechaMaxInterconSAPBO(EntityManager em);

    public void actualizarFlagProcesoAnularInterfaseContableSAPBOV8(EntityManager em, Date fechaIni, Date fechaFin);

    public void ejeuctarPKGUbicarnuevointercon_SAPBOV8(EntityManager em, BigInteger secuencia, Date fechaIni, Date fechaFin, BigInteger proceso);

    public void ejecutarDeleteInterconSAPBOV8(EntityManager em, Date fechaIni, Date fechaFin, BigInteger proceso);

    public void cerrarProcesoLiquidacion(EntityManager em, Date fechaIni, Date fechaFin, BigInteger proceso);
    
    public void ejecutarPKGRecontabilizacion(EntityManager em, Date fechaIni, Date fechaFin);
}
