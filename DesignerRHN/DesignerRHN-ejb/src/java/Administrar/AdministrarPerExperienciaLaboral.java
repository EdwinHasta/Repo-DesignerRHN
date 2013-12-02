/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import Entidades.HvExperienciasLaborales;
import Entidades.MotivosRetiros;
import Entidades.SectoresEconomicos;
import InterfaceAdministrar.AdministrarPerExperienciaLaboralInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaHvExperienciasLaboralesInterface;
import InterfacePersistencia.PersistenciaMotivosRetirosInterface;
import InterfacePersistencia.PersistenciaSectoresEconomicosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarPerExperienciaLaboral implements AdministrarPerExperienciaLaboralInterface {

    @EJB
    PersistenciaHvExperienciasLaboralesInterface persistenciaHvExperienciasLaborales;
    @EJB
    PersistenciaMotivosRetirosInterface persistenciaMotivosRetiros;
    @EJB
    PersistenciaSectoresEconomicosInterface persistenciaSectoresEconomicos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;

    @Override
    public Empleados empleadoActual(BigInteger secuencia) {
        try {
            Empleados empl = persistenciaEmpleado.buscarEmpleado(secuencia);
            return empl;
        } catch (Exception e) {
            System.out.println("Error empleadoActual Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SectoresEconomicos> listSectoresEconomicos() {
        try {
            List<SectoresEconomicos> retorno = persistenciaSectoresEconomicos.buscarSectoresEconomicos();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error listSectoresEconomicos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<MotivosRetiros> listMotivosRetiros() {
        try {
            List<MotivosRetiros> retorno = persistenciaMotivosRetiros.buscarMotivosRetiros();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error listMotivosRetiros Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearExperienciaLaboral(List<HvExperienciasLaborales> listHEL) {
        try {
            for (int i = 0; i < listHEL.size(); i++) {
                if (listHEL.get(i).getMotivoretiro().getSecuencia() == null) {
                    listHEL.get(i).setMotivoretiro(null);
                }
                if (listHEL.get(i).getSectoreconomico().getSecuencia() == null) {
                    listHEL.get(i).setSectoreconomico(null);
                }
                String aux1, aux2, aux3;
                aux1 = listHEL.get(i).getAlcance().toUpperCase();
                listHEL.get(i).setAlcance(aux1);
                aux2 = listHEL.get(i).getJefeinmediato().toUpperCase();
                listHEL.get(i).setJefeinmediato(aux2);
                aux3 = listHEL.get(i).getEmpresa().toUpperCase();
                listHEL.get(i).setEmpresa(aux3);
                persistenciaHvExperienciasLaborales.crear(listHEL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearExperienciaLaboral Admi : " + e.toString());
        }
    }

    @Override
    public void editarExperienciaLaboral(List<HvExperienciasLaborales> listHEL) {
        try {
            for (int i = 0; i < listHEL.size(); i++) {
                if (listHEL.get(i).getMotivoretiro().getSecuencia() == null) {
                    listHEL.get(i).setMotivoretiro(null);
                }
                if (listHEL.get(i).getSectoreconomico().getSecuencia() == null) {
                    listHEL.get(i).setSectoreconomico(null);
                }
                String aux1, aux2, aux3;
                aux1 = listHEL.get(i).getAlcance().toUpperCase();
                listHEL.get(i).setAlcance(aux1);
                aux2 = listHEL.get(i).getJefeinmediato().toUpperCase();
                listHEL.get(i).setJefeinmediato(aux2);
                aux3 = listHEL.get(i).getEmpresa().toUpperCase();
                listHEL.get(i).setEmpresa(aux3);
                persistenciaHvExperienciasLaborales.editar(listHEL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarExperienciaLaboral Admi : " + e.toString());
        }
    }

    @Override
    public void borrarExperienciaLaboral(List<HvExperienciasLaborales> listHEL) {
        try {
            for (int i = 0; i < listHEL.size(); i++) {
                if (listHEL.get(i).getMotivoretiro().getSecuencia() == null) {
                    listHEL.get(i).setMotivoretiro(null);
                }
                if (listHEL.get(i).getSectoreconomico().getSecuencia() == null) {
                    listHEL.get(i).setSectoreconomico(null);
                }
                persistenciaHvExperienciasLaborales.borrar(listHEL.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarExperienciaLaboral Admi : " + e.toString());
        }
    }

    @Override
    public List<HvExperienciasLaborales> listExperienciasLaboralesSecuenciaEmpleado(BigInteger secuencia) {
        try {
            List<HvExperienciasLaborales> retorno = persistenciaHvExperienciasLaborales.experienciasLaboralesSecuenciaEmpleado(secuencia);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error listExperienciasLaboralesSecuenciaEmpleado Admi : " + e.toString());
            return null;
        }
    }
}
