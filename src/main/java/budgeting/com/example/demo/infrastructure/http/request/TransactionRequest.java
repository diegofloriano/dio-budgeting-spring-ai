package budgeting.com.example.demo.infrastructure.http.request;

public record TransactionRequest(String description, Category category, long amount) {
    public PersistTransactionInput toInput(){
        return new PersistTransactionInput(description, amount, category);
    }
}
