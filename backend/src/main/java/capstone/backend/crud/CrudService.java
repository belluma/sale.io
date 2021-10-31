package capstone.backend.crud;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CrudService<DTO extends DataTransferObject, DBO extends DatabaseObject> {

    private final CrudRepository<DatabaseObject, Long> repository;
    private CrudMapper<DTO, DBO> mapper;

    @Autowired
    public CrudService(CrudRepository<DatabaseObject, Long> repository, CrudMapper<DTO, DBO> mapper) {
        this.repository = repository;
    }

    public void setMapper(CrudMapper<DTO, DBO> mapper){
        this.mapper = mapper;
    }


    public List<DTO> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::mapToDto)
                .toList();
    }

    public DTO getSpecific(Long id) {
        return repository
                .findById(id)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new NoSuchElementException(String.format("No element with id %d found", id)));
    }

    public DTO save(DTO objectToSave) {
        return mapper
                .mapToDto(repository
                        .save(mapper.mapToDbo(objectToSave)));
    }
}
