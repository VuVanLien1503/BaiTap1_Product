package manager.service.my_interface;

import java.util.List;

public interface ICrud<E> {
    List<E> findAll();
    void save (E e);
    void delete(E e);
}
