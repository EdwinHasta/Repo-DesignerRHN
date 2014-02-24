/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Formulas;
import Entidades.TiposFormulas;
import InterfaceAdministrar.AdministrarTiposFormulasInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaTiposFormulasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposFormulas implements AdministrarTiposFormulasInterface{
    
    @EJB
    PersistenciaTiposFormulasInterface persistenciaTiposFormulas;
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;
    

    @Override
    public List<TiposFormulas> buscarTiposFormulas(BigInteger secuenciaOperando, String tipoOperando) {
        List<TiposFormulas> listaTiposFormulas;
        listaTiposFormulas = persistenciaTiposFormulas.tiposFormulas(secuenciaOperando, tipoOperando);
        return listaTiposFormulas;
    }

    @Override
    public void borrarTiposFormulas(TiposFormulas tiposFormulas) {
        persistenciaTiposFormulas.borrar(tiposFormulas);
    }

    @Override
    public void crearTiposFormulas(TiposFormulas tiposFormulas) {
        persistenciaTiposFormulas.crear(tiposFormulas);
    }

    @Override
    public void modificarTiposFormulas(TiposFormulas tiposFormulas) {
        persistenciaTiposFormulas.editar(tiposFormulas);

    }
    
    public List<Formulas> lovFormulas(){
        List<Formulas> listaFormulas;
        listaFormulas = persistenciaFormulas.buscarFormulas();
        return listaFormulas;
    }
}
