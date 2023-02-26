package manager.service.my_interface;

import java.util.List;

public interface ISearch<E>{
    E findById(Long id);
    public List<E> findByName(String name);
}
