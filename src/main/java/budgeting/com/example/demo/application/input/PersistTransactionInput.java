tpackage budgeting.com.example.demo.application.input;

import budgeting.com.example.demo.domain.Category;

public record PersistTransactionInput(@ToolParam(description = "Descrição do gasto")String description,
                                      @ToolParam(description = "Valor gasto (em centavos)")long amount,
                                      @ToolParam(description = "Categoria de uma transação")Category category) {
}
