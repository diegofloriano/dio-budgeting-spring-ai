package budgeting.com.example.demo.application;

import budgeting.com.example.demo.application.input.PersistTransactionInput;
import budgeting.com.example.demo.application.output.TransactionOutput;
import budgeting.com.example.demo.domain.Category;
import budgeting.com.example.demo.domain.Transaction;
import budgeting.com.example.demo.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class PersistTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }
    @Tool(name = "persist-transaction",description = "Persiste uma nova transação financeira")
    public TransactionOutput execute(PersistTransactionInput input){
        var transaction = transactionRepository.save(
                new Transaction(input.description(), input.amount(), input.category()));
        return TransactionOutput.from(transaction);
    }
}
