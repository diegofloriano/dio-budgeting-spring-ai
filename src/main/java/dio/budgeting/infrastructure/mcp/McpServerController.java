package dio.budgeting.infrastructure.mcp;

import dio.budgeting.application.ListTransactionsByCategoryUseCase;
import dio.budgeting.application.PersistTransactionUseCase;
import dio.budgeting.application.input.PersistTransactionInput;
import dio.budgeting.domain.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mcp")
public class McpServerController {

    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ListTransactionsByCategoryUseCase listTransactionsByCategoryUseCase;

    public McpServerController(PersistTransactionUseCase persistTransactionUseCase,
                               ListTransactionsByCategoryUseCase listTransactionsByCategoryUseCase) {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionsByCategoryUseCase = listTransactionsByCategoryUseCase;
    }

    @GetMapping("/tools")
    public Map<String, Object> listMcpTools() {
        return Map.of(
                "tools", List.of(
                        Map.of(
                                "name", "persist-transaction",
                                "description", "Persiste uma nova transação financeira",
                                "parameters", Map.of("description", "string", "amount", "long", "category", "Category")
                        ),
                        Map.of(
                                "name", "List-transactions-by-category",
                                "description", "Lista transações financeiras filtradas por categoria",
                                "parameters", Map.of("category", "Category")
                        )
                )
        );
    }

    @PostMapping("/call")
    public Object executeMcpTool(@RequestBody McpRequest request) {
        if ("persist-transaction".equalsIgnoreCase(request.toolName())) {
            var input = new PersistTransactionInput(
                    (String) request.arguments().get("description"),
                    ((Number) request.arguments().get("amount")).longValue(),
                    Category.valueOf((String) request.arguments().get("category"))
            );
            return persistTransactionUseCase.execute(input);
        } else if ("List-transactions-by-category".equalsIgnoreCase(request.toolName())) {
            Category cat = Category.valueOf((String) request.arguments().get("category"));
            return listTransactionsByCategoryUseCase.execute(cat);
        }
        throw new IllegalArgumentException("Ferramenta MCP desconhecida: " + request.toolName());
    }

    public record McpRequest(String toolName, Map<String, Object> arguments) {}
}