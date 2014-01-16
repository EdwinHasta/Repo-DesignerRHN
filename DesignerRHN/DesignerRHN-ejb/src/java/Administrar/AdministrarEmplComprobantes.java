/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.Comprobantes;
import Entidades.CortesProcesos;
import Entidades.DetallesFormulas;
import Entidades.Empleados;
import Entidades.Procesos;
import Entidades.SolucionesNodos;
import Entidades.Terceros;
import InterfaceAdministrar.AdministrarEmplComprobantesInterface;
import InterfacePersistencia.PersistenciaComprobantesInterface;
import InterfacePersistencia.PersistenciaCortesProcesosInterface;
import InterfacePersistencia.PersistenciaDetallesFormulasInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaHistoriasformulasInterface;
import InterfacePersistencia.PersistenciaProcesosInterface;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'EmplComprobantes'.
 *
 * @author betelgeuse
 */
@Stateful
public class AdministrarEmplComprobantes implements AdministrarEmplComprobantesInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaComprobantes'.
     */
    @EJB
    PersistenciaComprobantesInterface persistenciaComprobantes;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaCortesProcesos'.
     */
    @EJB
    PersistenciaCortesProcesosInterface persistenciaCortesProcesos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaSolucionesNodos'.
     */
    @EJB
    PersistenciaSolucionesNodosInterface persistenciaSolucionesNodos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaEmpleado'.
     */
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaProcesos'.
     */
    @EJB
    PersistenciaProcesosInterface persistenciaProcesos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaTerceros'.
     */
    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaDetallesFormulas'.
     */
    @EJB
    PersistenciaDetallesFormulasInterface persistenciaDetallesFormulas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaHistoriasformulas'.
     */
    @EJB
    PersistenciaHistoriasformulasInterface persistenciaHistoriasformulas;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public Empleados consultarEmpleado(BigInteger secuencia) {
        Empleados empleado;
        try {
            empleado = persistenciaEmpleado.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            return empleado;
        }
    }
    
    @Override
    public BigInteger consultarMaximoNumeroComprobante(){
        return persistenciaComprobantes.numeroMaximoComprobante();
    }

    @Override
    public List<Comprobantes> consultarComprobantesEmpleado(BigInteger secuenciaEmpleado) {
        return persistenciaComprobantes.comprobantesEmpleado(secuenciaEmpleado);
    }

    @Override
    public List<CortesProcesos> consultarCortesProcesosComprobante(BigInteger secuenciaComprobante) {
        return persistenciaCortesProcesos.cortesProcesosComprobante(secuenciaComprobante);
    }

    @Override
    public List<SolucionesNodos> consultarSolucionesNodosEmpleado(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado) {
        return persistenciaSolucionesNodos.solucionNodoCorteProcesoEmpleado(secuenciaCorteProceso, secuenciaEmpleado);
    }

    @Override
    public List<SolucionesNodos> consultarSolucionesNodosEmpleador(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado) {
        return persistenciaSolucionesNodos.solucionNodoCorteProcesoEmpleador(secuenciaCorteProceso, secuenciaEmpleado);
    }

    @Override
    public List<Procesos> consultarLOVProcesos() {
        return persistenciaProcesos.lovProcesos();
    }
    
    @Override
    public List<Terceros> consultarLOVTerceros(BigInteger secEmpresa) {
        return persistenciaTerceros.lovTerceros(secEmpresa);
    }

    @Override
    public void modificarComprobantes(List<Comprobantes> listComprobantes) {
        for (int i = 0; i < listComprobantes.size(); i++) {
            System.out.println("Modificando Comprobantes...");
            persistenciaComprobantes.editar(listComprobantes.get(i));
        }

    }

    @Override
    public void borrarComprobantes(Comprobantes comprobante) {
        try {
            persistenciaComprobantes.borrar(comprobante);
        } catch (Exception e) {
            System.out.println("Error borrarComprobantes" + e);
        }
    }

    @Override
    public void crearComprobante(Comprobantes comprobantes) {
        persistenciaComprobantes.crear(comprobantes);
    }
    
    @Override
    public void modificarCortesProcesos(List<CortesProcesos> listaCortesProcesos) {
        for (int i = 0; i < listaCortesProcesos.size(); i++) {
            System.out.println("Modificando Cortes procesos...");
            if (listaCortesProcesos.get(i).getProceso().getSecuencia() == null) {
                listaCortesProcesos.get(i).setProceso(null);
                persistenciaCortesProcesos.editar(listaCortesProcesos.get(i));
            } else {
                persistenciaCortesProcesos.editar(listaCortesProcesos.get(i));
            }
        }
    }

    @Override
    public void borrarCortesProcesos(CortesProcesos corteProceso) {
        try {
            persistenciaCortesProcesos.borrar(corteProceso);
        } catch (Exception e) {
            System.out.println("Error borrarCortesProcesos" + e);
        }

    }

    @Override
    public void crearCorteProceso(CortesProcesos corteProceso) {
        persistenciaCortesProcesos.crear(corteProceso);
    }
    
    @Override
    public void modificarSolucionesNodosEmpleado(List<SolucionesNodos> listaSolucionesNodosEmpleado) {
        for (int i = 0; i < listaSolucionesNodosEmpleado.size(); i++) {
            System.out.println("Modificando Soluciones Nodo Empleado...");
            if (listaSolucionesNodosEmpleado.get(i).getNit().getSecuencia() == null) {
                listaSolucionesNodosEmpleado.get(i).setNit(null);
                persistenciaSolucionesNodos.editar(listaSolucionesNodosEmpleado.get(i));
            } else {
                persistenciaSolucionesNodos.editar(listaSolucionesNodosEmpleado.get(i));
            }
        }
    }
    
    @Override
    public List<DetallesFormulas> consultarDetallesFormula(BigInteger secEmpleado, String fechaDesde, String fechaHasta, BigInteger secProceso, BigInteger secHistoriaFormula){
        return persistenciaDetallesFormulas.detallesFormula(secEmpleado, fechaDesde, fechaHasta, secProceso, secHistoriaFormula);
    }
    
    @Override
    public BigInteger consultarHistoriaFormula(BigInteger secFormula, String fechaDesde){
        return persistenciaHistoriasformulas.obtenerSecuenciaHistoriaFormula(secFormula, fechaDesde);
    }
}
