/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Formulas;
import Entidades.FormulasAseguradas;
import Entidades.Periodicidades;
import Entidades.Procesos;
import InterfaceAdministrar.AdministrarFormulasAseguradasInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaFormulasAseguradasInterface;
import InterfacePersistencia.PersistenciaPeriodicidadesInterface;
import InterfacePersistencia.PersistenciaProcesosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarFormulasAseguradas implements AdministrarFormulasAseguradasInterface {
    //-------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaFormulasAseguradas'.
     */
    @EJB
    PersistenciaFormulasAseguradasInterface persistenciaFormulasAseguradas;
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;
    @EJB
    PersistenciaProcesosInterface persistenciaProcesos;
    @EJB
    PersistenciaPeriodicidadesInterface persistenciaPeriodicidades;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------

    public void modificarFormulasAseguradas(List<FormulasAseguradas> listaFormulasAseguradas) {
        for (int i = 0; i < listaFormulasAseguradas.size(); i++) {
            System.out.println("Administrar Modificando...");
            if (listaFormulasAseguradas.get(i).getPeriodicidad().getSecuencia() == null) {
                listaFormulasAseguradas.get(i).setPeriodicidad(null);
            }
            persistenciaFormulasAseguradas.editar(listaFormulasAseguradas.get(i));
        }
    }

    public void borrarFormulasAseguradas(List<FormulasAseguradas> listaFormulasAseguradas) {
        for (int i = 0; i < listaFormulasAseguradas.size(); i++) {
            System.out.println("Administrar Borrando...");
            if (listaFormulasAseguradas.get(i).getPeriodicidad().getSecuencia() == null) {
                listaFormulasAseguradas.get(i).setPeriodicidad(null);
            }
            persistenciaFormulasAseguradas.borrar(listaFormulasAseguradas.get(i));
        }
    }

    public void crearFormulasAseguradas(List<FormulasAseguradas> listaFormulasAseguradas) {
        for (int i = 0; i < listaFormulasAseguradas.size(); i++) {
            if (listaFormulasAseguradas.get(i).getPeriodicidad().getSecuencia() == null) {
                listaFormulasAseguradas.get(i).setPeriodicidad(null);
            }
            persistenciaFormulasAseguradas.crear(listaFormulasAseguradas.get(i));
        }
    }

    @Override
    public List<FormulasAseguradas> consultarFormulasAseguradas() {
        List<FormulasAseguradas> listaFormulasAseguradas;
        listaFormulasAseguradas = persistenciaFormulasAseguradas.consultarFormulasAseguradas();
        return listaFormulasAseguradas;
    }

    @Override
    public List<Formulas> consultarLOVFormulas() {
        List<Formulas> listLOVFormulas;
        listLOVFormulas = persistenciaFormulas.buscarFormulas();
        return listLOVFormulas;
    }

    public List<Procesos> consultarLOVProcesos() {
        List<Procesos> listLOVFormulas;
        listLOVFormulas = persistenciaProcesos.buscarProcesos();
        return listLOVFormulas;
    }

    public List<Periodicidades> consultarLOVPPeriodicidades() {
        List<Periodicidades> listLOVFormulas;
        listLOVFormulas = persistenciaPeriodicidades.consultarPeriodicidades();
        return listLOVFormulas;
    }

}
