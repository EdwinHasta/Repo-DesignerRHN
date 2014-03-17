/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.DetallesFormasDtos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaDetallesFormasDtosInterface {

    public void crear(DetallesFormasDtos detallesFormasDtos);

    public void editar(DetallesFormasDtos detallesFormasDtos);
    
    public void borrar(DetallesFormasDtos detallesFormasDtos);

    public List<DetallesFormasDtos> detallesFormasDescuentos(BigInteger formaDto);
    
}
