package Administrar;

import Entidades.HVHojasDeVida;
import InterfacePersistencia.AdministrarEmpleadoIndividualInterface;
import InterfacePersistencia.PersistenciaHVHojasDeVidaInterface;
import java.math.BigInteger;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarEmpleadoIndividual implements AdministrarEmpleadoIndividualInterface {
    
    @EJB
    PersistenciaHVHojasDeVidaInterface persistenciaHVHojasDeVida;
    
    @Override
    public HVHojasDeVida hvHojaDeVidaPersona(BigInteger secPersona){
        return persistenciaHVHojasDeVida.hvHojaDeVidaPersona(secPersona);
    }
}
