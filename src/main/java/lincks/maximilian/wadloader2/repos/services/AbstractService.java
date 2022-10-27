package lincks.maximilian.wadloader2.repos.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractService<T,ID> {
    protected final JpaRepository<T,ID> repo;

    public List<T> findAll(){
        return repo.findAll();
    }
    public T findById(ID id){
        return repo.findById(id).orElse(null);
    }

    public void delete(T toDelete){
        repo.delete(toDelete);
    }

    public void deleteById(ID id){
        repo.deleteById(id);
    }

    public void deleteAll(){
        repo.deleteAll();
    }

    public T save(T toAdd){
        return repo.save(toAdd);
    }

    public List<T> saveAll(Iterable<T> toSave){
        return repo.saveAll(toSave);
    }
}