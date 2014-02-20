/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.TiposConstantes;
import InterfaceAdministrar.AdministrarTiposConstantesInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaTiposConstantesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposConstantes implements AdministrarTiposConstantesInterface{
    
    @EJB
    PersistenciaTiposConstantesInterface persistenciaTiposConstantes;
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;
    

    @Override
    public List<TiposConstantes> buscarTiposConstantes(BigInteger secuenciaOperando, String tipoOperando) {
        List<TiposConstantes> listaTiposConstantes;
        listaTiposConstantes = persistenciaTiposConstantes.tiposConstantes(secuenciaOperando, tipoOperando);
        return listaTiposConstantes;
    }

    @Override
    public void borrarTiposConstantes(TiposConstantes tiposConstantes) {
        persistenciaTiposConstantes.borrar(tiposConstantes);
    }

    @Override
    public void crearTiposConstantes(TiposConstantes tiposConstantes) {
        persistenciaTiposConstantes.crear(tiposConstantes);
    }

    @Override
    public void modificarTiposConstantes(TiposConstantes tiposConstantes) {
        persistenciaTiposConstantes.editar(tiposConstantes);

    }
    
}
