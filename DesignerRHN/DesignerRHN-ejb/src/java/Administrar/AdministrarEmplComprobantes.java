package Administrar;

import Entidades.Comprobantes;
import Entidades.CortesProcesos;
import Entidades.Empleados;
import Entidades.Procesos;
import Entidades.SolucionesNodos;
import Entidades.Terceros;
import InterfaceAdministrar.AdministrarEmplComprobantesInterface;
import InterfacePersistencia.PersistenciaComprobantesInterface;
import InterfacePersistencia.PersistenciaCortesProcesosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaProcesosInterface;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarEmplComprobantes implements AdministrarEmplComprobantesInterface {

    @EJB
    PersistenciaComprobantesInterface persistenciaComprobantes;
    @EJB
    PersistenciaCortesProcesosInterface persistenciaCortesProcesos;
    @EJB
    PersistenciaSolucionesNodosInterface persistenciaSolucionesNodos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaProcesosInterface persistenciaProcesos;
    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;

    public Empleados buscarEmpleado(BigInteger secuencia) {
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
    public BigInteger maximoNumeroComprobante(){
        return persistenciaComprobantes.numeroMaximoComprobante();
    }

    @Override
    public List<Comprobantes> comprobantesEmpleado(BigInteger secuenciaEmpleado) {
        return persistenciaComprobantes.comprobantesEmpleado(secuenciaEmpleado);
    }

    @Override
    public List<CortesProcesos> cortesProcesosComprobante(BigInteger secuenciaComprobante) {
        return persistenciaCortesProcesos.cortesProcesosComprobante(secuenciaComprobante);
    }

    @Override
    public List<SolucionesNodos> solucionesNodosCorteProcesoEmpleado(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado) {
        return persistenciaSolucionesNodos.solucionNodoCorteProcesoEmpleado(secuenciaCorteProceso, secuenciaEmpleado);
    }

    @Override
    public List<SolucionesNodos> solucionesNodosCorteProcesoEmpleador(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado) {
        return persistenciaSolucionesNodos.solucionNodoCorteProcesoEmpleador(secuenciaCorteProceso, secuenciaEmpleado);
    }

    @Override
    public List<Procesos> lovProcesos() {
        return persistenciaProcesos.lovProcesos();
    }
    
    @Override
    public List<Terceros> lovTerceros(BigInteger secEmpresa) {
        return persistenciaTerceros.lovTerceros(secEmpresa);
    }

    //CAMBIOS COMPROBANTES
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

    //CAMBIOS CORTES PROCESOS
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
    public void modificarSolucionesNodosEmpleador(List<SolucionesNodos> listaSolucionesNodosEmpleador) {
        for (int i = 0; i < listaSolucionesNodosEmpleador.size(); i++) {
            System.out.println("Modificando Soluciones Nodo Empleador...");
            if (listaSolucionesNodosEmpleador.get(i).getNit().getSecuencia() == null) {
                listaSolucionesNodosEmpleador.get(i).setNit(null);
                persistenciaSolucionesNodos.editar(listaSolucionesNodosEmpleador.get(i));
            } else {
                persistenciaSolucionesNodos.editar(listaSolucionesNodosEmpleador.get(i));
            }
        }
    }
}
