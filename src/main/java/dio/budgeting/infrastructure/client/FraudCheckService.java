package dio.budgeting.infrastructure.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class FraudCheckService {

    private final RestClient restClient;

    public FraudCheckService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://httpbin.org")
                .build();
    }

    public boolean isSuspicious(long amountInCents) {
        // Regra de negócio: se for maior que R$ 10.000,00, consulta serviço externo
        if (amountInCents > 1000000L) {
            try {
                restClient.get().uri("/get?riskCheck=true").retrieve().toBodilessEntity();
            } catch (Exception ignored) {
                // Fallback resiliente
            }
        }
        return false;
    }
}