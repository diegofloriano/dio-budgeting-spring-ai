package dio.budgeting.infrastructure.persistence.entity;

import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionId;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    private UUID id;
    private String description;
    private long amount;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "source_channel")
    private String sourceChannel;

    @Column(name = "audio_file_name")
    private String audioFileName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public TransactionEntity() {
    }

    public TransactionEntity(UUID id, String description, long amount, Category category,
                             String userId, String sourceChannel, String audioFileName, LocalDateTime createdAt) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.userId = userId;
        this.sourceChannel = sourceChannel;
        this.audioFileName = audioFileName;
        this.createdAt = createdAt;
    }

    public static TransactionEntity from(Transaction transaction) {
        return new TransactionEntity(
                transaction.getId().uuid(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getCategory(),
                transaction.getUserId(),
                transaction.getSourceChannel(),
                transaction.getAudioFileName(),
                transaction.getCreatedAt()
        );
    }

    public Transaction toDomain() {
        return new Transaction(
                new TransactionId(this.id),
                this.description,
                this.amount,
                this.category,
                this.userId,
                this.sourceChannel,
                this.audioFileName,
                this.createdAt
        );
    }

    public UUID getId() { return id; }
    public String getDescription() { return description; }
    public long getAmount() { return amount; }
    public Category getCategory() { return category; }
    public String getUserId() { return userId; }
    public String getSourceChannel() { return sourceChannel; }
    public String getAudioFileName() { return audioFileName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}