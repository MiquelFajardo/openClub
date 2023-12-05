package main.java.persistencia;

import java.util.List;

/**
 * Interfície per la implementació del patró de disseny DAO
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public interface InterficieExtensaDAO<T> {
    public void emmagatzemar(T entitat, String nomTaula);
    public void modificar(T entitat, String nomTaula);
    public void eliminar(Long id,  String nomTaula);
    public void restaurar(Long id, String nomTaula);
    public T obtenir(Long id, String nomTaula);
    public List<T> obtenirTot(String nomTaula);
    public List<T> obtenirTotPerPropietari(String nomTaula, Long idPropietari);
}
