package capstone.backend.crud;

import org.springframework.stereotype.Component;

@Component
public  interface CrudMapper< DTO extends DataTransferObject, DBO extends DatabaseObject> {
 abstract DTO mapToDto(DatabaseObject databaseObject);
 abstract DBO mapToDbo(DataTransferObject databaseObject);
}
