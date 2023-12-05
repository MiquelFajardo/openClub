package main.java.persistencia;

import java.util.List;

/**
 * Interfície per la implementació del patró de disseny DAO
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public interface InterficieDAO <T> {
    public void emmagatzemar(T entitat);
    public void modificar(T entitat);
    public void eliminar(Long id);
    public void restaurar(Long id);
    public T obtenir(Long id);
    public List<T> obtenirTot();
}
