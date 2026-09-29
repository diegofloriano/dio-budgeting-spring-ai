package budgeting.com.example.demo.infrastructure.persistence.repository;

import budgeting.com.example.demo.infrastructure.persistence.entity.TransactionEntity;

import java.util.UUID;

public interface TransactionEntityRepository extends CrudRepository<TransactionEntity, UUID>{
    List<TransactionEntity> findAllByCategory(Category category);
}
