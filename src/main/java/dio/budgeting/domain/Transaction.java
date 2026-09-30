package dio.budgeting.domain;

import java.time.LocalDateTime;

public class Transaction {
    private final TransactionId id;
    private final String description;
    private final long amount;
    private final Category category;
    private final String userId;
    private final String sourceChannel;
    private final String audioFileName;
    private final LocalDateTime createdAt;

    public Transaction(TransactionId id, String description, long amount, Category category,
                       String userId, String sourceChannel, String audioFileName, LocalDateTime createdAt) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.userId = userId != null ? userId : "anonymous_user";
        this.sourceChannel = sourceChannel != null ? sourceChannel : "REST_API";
        this.audioFileName = audioFileName;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    public Transaction(String description, long amount, Category category) {
        this(new TransactionId(), description, amount, category, "system_user", "REST_API", null, LocalDateTime.now());
    }

    public TransactionId getId() { return id; }
    public String getDescription() { return description; }
    public long getAmount() { return amount; }
    public Category getCategory() { return category; }
    public String getUserId() { return userId; }
    public String getSourceChannel() { return sourceChannel; }
    public String getAudioFileName() { return audioFileName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}