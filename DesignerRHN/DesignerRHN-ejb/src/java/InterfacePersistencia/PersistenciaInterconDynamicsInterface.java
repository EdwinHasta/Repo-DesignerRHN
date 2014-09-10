/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.InterconDynamics;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrador
 */
public interface PersistenciaInterconDynamicsInterface {

    public void crear(EntityManager em, InterconDynamics interconDynamics);

    public void editar(EntityManager em, InterconDynamics interconDynamics);

    public void borrar(EntityManager em, InterconDynamics interconDynamics);

    public InterconDynamics buscarInterconDynamicSecuencia(EntityManager em, BigInteger secuencia);

    public List<InterconDynamics> buscarInterconDynamicParametroContable(EntityManager em, Date fechaInicial, Date fechaFinal);

    public int contarProcesosContabilizadosInterconDynamics(EntityManager em, Date fechaInicial, Date fechaFinal);

    public void cerrarProcesoContabilizacion(EntityManager em, Date fechaInicial, Date fechaFinal, BigInteger proceso, BigInteger empleadoDesde, BigInteger empleadoHasta);

    public void ejecutarPKGCrearArchivoPlano(EntityManager em, Date fechaInicial, Date fechaFinal, BigInteger proceso, String descripcionProceso, String nombreArchivo, BigInteger empleadoDesde, BigInteger empleadoHasta);

    public void ejecutarPKGRecontabilizar(EntityManager em, Date fechaInicial, Date fechaFinal);

    public void deleteInterconDynamics(EntityManager em, Date fechaInicial, Date fechaFinal, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta);

    public void actualizarFlagContabilizacionDeshacerDynamics(EntityManager em, Date fechaInicial, Date fechaFinal, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta);

    public void ejecutarPKGUbicarnuevointercon_DYNAMICS(EntityManager em, BigInteger secuencia, Date fechaInicial, Date fechaFinal, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta);

    public void anularComprobantesCerrados(EntityManager em, Date fechaInicial, Date fechaFinal, BigInteger proceso);

    public Date obtenerFechaContabilizacionMaxInterconDynamics(EntityManager em);
}
