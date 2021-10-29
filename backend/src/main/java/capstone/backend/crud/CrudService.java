package capstone.backend.crud;


import capstone.backend.crud.DatabaseObject;
import capstone.backend.crud.DataTransferObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CrudService<DTO extends DataTransferObject, DBO extends DatabaseObject > {

    private JpaRepository<DatabaseObject, Long> repository;
    private CrudMapper<DTO, DBO> mapper;


    public List<DTO> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::mapToDto)
                .toList();
    }
}
